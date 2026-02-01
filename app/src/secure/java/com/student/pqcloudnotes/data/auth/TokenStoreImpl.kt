package com.student.pqcloudnotes.data.auth

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.student.pqcloudnotes.data.model.UserSession

class TokenStoreImpl(context: Context) : TokenStore {
    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()
    private val prefs = EncryptedSharedPreferences.create(
        context,
        "secure_tokens",
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    override fun save(session: UserSession) {
        prefs.edit()
            .putString("userId", session.userId)
            .putString("accessToken", session.accessToken)
            .putString("refreshToken", session.refreshToken)
            .putString("suiteId", session.suiteId)
            .putInt("keyVersion", session.keyVersion)
            .apply()
    }

    override fun load(): UserSession? {
        val userId = prefs.getString("userId", null) ?: return null
        val accessToken = prefs.getString("accessToken", null) ?: return null
        val refreshToken = prefs.getString("refreshToken", null) ?: return null
        val suiteId = prefs.getString("suiteId", "CLASSICAL") ?: "CLASSICAL"
        val keyVersion = prefs.getInt("keyVersion", 1)
        return UserSession(
            userId = userId,
            accessToken = accessToken,
            refreshToken = refreshToken,
            suiteId = suiteId,
            keyVersion = keyVersion
        )
    }

    override fun clear() {
        prefs.edit().clear().apply()
    }

    override fun storageHint(): String {
        return "EncryptedSharedPreferences: secure_tokens.xml"
    }
}
