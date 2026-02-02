package com.student.pqcloudnotes.attachments

import android.content.Context
import android.net.Uri
import android.util.Base64
import java.io.File
import java.io.FileOutputStream
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.spec.GCMParameterSpec

class AttachmentHandlerImpl(private val context: Context) : AttachmentHandler {
    override fun save(uri: Uri): String {
        val input = context.contentResolver.openInputStream(uri) ?: return "error"
        val file = File(context.filesDir, "attachment_${System.currentTimeMillis()}.bin")
        val data = input.readBytes()
        val cipher = Cipher.getInstance("AES/GCM/NoPadding")
        val keyGen = KeyGenerator.getInstance("AES")
        keyGen.init(256)
        val key = keyGen.generateKey()
        val iv = ByteArray(12).also { java.security.SecureRandom().nextBytes(it) }
        cipher.init(Cipher.ENCRYPT_MODE, key, GCMParameterSpec(128, iv))
        val encrypted = cipher.doFinal(data)
        FileOutputStream(file).use { output ->
            output.write(iv)
            output.write(encrypted)
        }
        return file.absolutePath + " (encrypted)"
    }
}
