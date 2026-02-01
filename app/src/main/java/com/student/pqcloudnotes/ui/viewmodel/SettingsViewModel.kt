package com.student.pqcloudnotes.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.student.pqcloudnotes.data.model.CryptoSuite
import com.student.pqcloudnotes.ui.state.SettingsUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class SettingsViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState

    fun selectSuite(suite: CryptoSuite) {
        _uiState.update { it.copy(suite = suite) }
    }

    fun rotateKeys() {
        _uiState.update { it.copy(keyVersion = it.keyVersion + 1) }
    }
}
