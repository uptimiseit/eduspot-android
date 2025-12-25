package com.dw.eduspot.ui.results

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.dw.eduspot.domain.model.QuestionResult

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultScreen(
    attemptId: String,
    testId: String,
    onBack: () -> Unit
) {
    val viewModel: ResultViewModel = hiltViewModel()
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadResult(attemptId, testId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Test Result") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Text("Back")
                    }
                }
            )
        }
    ) { padding ->

        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            item {
                ResultSummaryCard(state)
            }

            item { Spacer(Modifier.height(24.dp)) }

            items(state.questions.size) { index ->
                QuestionReviewCard(state.questions[index])
            }
        }
    }
}

@Composable
fun ResultSummaryCard(state: ResultUiState) {
    Card(
        Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            Modifier.padding(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(state.scoreText, style = MaterialTheme.typography.headlineSmall)
            Spacer(Modifier.height(8.dp))
            Text("Correct: ${state.correct}")
            Text("Wrong: ${state.wrong}")
            Text("Unattempted: ${state.unAttempted}")
            Text("Time Taken: ${state.timeTakenText}")
        }
    }
}

@Composable
fun QuestionReviewCard(question: QuestionResult) {
    Card(
        Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(question.question, style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(12.dp))

            question.options.forEachIndexed { i, opt ->
                val bg = when {
                    i == question.correctAnswerIndex -> MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
                    question.selectedAnswerIndex == i -> MaterialTheme.colorScheme.error.copy(alpha = 0.15f)
                    question.selectedAnswerIndex == null -> MaterialTheme.colorScheme.secondary.copy(alpha = 0.15f)
                    else -> MaterialTheme.colorScheme.surface
                }

                Surface(
                    Modifier.fillMaxWidth().padding(vertical = 4.dp),
                    color = bg,
                    shape = MaterialTheme.shapes.small
                ) {
                    Text(opt, Modifier.padding(12.dp))
                }
            }
        }
    }
}