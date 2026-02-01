package com.student.pqcloudnotes.data.api

data class AuthRequest(
    val email: String,
    val password: String
)

data class AuthResponse(
    val userId: String,
    val accessToken: String,
    val refreshToken: String,
    val suiteId: String,
    val keyVersion: Int
)

data class NoteResponse(
    val id: String,
    val title: String,
    val ciphertext: String,
    val createdAt: String,
    val ownerId: String,
    val suiteId: String,
    val keyVersion: Int
)

data class NoteUpsertRequest(
    val id: String?,
    val title: String,
    val ciphertext: String,
    val suiteId: String,
    val keyVersion: Int
)

data class SecurityEventResponse(
    val id: String,
    val type: String,
    val createdAt: String,
    val detail: String
)
