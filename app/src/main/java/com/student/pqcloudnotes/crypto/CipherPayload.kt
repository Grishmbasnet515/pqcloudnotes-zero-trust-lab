package com.student.pqcloudnotes.crypto

data class CipherPayload(
    val suiteId: String,
    val keyVersion: Int,
    val ivBase64: String,
    val cipherBase64: String
)
