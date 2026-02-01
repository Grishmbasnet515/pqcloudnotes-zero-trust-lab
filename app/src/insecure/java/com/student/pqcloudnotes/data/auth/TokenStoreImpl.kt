package com.student.pqcloudnotes.data.auth

import android.content.Context
import android.util.Base64
import com.student.pqcloudnotes.data.model.UserSession

class TokenStoreImpl(context: Context) : TokenStore {
    private val prefs = context.getSharedPreferences("insecure_tokens", Context.MODE_PRIVATE)

    override fun save(session: UserSession) {
        val raw = listOf(
            session.userId,
            session.accessToken,
            session.refreshToken,
            session.suiteId,
            session.keyVersion.toString()
        ).joinToString("|")
        val encoded = Base64.encodeToString(raw.toByteArray(), Base64.NO_WRAP)
        prefs.edit().putString("session", encoded).apply()
    }

    override fun load(): UserSession? {
        val encoded = prefs.getString("session", null) ?: return null
        val raw = String(Base64.decode(encoded, Base64.NO_WRAP))
        val parts = raw.split("|")
        if (parts.size < 5) return null
        return UserSession(
            userId = parts[0],
            accessToken = parts[1],
            refreshToken = parts[2],
            suiteId = parts[3],
            keyVersion = parts[4].toIntOrNull() ?: 1
        )
    }

    override fun clear() {
        prefs.edit().clear().apply()
    }

    override fun storageHint(): String {
        return "SharedPreferences: insecure_tokens.xml"
    }
}
