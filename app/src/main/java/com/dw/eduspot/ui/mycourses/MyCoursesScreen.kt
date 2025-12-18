package com.dw.eduspot.ui.mycourses

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dw.eduspot.data.fake.FakeCourseAttemptRepository

@Composable
fun MyCoursesScreen(
    onOpenCourse: (String) -> Unit,
    onOpenTests: (String, String) -> Unit // courseId, attemptId
) {
    val attempts by FakeCourseAttemptRepository.attempts.collectAsState()

    if (attempts.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("No courses purchased yet")
        }
    } else {
        LazyColumn(modifier = Modifier.padding(16.dp)) {
            items(attempts) { attempt ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp)
                        .clickable {
                            onOpenTests(
                                attempt.courseId,
                                attempt.attemptId
                            )
                        }
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = attempt.courseTitle,
                            style = MaterialTheme.typography.titleMedium
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            "Progress: ${attempt.completedTestIds.size}/${attempt.totalTests}"
                        )
                    }
                }
            }
        }
    }
}