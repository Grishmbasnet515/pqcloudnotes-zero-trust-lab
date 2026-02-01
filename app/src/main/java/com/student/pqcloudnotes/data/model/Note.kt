package com.student.pqcloudnotes.data.model

data class Note(
    val id: String,
    val title: String,
    val ciphertext: String,
    val createdAt: String,
    val ownerId: String,
    val suiteId: String,
    val keyVersion: Int
)
