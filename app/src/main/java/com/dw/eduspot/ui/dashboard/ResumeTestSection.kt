package com.dw.eduspot.ui.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ResumeTestSection(
    courses: List<ResumeCourseItem>,
    onResume: (ResumeCourseItem) -> Unit
) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(courses) { course ->
            ResumeCourseCard(
                course = course,
                onResume = {
                    onResume(course)
                }
            )
        }
    }
}