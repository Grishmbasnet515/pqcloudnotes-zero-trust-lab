package com.student.pqcloudnotes.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.student.pqcloudnotes.data.AppConfigStore
import com.student.pqcloudnotes.data.model.CryptoSuite
import com.student.pqcloudnotes.data.repo.SecurityEventsRepository
import com.student.pqcloudnotes.data.repo.impl.ApiSecurityEventsRepository
import com.student.pqcloudnotes.ui.state.SettingsUiState
import com.student.pqcloudnotes.security.DeviceSecurityChecker
import com.student.pqcloudnotes.data.auth.AppContextProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SettingsViewModel : ViewModel() {
    private val eventsRepository: SecurityEventsRepository = ApiSecurityEventsRepository()
    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState

    fun refreshPosture() {
        val posture = DeviceSecurityChecker.check(AppContextProvider.get())
        AppConfigStore.updatePosture(posture)
        _uiState.update {
            it.copy(
                isEmulator = posture.isEmulator,
                isRooted = posture.isRooted,
                isDebuggable = posture.isDebuggable
            )
        }
    }

    fun selectSuite(suite: CryptoSuite) {
        AppConfigStore.updateSuite(suite)
        _uiState.update { it.copy(suite = suite) }
    }

    fun rotateKeys() {
        AppConfigStore.incrementKeyVersion()
        _uiState.update { it.copy(keyVersion = it.keyVersion + 1) }
    }

    fun setSimulateCompromised(enabled: Boolean) {
        val risk = if (enabled) "HIGH" else "LOW"
        AppConfigStore.updateRisk(risk)
        _uiState.update { it.copy(simulateCompromised = enabled, deviceRisk = risk) }
    }

    fun loadEvents() {
        viewModelScope.launch {
            val events = eventsRepository.listEvents()
            _uiState.update { it.copy(events = events) }
        }
    }
}
