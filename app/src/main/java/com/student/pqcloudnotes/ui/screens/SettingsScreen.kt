package com.student.pqcloudnotes.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.student.pqcloudnotes.BuildConfig
import androidx.lifecycle.viewmodel.compose.viewModel
import com.student.pqcloudnotes.data.model.CryptoSuite
import com.student.pqcloudnotes.ui.viewmodel.SettingsViewModel

@Composable
fun SettingsScreen(onBack: () -> Unit) {
    val viewModel: SettingsViewModel = viewModel()
    val state by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "Settings / Security Lab",
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Build Mode: ")
            Text(text = if (BuildConfig.INSECURE_MODE) "INSECURE" else "SECURE")
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Hybrid Locked: ")
            Text(text = BuildConfig.HYBRID_SUITE_LOCKED.toString())
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Crypto Suite: ${state.suite.id}")
        Text(text = "Key Version: ${state.keyVersion}")
        Text(text = "Device Risk: ${state.deviceRisk}")
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Select Suite:")
        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(
                selected = state.suite == CryptoSuite.CLASSICAL,
                onClick = { viewModel.selectSuite(CryptoSuite.CLASSICAL) }
            )
            Text(text = "CLASSICAL")
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(
                selected = state.suite == CryptoSuite.HYBRID_PQ_READY,
                onClick = { viewModel.selectSuite(CryptoSuite.HYBRID_PQ_READY) },
                enabled = !BuildConfig.HYBRID_SUITE_LOCKED
            )
            Text(text = "HYBRID_PQ_READY")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { viewModel.rotateKeys() }) {
            Text("Rotate Keys")
        }
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = onBack) {
            Text("Back")
        }
    }
}
