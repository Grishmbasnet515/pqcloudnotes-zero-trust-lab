package com.student.pqcloudnotes.crypto

import com.student.pqcloudnotes.data.model.CryptoSuite

interface CryptoEngine {
    fun encrypt(plaintext: String, suite: CryptoSuite, keyVersion: Int): CipherPayload
    fun decrypt(payload: CipherPayload): String
}
