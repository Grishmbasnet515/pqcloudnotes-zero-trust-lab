package com.student.pqcloudnotes.data.repo.impl

import com.student.pqcloudnotes.data.api.RetrofitProvider
import com.student.pqcloudnotes.data.auth.InMemoryTokenStore
import com.student.pqcloudnotes.data.model.Note
import com.student.pqcloudnotes.data.repo.NotesRepository

class ApiNotesRepository : NotesRepository {
    private val tokenStore = InMemoryTokenStore
    private val api = RetrofitProvider.create(tokenStore)

    override suspend fun listNotes(): List<Note> {
        return api.listNotes().map {
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
    }

    override suspend fun getNote(id: String): Note? {
        val it = api.getNote(id)
        return Note(
            id = it.id,
            title = it.title,
            ciphertext = it.ciphertext,
            createdAt = it.createdAt,
            ownerId = it.ownerId,
            suiteId = it.suiteId,
            keyVersion = it.keyVersion
        )
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
}
