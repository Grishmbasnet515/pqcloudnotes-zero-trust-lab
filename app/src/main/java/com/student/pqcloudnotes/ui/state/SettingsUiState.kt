package com.student.pqcloudnotes.ui.state

import com.student.pqcloudnotes.data.model.CryptoSuite

data class SettingsUiState(
    val suite: CryptoSuite = CryptoSuite.CLASSICAL,
    val keyVersion: Int = 1,
    val deviceRisk: String = "LOW",
    val simulateCompromised: Boolean = false,
    val events: List<com.student.pqcloudnotes.data.model.SecurityEvent> = emptyList(),
    val isEmulator: Boolean = false,
    val isRooted: Boolean = false,
    val isDebuggable: Boolean = false
)
