package com.dw.eduspot.ui.testengine

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import coil.compose.AsyncImage
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TestEngineScreen(
    attemptId: String,
    testId: String,
    onTestFinished: () -> Unit
) {    val viewModel: TestEngineViewModel = viewModel(
    factory = TestEngineViewModelFactory(
        attemptId = attemptId,
        testId = testId
    )
)

    val state by viewModel.uiState.collectAsState()

    // 🔒 Disable system back during test
    BackHandler(enabled = true) {}

    if (state.isFinished) {
        onTestFinished()
        return
    }

    val question = state.questions[state.currentIndex]
    val selectedAnswer = state.selectedAnswers[question.id]

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Q ${state.currentIndex + 1}/${state.questions.size}")
                },
                actions = {
                    Text(
                        text = formatTime(state.timeLeftSeconds),
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {

            // -------------------------
            // Question Text
            // -------------------------
            Text(
                text = question.question,
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(12.dp))

            // -------------------------
            // Optional Image
            // -------------------------
            question.imageUrl?.let { url ->
                AsyncImage(
                    model = url,
                    contentDescription = "Question Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 240.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            // -------------------------
            // Options A–D
            // -------------------------
            question.options.forEachIndexed { index, option ->
                AnswerOption(
                    text = option,
                    selected = selectedAnswer == index,
                    onClick = { viewModel.selectAnswer(index) }
                )
            }

            // -------------------------
            // Option E — Unattempted
            // -------------------------
            AnswerOption(
                text = "Unattempted",
                selected = selectedAnswer == 4,
                onClick = { viewModel.selectAnswer(4) },
                isUnattempted = true
            )

            Spacer(modifier = Modifier.height(24.dp))

            // -------------------------
            // Navigation Buttons
            // -------------------------
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                OutlinedButton(
                    onClick = { viewModel.previousQuestion() },
                    enabled = state.currentIndex > 0
                ) {
                    Text("Previous")
                }

                Button(
                    onClick = {
                        if (state.currentIndex == state.questions.lastIndex) {
                            viewModel.submitTest()
                        } else {
                            viewModel.nextQuestion()
                        }
                    }
                ) {
                    Text(
                        if (state.currentIndex == state.questions.lastIndex)
                            "Submit"
                        else
                            "Next"
                    )
                }
            }
        }
    }
}

// -------------------------
// Option Composable
// -------------------------
@Composable
private fun AnswerOption(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    isUnattempted: Boolean = false
) {
    val background =
        if (selected)
            MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
        else
            MaterialTheme.colorScheme.surface

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        color = background,
        shape = MaterialTheme.shapes.medium,
        tonalElevation = if (selected) 2.dp else 0.dp,
        onClick = onClick
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(14.dp),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

// -------------------------
// Timer Format
// -------------------------
private fun formatTime(seconds: Int): String {
    val min = seconds / 60
    val sec = seconds % 60
    return "%02d:%02d".format(min, sec)
}