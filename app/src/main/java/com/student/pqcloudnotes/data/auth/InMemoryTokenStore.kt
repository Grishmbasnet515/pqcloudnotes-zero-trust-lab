package com.student.pqcloudnotes.data.auth

import com.student.pqcloudnotes.data.model.UserSession

object InMemoryTokenStore : TokenStore {
    private var session: UserSession? = null

    override fun save(session: UserSession) {
        this.session = session
    }

    override fun load(): UserSession? = session

    override fun clear() {
        session = null
    }
}
