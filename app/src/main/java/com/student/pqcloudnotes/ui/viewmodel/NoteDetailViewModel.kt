package com.student.pqcloudnotes.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import android.net.Uri
import com.student.pqcloudnotes.attachments.AttachmentHandler
import com.student.pqcloudnotes.attachments.AttachmentHandlerImpl
import com.student.pqcloudnotes.crypto.CipherPayload
import com.student.pqcloudnotes.crypto.SimpleCryptoEngine
import com.student.pqcloudnotes.data.AppConfigStore
import com.student.pqcloudnotes.data.auth.AppContextProvider
import com.student.pqcloudnotes.data.model.CryptoSuite
import com.student.pqcloudnotes.data.model.Note
import com.student.pqcloudnotes.data.repo.NotesRepository
import com.student.pqcloudnotes.data.repo.impl.ApiNotesRepository
import com.student.pqcloudnotes.ui.state.NoteDetailUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NoteDetailViewModel(
    private val notesRepository: NotesRepository = ApiNotesRepository()
) : ViewModel() {
    private val crypto = SimpleCryptoEngine()
    private val attachmentHandler: AttachmentHandler =
        AttachmentHandlerImpl(AppContextProvider.get())
    private val _uiState = MutableStateFlow(
        NoteDetailUiState(
            suiteId = AppConfigStore.suite.id,
            keyVersion = AppConfigStore.keyVersion
        )
    )
    val uiState: StateFlow<NoteDetailUiState> = _uiState

    fun updateTitle(value: String) {
        _uiState.update { it.copy(title = value) }
    }

    fun updatePlaintext(value: String) {
        _uiState.update { it.copy(plaintext = value) }
    }

    fun encryptNow() {
        val suite = CryptoSuite.valueOf(AppConfigStore.suite.id)
        val payload = crypto.encrypt(uiState.value.plaintext, suite, AppConfigStore.keyVersion)
        _uiState.update {
            it.copy(
                ciphertext = payloadToString(payload),
                suiteId = payload.suiteId,
                keyVersion = payload.keyVersion
            )
        }
    }

    fun saveNote() {
        viewModelScope.launch {
            val payload = parsePayload(uiState.value.ciphertext) ?: run {
                _uiState.update { it.copy(status = "Encrypt before saving") }
                return@launch
            }
            val note = Note(
                id = "note_${System.currentTimeMillis()}",
                title = uiState.value.title,
                ciphertext = payloadToString(payload),
                createdAt = "2026-02-02T00:00:00Z",
                ownerId = "me",
                suiteId = payload.suiteId,
                keyVersion = payload.keyVersion
            )
            notesRepository.upsertNote(note)
            _uiState.update { it.copy(status = "Saved") }
        }
    }

    fun saveAttachment(uri: Uri): String {
        return attachmentHandler.save(uri)
    }

    private fun payloadToString(payload: CipherPayload): String {
        return listOf(
            payload.suiteId,
            payload.keyVersion.toString(),
            payload.ivBase64,
            payload.cipherBase64
        ).joinToString("|")
    }

    private fun parsePayload(raw: String): CipherPayload? {
        val parts = raw.split("|")
        if (parts.size != 4) return null
        return CipherPayload(
            suiteId = parts[0],
            keyVersion = parts[1].toIntOrNull() ?: return null,
            ivBase64 = parts[2],
            cipherBase64 = parts[3]
        )
    }
}
