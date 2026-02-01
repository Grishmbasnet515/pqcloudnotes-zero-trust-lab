package com.student.pqcloudnotes.data.repo.impl

import com.student.pqcloudnotes.data.model.UserSession
import com.student.pqcloudnotes.data.repo.AuthRepository

class FakeAuthRepository : AuthRepository {
    override suspend fun login(email: String, password: String): UserSession {
        return UserSession(
            userId = "userA",
            accessToken = "access-placeholder",
            refreshToken = "refresh-placeholder",
            keyVersion = 1,
            suiteId = "CLASSICAL"
        )
    }

    override suspend fun register(email: String, password: String): UserSession {
        return login(email, password)
    }

    override suspend fun refresh(refreshToken: String): UserSession {
        return login("demo@demo.com", "password")
    }

    override suspend fun logout() {
        // Placeholder
    }
}
