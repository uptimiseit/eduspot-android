package com.dw.eduspot.ui.dashboard

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dw.eduspot.ui.dashboard.model.ResumeCourseItem

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