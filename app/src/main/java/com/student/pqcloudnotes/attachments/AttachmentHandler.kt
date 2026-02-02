package com.student.pqcloudnotes.attachments

import android.net.Uri

interface AttachmentHandler {
    fun save(uri: Uri): String
}
