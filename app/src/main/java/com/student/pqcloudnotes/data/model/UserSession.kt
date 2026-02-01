package com.student.pqcloudnotes.data.model

data class UserSession(
    val userId: String,
    val accessToken: String,
    val refreshToken: String,
    val keyVersion: Int,
    val suiteId: String
)
