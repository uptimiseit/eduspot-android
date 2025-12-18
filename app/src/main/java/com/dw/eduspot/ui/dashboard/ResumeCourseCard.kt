package com.dw.eduspot.ui.dashboard

import android.R.attr.progress
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dw.eduspot.ui.dashboard.model.ResumeCourseItem

@Composable
fun ResumeCourseCard(
    course: ResumeCourseItem,
    onResume: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(260.dp)
            .padding(end = 12.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Text(
                text = "${course.courseTitle} • Attempt #${course.attemptNumber}",
                style = MaterialTheme.typography.titleMedium
            )
            AssistChip(
                onClick = {},
                label = { Text("Attempt #${course.attemptNumber}") }
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Attempt ID: ${course.attemptId.takeLast(6)}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(8.dp))

            LinearProgressIndicator(
                progress = { progress / 100f }
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text("${course.progressPercent}% completed")

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = onResume,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Resume")
            }
        }
    }
}