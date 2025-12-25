package com.dw.eduspot.ui.results

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.clickable
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultsHistoryScreen(
    onOpenResult: (String, String) -> Unit,
    viewModel: ResultsHistoryViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    val results = state.results

    Scaffold(
        topBar = { TopAppBar(title = { Text("Results") }) },
    ) { padding ->

        if (results.isEmpty()) {
            Box(
                Modifier.fillMaxSize().padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text("No tests attempted yet", style = MaterialTheme.typography.bodyLarge)
            }
        } else {
            LazyColumn(
                Modifier.padding(padding).padding(16.dp),
            ) {
                items(results) { item ->
                    Card(
                        Modifier.fillMaxWidth().padding(bottom = 12.dp).clickable {
                            onOpenResult(item.attemptId, item.testId)
                        },
                        elevation = CardDefaults.cardElevation(2.dp)
                    ) {
                        Column(Modifier.padding(16.dp)) {
                            Text("${item.title} • Attempt #${item.attemptNumber}", style = MaterialTheme.typography.titleMedium)
                            Spacer(Modifier.height(6.dp))
                            Text(item.scoreText, style = MaterialTheme.typography.bodyMedium)
                            Spacer(Modifier.height(4.dp))
                            Text(item.dateText, style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
            }
        }
    }
}