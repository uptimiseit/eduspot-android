package com.dw.eduspot.ui.dashboard.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.dw.eduspot.ui.dashboard.ExamCard
import com.dw.eduspot.ui.course.Course
import com.dw.eduspot.utils.LocalAppWindowInfo

private val Int.id: String

@Composable
fun PopularCourseSection(
    courses: List<Course>,
    onBuyNow: (String) -> Unit
) {
    val windowInfo = LocalAppWindowInfo.current

    if (windowInfo.isTablet) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(courses) { course ->
                ExamCard(course) {
                    onBuyNow(course.id)
                }
            }
        }
    } else {
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(courses) { course ->
                ExamCard(course) {
                    onBuyNow(course.id)
                }
            }
        }
    }
}