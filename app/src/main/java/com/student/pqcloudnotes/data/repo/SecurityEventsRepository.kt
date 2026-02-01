package com.student.pqcloudnotes.data.repo

import com.student.pqcloudnotes.data.model.SecurityEvent

interface SecurityEventsRepository {
    suspend fun listEvents(): List<SecurityEvent>
}
