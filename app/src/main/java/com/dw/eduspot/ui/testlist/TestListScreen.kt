package com.dw.eduspot.ui.testlist

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
fun TestListScreen(
    courseId: String,
    attemptId: String,
    onStartTest: (String) -> Unit
) {
    val tests = TestFakeData.getTestsForCourse(courseId)

    val attempts by FakeCourseAttemptRepository.attempts.collectAsState()
    val courseAttempt = attempts.firstOrNull { it.attemptId == attemptId }

    val attemptNumber =
        attempts
            .filter { it.courseId == courseId }
            .sortedBy { it.startedAt }
            .indexOfFirst { it.attemptId == attemptId } + 1

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(courseAttempt?.courseTitle ?: "")
                        Text(
                            text = "Attempt #$attemptNumber",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            )
        }
    ) { padding ->

        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {

            items(tests) { test ->

                val isAttempted =
                    courseAttempt?.completedTestIds?.contains(test.id) == true

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp),
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {

                    Column(modifier = Modifier.padding(16.dp)) {

                        Text(
                            text = test.title,
                            style = MaterialTheme.typography.titleMedium
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = "${test.totalQuestions} Questions • ${test.durationMinutes} mins",
                            style = MaterialTheme.typography.bodyMedium
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Button(
                            onClick = { onStartTest(test.id) },
                            enabled = !isAttempted,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                if (isAttempted)
                                    "Already Attempted"
                                else
                                    "Start Test"
                            )
                        }

                        if (isAttempted) {
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "You can attempt this test only once",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                }
            }
        }
    }
}