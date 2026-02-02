package com.student.pqcloudnotes.data.repo.impl

import com.student.pqcloudnotes.BuildConfig
import com.student.pqcloudnotes.data.api.RetrofitProvider
import com.student.pqcloudnotes.data.auth.AppContextProvider
import com.student.pqcloudnotes.data.auth.TokenStoreProvider
import com.student.pqcloudnotes.data.local.AppDatabaseProvider
import com.student.pqcloudnotes.data.local.NoteEntity
import com.student.pqcloudnotes.data.model.Note
import com.student.pqcloudnotes.data.repo.NotesRepository

class ApiNotesRepository : NotesRepository {
    private val tokenStore = TokenStoreProvider.get()
    private val api = RetrofitProvider.create(tokenStore)
    private val dao = AppDatabaseProvider.get(AppContextProvider.get()).notesDao()

    override suspend fun listNotes(): List<Note> {
        val notes = api.listNotes().map {
            Note(
                id = it.id,
                title = it.title,
                ciphertext = it.ciphertext,
                createdAt = it.createdAt,
                ownerId = it.ownerId,
                suiteId = it.suiteId,
                keyVersion = it.keyVersion
            )
        }
        cacheNotes(notes)
        return notes
    }

    override suspend fun getNote(id: String): Note? {
        val it = api.getNote(id)
        val note = Note(
            id = it.id,
            title = it.title,
            ciphertext = it.ciphertext,
            createdAt = it.createdAt,
            ownerId = it.ownerId,
            suiteId = it.suiteId,
            keyVersion = it.keyVersion
        )
        cacheNotes(listOf(note))
        return note
    }

    override suspend fun upsertNote(note: Note): Note {
        val response = api.upsertNote(
            com.student.pqcloudnotes.data.api.NoteUpsertRequest(
                id = note.id,
                title = note.title,
                ciphertext = note.ciphertext,
                suiteId = note.suiteId,
                keyVersion = note.keyVersion
            )
        )
        return Note(
            id = response.id,
            title = response.title,
            ciphertext = response.ciphertext,
            createdAt = response.createdAt,
            ownerId = response.ownerId,
            suiteId = response.suiteId,
            keyVersion = response.keyVersion
        )
    }

    private suspend fun cacheNotes(notes: List<Note>) {
        notes.forEach { note ->
            val plaintextPreview = if (BuildConfig.INSECURE_MODE) {
                "PLAINTEXT_PREVIEW_DISABLED"
            } else {
                null
            }
            val entity = NoteEntity(
                id = note.id,
                title = note.title,
                ciphertext = note.ciphertext,
                createdAt = note.createdAt,
                ownerId = note.ownerId,
                suiteId = note.suiteId,
                keyVersion = note.keyVersion,
                plaintextPreview = plaintextPreview
            )
            dao.upsertNote(entity)
        }
    }
}
