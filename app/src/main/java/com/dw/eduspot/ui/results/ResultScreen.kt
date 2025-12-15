package com.dw.eduspot.ui.results

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dw.eduspot.domain.model.QuestionResult

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultScreen(
    testId: String,
    onBack: () -> Unit
) {
    val viewModel: ResultViewModel = viewModel(
        factory = ResultViewModelFactory(testId)
    )

    val state by viewModel.uiState.collectAsState()

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
        ) {
            @Composable
           fun ResultSummaryCard(state: ResultUiState) {
                Card(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {

                        Text(
                            text = state.scoreText,
                            style = MaterialTheme.typography.headlineSmall
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text("Correct: ${state.correct}")
                        Text("Wrong: ${state.wrong}")
                        Text("Unattempted: ${state.unAttempted}")
                        Text("Time Taken: ${state.timeTakenText}")
                    }
                }
            }
            // -------------------------
            // Summary Section
            // -------------------------
            item {
                ResultSummaryCard(state)
            }
            @Composable
             fun QuestionReviewCard(question: QuestionResult) {

                Card(
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {

                        Text(
                            text = question.question,
                            style = MaterialTheme.typography.titleMedium
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        question.options.forEachIndexed { index, option ->
                            val backgroundColor = when {
                                index == question.correctAnswerIndex ->
                                    Color(0xFFB9F6CA) // correct (green)
                                question.selectedAnswerIndex == index ->
                                    Color(0xFFFFCDD2) // wrong (red)
                                question.selectedAnswerIndex == null ->
                                    Color(0xFFFFF9C4) // unattempted (yellow)
                                else ->
                                    MaterialTheme.colorScheme.surface
                            }

                            Surface(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                color = backgroundColor,
                                shape = MaterialTheme.shapes.small
                            ) {
                                Text(
                                    text = option,
                                    modifier = Modifier.padding(12.dp)
                                )
                            }
                        }
                    }
                }
            }
            // -------------------------
            // Question Review
            // -------------------------
            items(state.questions) { question ->
                QuestionReviewCard(question)
            }
        }
    }
}