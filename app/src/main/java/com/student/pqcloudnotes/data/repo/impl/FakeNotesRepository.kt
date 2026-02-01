package com.student.pqcloudnotes.data.repo.impl

import com.student.pqcloudnotes.data.model.Note
import com.student.pqcloudnotes.data.repo.NotesRepository

class FakeNotesRepository : NotesRepository {
    private val notes = mutableListOf(
        Note(
            id = "n1",
            title = "Welcome",
            ciphertext = "ciphertext-placeholder",
            createdAt = "2026-02-01T00:00:00Z",
            ownerId = "userA",
            suiteId = "CLASSICAL",
            keyVersion = 1
        )
    )

    override suspend fun listNotes(): List<Note> = notes.toList()

    override suspend fun getNote(id: String): Note? = notes.firstOrNull { it.id == id }

    override suspend fun upsertNote(note: Note): Note {
        notes.removeAll { it.id == note.id }
        notes.add(note)
        return note
    }
}
