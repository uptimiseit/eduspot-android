package com.dw.eduspot.ui.course_list.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dw.eduspot.ui.course_list.CourseListUiState

@Composable
fun CourseListContent(
    uiState: CourseListUiState,
    onLoadMore: () -> Unit,
    onRefresh: () -> Unit,
    onFilterClick: () -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 24.dp)
    ) {

        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Courses",
                    style = MaterialTheme.typography.titleLarge
                )
                TextButton(onClick = onFilterClick) {
                    Text("Filter")
                }
            }
        }

        items(uiState.courses) { course ->
            CourseCard(course)
        }

        if (!uiState.endReached && !uiState.isLoading) {
            item {
                LaunchedEffect(Unit) {
                    onLoadMore()
                }
            }
        }

        if (uiState.isLoading) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}