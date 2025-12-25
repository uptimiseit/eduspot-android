package com.dw.eduspot.ui.testlist

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.clickable
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dw.eduspot.navigation.Routes
import com.dw.eduspot.domain.model.AttemptResult
import com.dw.eduspot.ui.testlist.model.TestItem
import com.dw.eduspot.ui.testlist.model.TestListUiState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TestListScreen(
    courseId: String,
    attemptId: String,
    onStartTest: (String) -> Unit,
    viewModel: TestListViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val coroutineScope = rememberCoroutineScope()
    val localContext = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Your Tests") }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        when (uiState) {
            is TestListUiState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is TestListUiState.Error -> {
                val msg = (uiState as TestListUiState.Error).message
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(msg, style = MaterialTheme.typography.bodyLarge)
                }
            }

            is TestListUiState.Success -> {
                val data = (uiState as TestListUiState.Success).tests
                val attemptMap = (uiState as TestListUiState.Success).attempts

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(data) { test ->
                        val isAttempted = attemptMap[test.id] == true

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable(enabled = !isAttempted) {
                                    if (!isAttempted) {
                                        onStartTest(test.id)
                                    } else {
                                        coroutineScope.launch {
                                            snackbarHostState.showSnackbar("Already attempted, can't redo.")
                                        }
                                    }
                                },
                            elevation = CardDefaults.cardElevation(2.dp)
                        ) {
                            Column(Modifier.padding(16.dp)) {
                                Text(test.title, style = MaterialTheme.typography.titleMedium)
                                Spacer(Modifier.height(6.dp))
                                Text("${test.totalQuestions} Questions • ${test.durationMinutes} mins",
                                    style = MaterialTheme.typography.bodyMedium)
                                Spacer(Modifier.height(12.dp))

                                Button(
                                    onClick = {
                                        if (!isAttempted) {
                                            onStartTest(test.id)
                                        } else {
                                            coroutineScope.launch {
                                                snackbarHostState.showSnackbar("Already attempted, can't redo.")
                                            }
                                        }
                                    },
                                    enabled = !isAttempted,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(if (isAttempted) "Already Attempted" else "Start Test")
                                }

                                if (isAttempted) {
                                    Spacer(Modifier.height(6.dp))
                                    Text("You can attempt this test only once",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.error)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}