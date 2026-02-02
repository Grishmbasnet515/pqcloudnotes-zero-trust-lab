package com.student.pqcloudnotes.network

import android.util.Base64
import com.student.pqcloudnotes.BuildConfig
import okhttp3.Interceptor
import okio.Buffer
import java.security.MessageDigest
import java.util.UUID
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

object SigningProvider {
    private const val SHARED_SECRET = "demo-shared-secret"

    fun create(): Interceptor? {
        if (BuildConfig.INSECURE_MODE) return null
        return Interceptor { chain ->
            val request = chain.request()
            val bodyHash = sha256Hex(bodyToBytes(request))
            val timestamp = (System.currentTimeMillis() / 1000L).toString()
            val nonce = UUID.randomUUID().toString()
            val path = request.url.encodedPath + request.url.encodedQuery?.let { "?$it" }.orEmpty()
            val payload = request.method + path + timestamp + nonce + bodyHash
            val signature = hmacSha256Hex(SHARED_SECRET.toByteArray(), payload.toByteArray())
            val signed = request.newBuilder()
                .addHeader("X-Timestamp", timestamp)
                .addHeader("X-Nonce", nonce)
                .addHeader("X-Body-Hash", bodyHash)
                .addHeader("X-Signature", signature)
                .build()
            chain.proceed(signed)
        }
    }

    private fun bodyToBytes(request: okhttp3.Request): ByteArray {
        val body = request.body ?: return ByteArray(0)
        val buffer = Buffer()
        body.writeTo(buffer)
        return buffer.readByteArray()
    }

    private fun sha256Hex(data: ByteArray): String {
        val digest = MessageDigest.getInstance("SHA-256").digest(data)
        return Base64.encodeToString(digest, Base64.NO_WRAP)
    }

    private fun hmacSha256Hex(key: ByteArray, data: ByteArray): String {
        val mac = Mac.getInstance("HmacSHA256")
        mac.init(SecretKeySpec(key, "HmacSHA256"))
        val digest = mac.doFinal(data)
        return Base64.encodeToString(digest, Base64.NO_WRAP)
    }
}
