package com.student.pqcloudnotes.security

import android.content.Context
import android.os.Build

object DeviceSecurityChecker {
    fun check(context: Context): DevicePosture {
        val emulator = isProbablyEmulator()
        val rooted = isProbablyRooted()
        val debuggable = (context.applicationInfo.flags and android.content.pm.ApplicationInfo.FLAG_DEBUGGABLE) != 0
        return DevicePosture(
            isEmulator = emulator,
            isRooted = rooted,
            isDebuggable = debuggable
        )
    }

    private fun isProbablyEmulator(): Boolean {
        val fingerprint = Build.FINGERPRINT.lowercase()
        return fingerprint.contains("generic") ||
            fingerprint.contains("emulator") ||
            Build.MODEL.contains("Emulator") ||
            Build.MODEL.contains("Android SDK built for")
    }

    private fun isProbablyRooted(): Boolean {
        val tags = Build.TAGS
        return tags != null && tags.contains("test-keys")
    }
}
