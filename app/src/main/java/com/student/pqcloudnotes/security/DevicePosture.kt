package com.student.pqcloudnotes.security

data class DevicePosture(
    val isEmulator: Boolean = false,
    val isRooted: Boolean = false,
    val isDebuggable: Boolean = false
)
