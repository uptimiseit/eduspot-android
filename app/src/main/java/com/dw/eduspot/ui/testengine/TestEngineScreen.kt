package com.dw.eduspot.ui.testengine

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.ui.Alignment

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TestEngineScreen(
    attemptId: String,
    testId: String,
    onTestFinished: () -> Unit
) {
    val viewModel = hiltViewModel<TestEngineViewModel>()
    val state by viewModel.uiState.collectAsState()

    BackHandler(true) {}

    if (state.isFinished) {
        onTestFinished()
        return
    }

    val question = state.questions[state.currentIndex]

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Q ${state.currentIndex + 1}/${state.questions.size}") },
                actions = { Text("${state.timeLeftSeconds}s"); Spacer(Modifier.width(12.dp)) }
            )
        }
    ) { pad ->
        Column(
            Modifier.fillMaxSize().padding(pad).padding(16.dp).verticalScroll(rememberScrollState())
        ) {
            Text(question.question, style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.height(16.dp))
            question.options.forEachIndexed { i, opt ->
                Card(
                    Modifier.fillMaxWidth().padding(bottom = 12.dp).clickable { viewModel.selectAnswer(i) }
                ) { Text(opt, Modifier.padding(12.dp)) }
            }

            Spacer(Modifier.height(32.dp))
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                OutlinedButton({ viewModel.previousQuestion() }, enabled = state.currentIndex > 0) { Text("Prev") }
                Button({ if (state.currentIndex == state.questions.lastIndex) viewModel.submitTest() else viewModel.nextQuestion() }) {
                    Text(if (state.currentIndex == state.questions.lastIndex) "Submit" else "Next")
                }
            }
        }
    }
}