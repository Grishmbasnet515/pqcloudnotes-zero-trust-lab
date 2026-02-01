package com.student.pqcloudnotes.data.auth

object TokenStoreProvider {
    private var instance: TokenStore? = null

    fun get(): TokenStore {
        if (instance == null) {
            instance = TokenStoreImpl(AppContextProvider.get())
        }
        return instance!!
    }
}
