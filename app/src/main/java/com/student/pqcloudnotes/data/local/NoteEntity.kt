package com.student.pqcloudnotes.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class NoteEntity(
    @PrimaryKey val id: String,
    val title: String,
    val ciphertext: String,
    val createdAt: String,
    val ownerId: String,
    val suiteId: String,
    val keyVersion: Int
)
