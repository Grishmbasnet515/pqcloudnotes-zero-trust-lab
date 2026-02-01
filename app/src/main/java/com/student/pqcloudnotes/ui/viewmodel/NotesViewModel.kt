package com.student.pqcloudnotes.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.student.pqcloudnotes.data.repo.NotesRepository
import com.student.pqcloudnotes.data.repo.impl.FakeNotesRepository
import com.student.pqcloudnotes.ui.state.NotesUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NotesViewModel(
    private val notesRepository: NotesRepository = FakeNotesRepository()
) : ViewModel() {
    private val _uiState = MutableStateFlow(NotesUiState())
    val uiState: StateFlow<NotesUiState> = _uiState

    fun loadNotes() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            val notes = notesRepository.listNotes()
            _uiState.update { it.copy(isLoading = false, notes = notes) }
        }
    }
}
