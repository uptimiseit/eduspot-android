package com.dw.eduspot.ui.attempts

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class CourseAttemptUi(
    val attemptId: String,
    val courseId: String,
    val courseTitle: String,
    val totalTests: Int,
    val completedTestIds: List<String>,
    val startedAt: Long
)

interface CourseAttemptRepository {
    val attemptsFlow: StateFlow<List<CourseAttemptUi>>
    fun attempts(): List<CourseAttemptUi> = attemptsFlow.value
}

@Composable
fun AttemptSelectionScreen(
    courseId: String,
    onAttemptSelected: (String) -> Unit,
    repo: CourseAttemptRepository
) {
    val attempts by repo.attemptsFlow.collectAsState()

    val list =
        attempts.filter { it.courseId == courseId }
            .sortedBy { it.startedAt }

    if (list.isEmpty()) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("No attempts found")
        }
        return
    }

    LazyColumn(Modifier.fillMaxSize().padding(16.dp)) {
        items(list) { attempt ->
            val index = list.indexOf(attempt) + 1
            val progress =
                if (attempt.totalTests == 0) 0
                else (attempt.completedTestIds.size * 100) / attempt.totalTests

            Card(
                Modifier.fillMaxWidth().padding(bottom = 12.dp).clickable {
                    onAttemptSelected(attempt.attemptId)
                },
                elevation = CardDefaults.cardElevation(3.dp)
            ) {
                Column(Modifier.padding(16.dp)) {
                    Text("Attempt #$index • ${if (progress == 100) "Completed" else "In Progress"}")
                    Spacer(Modifier.height(6.dp))
                    LinearProgressIndicator(progress / 100f)
                    Spacer(Modifier.height(6.dp))
                    Text("$progress% completed")
                }
            }
        }
    }
}