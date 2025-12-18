package com.dw.eduspot.ui.attempts

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dw.eduspot.data.fake.FakeCourseAttemptRepository

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AttemptSelectionScreen(
    courseId: String,
    onAttemptSelected: (attemptId: String) -> Unit
) {
    val attempts by FakeCourseAttemptRepository.attempts.collectAsState()

    val courseAttempts =
        attempts
            .filter { it.courseId == courseId }
            .sortedBy { it.startedAt }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Select Attempt") }
            )
        }
    ) { padding ->

        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {

            items(courseAttempts) { attempt ->

                val attemptNumber =
                    courseAttempts.indexOf(attempt) + 1

                val progress =
                    if (attempt.totalTests == 0) 0
                    else (attempt.completedTestIds.size * 100) / attempt.totalTests

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp)
                        .clickable {
                            onAttemptSelected(attempt.attemptId)
                        },
                    elevation = CardDefaults.cardElevation(3.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {

                        val isCompleted =
                            attempt.completedTestIds.size >= attempt.totalTests

                        Text(
                            text = "Attempt #$attemptNumber • ${
                                if (isCompleted) "Completed" else "In Progress"
                            }",
                            style = MaterialTheme.typography.titleMedium
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        LinearProgressIndicator(
                            progress = { progress / 100f }
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text("$progress% completed")
                    }
                }
            }
        }
    }
}