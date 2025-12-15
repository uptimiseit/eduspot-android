package com.dw.eduspot.ui.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dw.eduspot.ui.dashboard.model.ResumeCourseItem

@Composable
fun ResumeTestSection(
    courses: List<ResumeCourseItem>,
    onResumeTest: (String) -> Unit
) {
    if (courses.isEmpty()) return

    Column {
        Text(
            text = "Resume Your Tests",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
        )

        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(courses) { course ->
                ResumeCourseCard(
                    course = course,
                    onResume = {
                        onResumeTest(course.lastTestId)
                    }
                )
            }
        }
    }
}