package com.student.pqcloudnotes.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.student.pqcloudnotes.ui.viewmodel.NoteDetailViewModel

@Composable
fun NoteDetailScreen(onBack: () -> Unit) {
    val viewModel: NoteDetailViewModel = viewModel()
    val state by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text(text = "Note Detail", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedTextField(
            value = state.title,
            onValueChange = viewModel::updateTitle,
            label = { Text("Title") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = state.plaintext,
            onValueChange = viewModel::updatePlaintext,
            label = { Text("Plaintext") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { viewModel.encryptNow() }) {
            Text("Encrypt")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "suite_id: ${state.suiteId}")
        Text(text = "key_version: ${state.keyVersion}")
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "ciphertext:")
        Text(text = state.ciphertext.ifBlank { "(empty)" })
        Spacer(modifier = Modifier.height(12.dp))
        Button(onClick = { viewModel.saveNote() }) {
            Text("Save")
        }
        state.status?.let {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = it)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onBack) {
            Text("Back")
        }
    }
}
