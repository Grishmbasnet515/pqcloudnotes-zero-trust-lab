package com.student.pqcloudnotes.ui.state

import com.student.pqcloudnotes.data.model.Note

data class NotesUiState(
    val isLoading: Boolean = false,
    val notes: List<Note> = emptyList(),
    val error: String? = null
)
