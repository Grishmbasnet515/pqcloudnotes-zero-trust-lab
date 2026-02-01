package com.student.pqcloudnotes.data.repo

import com.student.pqcloudnotes.data.model.UserSession

interface AuthRepository {
    suspend fun login(email: String, password: String): UserSession
    suspend fun register(email: String, password: String): UserSession
    suspend fun refresh(refreshToken: String): UserSession
    suspend fun logout()
}
