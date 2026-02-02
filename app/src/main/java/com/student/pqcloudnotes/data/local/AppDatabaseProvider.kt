package com.student.pqcloudnotes.data.local

import android.content.Context
import androidx.room.Room

object AppDatabaseProvider {
    @Volatile
    private var db: AppDatabase? = null

    fun get(context: Context): AppDatabase {
        if (db == null) {
            db = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "pqcloudnotes.db"
            ).build()
        }
        return db!!
    }
}
