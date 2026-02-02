package com.student.pqcloudnotes.ui.state

data class NoteDetailUiState(
    val title: String = "",
    val plaintext: String = "",
    val ciphertext: String = "",
    val suiteId: String = "CLASSICAL",
    val keyVersion: Int = 1,
    val status: String? = null
)
