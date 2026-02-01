package com.student.pqcloudnotes.data.repo

import com.student.pqcloudnotes.data.model.Note

interface NotesRepository {
    suspend fun listNotes(): List<Note>
    suspend fun getNote(id: String): Note?
    suspend fun upsertNote(note: Note): Note
}
