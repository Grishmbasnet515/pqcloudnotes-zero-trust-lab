package com.student.pqcloudnotes.data.repo.impl

import com.student.pqcloudnotes.data.api.AuthRequest
import com.student.pqcloudnotes.data.api.RetrofitProvider
import com.student.pqcloudnotes.data.auth.TokenStoreProvider
import com.student.pqcloudnotes.data.model.UserSession
import com.student.pqcloudnotes.data.repo.AuthRepository

class ApiAuthRepository : AuthRepository {
    private val tokenStore = TokenStoreProvider.get()
    private val api = RetrofitProvider.create(tokenStore)

    override suspend fun login(email: String, password: String): UserSession {
        val response = api.login(AuthRequest(email, password))
        val session = UserSession(
            userId = response.userId,
            accessToken = response.accessToken,
            refreshToken = response.refreshToken,
            keyVersion = response.keyVersion,
            suiteId = response.suiteId
        )
        tokenStore.save(session)
        return session
    }

    override suspend fun register(email: String, password: String): UserSession {
        val response = api.register(AuthRequest(email, password))
        val session = UserSession(
            userId = response.userId,
            accessToken = response.accessToken,
            refreshToken = response.refreshToken,
            keyVersion = response.keyVersion,
            suiteId = response.suiteId
        )
        tokenStore.save(session)
        return session
    }

    override suspend fun refresh(refreshToken: String): UserSession {
        val response = api.refresh(mapOf("refreshToken" to refreshToken))
        val session = UserSession(
            userId = response.userId,
            accessToken = response.accessToken,
            refreshToken = response.refreshToken,
            keyVersion = response.keyVersion,
            suiteId = response.suiteId
        )
        tokenStore.save(session)
        return session
    }

    override suspend fun logout() {
        tokenStore.clear()
    }
}
