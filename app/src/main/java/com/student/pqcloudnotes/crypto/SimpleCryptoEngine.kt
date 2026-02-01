package com.student.pqcloudnotes.crypto

import android.util.Base64
import com.student.pqcloudnotes.data.model.CryptoSuite
import java.security.MessageDigest
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.Mac
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.SecretKeySpec

class SimpleCryptoEngine : CryptoEngine {
    private val random = SecureRandom()

    override fun encrypt(plaintext: String, suite: CryptoSuite, keyVersion: Int): CipherPayload {
        val key = deriveKey(suite, keyVersion)
        val iv = ByteArray(12).also { random.nextBytes(it) }
        val cipher = Cipher.getInstance("AES/GCM/NoPadding")
        cipher.init(Cipher.ENCRYPT_MODE, SecretKeySpec(key, "AES"), GCMParameterSpec(128, iv))
        val cipherBytes = cipher.doFinal(plaintext.toByteArray())
        return CipherPayload(
            suiteId = suite.id,
            keyVersion = keyVersion,
            ivBase64 = Base64.encodeToString(iv, Base64.NO_WRAP),
            cipherBase64 = Base64.encodeToString(cipherBytes, Base64.NO_WRAP)
        )
    }

    override fun decrypt(payload: CipherPayload): String {
        val suite = CryptoSuite.valueOf(payload.suiteId)
        val key = deriveKey(suite, payload.keyVersion)
        val iv = Base64.decode(payload.ivBase64, Base64.NO_WRAP)
        val cipherBytes = Base64.decode(payload.cipherBase64, Base64.NO_WRAP)
        val cipher = Cipher.getInstance("AES/GCM/NoPadding")
        cipher.init(Cipher.DECRYPT_MODE, SecretKeySpec(key, "AES"), GCMParameterSpec(128, iv))
        val plaintext = cipher.doFinal(cipherBytes)
        return String(plaintext)
    }

    private fun deriveKey(suite: CryptoSuite, keyVersion: Int): ByteArray {
        val baseSecret = when (suite) {
            CryptoSuite.CLASSICAL -> sha256("classical-secret-$keyVersion")
            CryptoSuite.HYBRID_PQ_READY -> {
                val (classical, pq) = HybridSecrets.ensureSecrets()
                sha256(classical + pq + keyVersion.toString().toByteArray())
            }
        }
        return hkdf(baseSecret, "pqcloudnotes-key")
    }

    private fun hkdf(ikm: ByteArray, info: String): ByteArray {
        val salt = ByteArray(32)
        val prk = hmacSha256(salt, ikm)
        return hmacSha256(prk, info.toByteArray()).copyOf(32)
    }

    private fun hmacSha256(key: ByteArray, data: ByteArray): ByteArray {
        val mac = Mac.getInstance("HmacSHA256")
        mac.init(SecretKeySpec(key, "HmacSHA256"))
        return mac.doFinal(data)
    }

    private fun sha256(input: String): ByteArray {
        return MessageDigest.getInstance("SHA-256").digest(input.toByteArray())
    }

    private fun sha256(input: ByteArray): ByteArray {
        return MessageDigest.getInstance("SHA-256").digest(input)
    }
}
