package com.student.pqcloudnotes.crypto

import java.security.SecureRandom

object HybridSecrets {
    private val random = SecureRandom()
    private var classicalSecret: ByteArray? = null
    private var pqSecret: ByteArray? = null

    fun ensureSecrets(): Pair<ByteArray, ByteArray> {
        if (classicalSecret == null) {
            classicalSecret = ByteArray(32).also { random.nextBytes(it) }
        }
        if (pqSecret == null) {
            // Simulated PQ KEM output for demo purposes
            pqSecret = ByteArray(32).also { random.nextBytes(it) }
        }
        return Pair(classicalSecret!!, pqSecret!!)
    }
}
