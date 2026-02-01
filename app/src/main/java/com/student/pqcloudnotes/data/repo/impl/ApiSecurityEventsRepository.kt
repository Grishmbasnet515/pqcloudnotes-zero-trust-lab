package com.student.pqcloudnotes.data.repo.impl

import com.student.pqcloudnotes.data.api.RetrofitProvider
import com.student.pqcloudnotes.data.auth.TokenStoreProvider
import com.student.pqcloudnotes.data.model.SecurityEvent
import com.student.pqcloudnotes.data.repo.SecurityEventsRepository

class ApiSecurityEventsRepository : SecurityEventsRepository {
    private val tokenStore = TokenStoreProvider.get()
    private val api = RetrofitProvider.create(tokenStore)

    override suspend fun listEvents(): List<SecurityEvent> {
        return api.securityEvents().map {
            SecurityEvent(
                id = it.id,
                type = it.type,
                createdAt = it.createdAt,
                detail = it.detail
            )
        }
    }
}
