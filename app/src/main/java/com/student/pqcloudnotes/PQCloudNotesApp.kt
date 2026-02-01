package com.student.pqcloudnotes

import android.app.Application
import com.student.pqcloudnotes.data.auth.AppContextProvider

class PQCloudNotesApp : Application() {
    override fun onCreate() {
        super.onCreate()
        AppContextProvider.init(this)
    }
}
