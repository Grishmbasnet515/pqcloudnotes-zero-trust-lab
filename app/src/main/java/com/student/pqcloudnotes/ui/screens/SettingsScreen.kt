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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.student.pqcloudnotes.BuildConfig

@Composable
fun SettingsScreen(onBack: () -> Unit) {
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
        Text(text = "Crypto Suite: (placeholder)")
        Text(text = "Key Version: (placeholder)")
        Text(text = "Device Risk: (placeholder)")
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = onBack) {
            Text("Back")
        }
    }
}
