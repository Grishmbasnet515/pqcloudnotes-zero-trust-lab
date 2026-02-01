package com.student.pqcloudnotes.data.auth

import android.content.Context

object AppContextProvider {
    private var appContext: Context? = null

    fun init(context: Context) {
        appContext = context.applicationContext
    }

    fun get(): Context {
        return requireNotNull(appContext) { "AppContextProvider not initialized" }
    }
}
