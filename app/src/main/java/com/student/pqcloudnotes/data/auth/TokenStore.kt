package com.student.pqcloudnotes.data.auth

import com.student.pqcloudnotes.data.model.UserSession

interface TokenStore {
    fun save(session: UserSession)
    fun load(): UserSession?
    fun clear()
}
