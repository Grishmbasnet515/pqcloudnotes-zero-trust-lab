package com.student.pqcloudnotes.attachments

import android.content.Context
import android.net.Uri
import java.io.File
import java.io.FileOutputStream

class AttachmentHandlerImpl(private val context: Context) : AttachmentHandler {
    override fun save(uri: Uri): String {
        val input = context.contentResolver.openInputStream(uri) ?: return "error"
        val file = File(context.filesDir, "attachment_${System.currentTimeMillis()}.bin")
        FileOutputStream(file).use { output ->
            input.copyTo(output)
        }
        return file.absolutePath
    }
}
