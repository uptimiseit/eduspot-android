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

@Composable
fun MyCoursesScreen(
    onOpenCourse: (String) -> Unit,
    onOpenTests: (String, String) -> Unit
) {
    // Using an empty state placeholder until real DI repository is wired
    val attempts = remember { emptyList<CourseAttemptUi>() }

    if (attempts.isEmpty()) {
        Box(
            Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("No courses purchased yet")
        }
    } else {
        LazyColumn(Modifier.padding(16.dp)) {
            items(attempts) { attempt ->
                Card(
                    Modifier.fillMaxWidth().padding(bottom = 12.dp).clickable {
                        onOpenTests(attempt.courseId, attempt.attemptId)
                    }
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Text(attempt.courseTitle, style = MaterialTheme.typography.titleMedium)
                        Spacer(Modifier.height(4.dp))
                        Text("Progress: ${attempt.completedTestIds.size}/${attempt.totalTests}")
                    }
                }
            }
        }
    }
}

data class CourseAttemptUi(
    val attemptId: String,
    val courseId: String,
    val courseTitle: String,
    val totalTests: Int,
    val completedTestIds: List<String>,
    val startedAt: Long
)