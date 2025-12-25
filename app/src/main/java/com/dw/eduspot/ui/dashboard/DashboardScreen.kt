package com.dw.eduspot.ui.dashboard

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.dw.eduspot.ui.common.UiState
import com.dw.eduspot.ui.dashboard.model.DashboardUi

@Composable
fun DashboardScreen(
    onOpenCourse: (String) -> Unit,
    onOpenTestList: (String, String) -> Unit,
    onResumeTest: (String) -> Unit,
    onOpenResult: (String) -> Unit
) {
    val viewModel = hiltViewModel<DashboardViewModel>()
    val uiState by viewModel.dashboardState.collectAsState()

    when (uiState) {
        UiState.Loading -> Box(Modifier.fillMaxSize(), Alignment.Center) { CircularProgressIndicator() }
        is UiState.Error -> Box(Modifier.fillMaxSize(), Alignment.Center) { Text((uiState as UiState.Error).message) }
        is UiState.Success -> {
            val data = (uiState as UiState.Success<DashboardUi>).data

            LazyColumn(Modifier.fillMaxSize().padding(16.dp)) {
                item { Text("🔥 Continue Learning", style = MaterialTheme.typography.titleLarge) }
                items(data.resumeCourses) { resume ->
                    Card(Modifier.fillMaxWidth().padding(bottom = 12.dp).clickable {
                        onOpenTestList(resume.courseId, resume.attemptId)
                    }) {
                        Column(Modifier.padding(16.dp)) {
                            Text(resume.courseTitle, fontWeight = FontWeight.Bold)
                            Text("Progress: ${resume.progressPercent}%")
                        }
                    }
                }

                item { Spacer(Modifier.height(32.dp)) }
                item { Text("⭐ Popular Exams", style = MaterialTheme.typography.titleLarge) }
                item {
                    LazyRow {
                        items(data.popularCourses) { course ->
                            Card(Modifier.padding(end = 12.dp).clickable { onOpenCourse(course.id) }) {
                                Column(Modifier.padding(12.dp)) {
                                    Text(course.title)
                                    Text("${course.totalTests} tests")
                                }
                            }
                        }
                    }
                }

                item { Spacer(Modifier.height(32.dp)) }
                item { Text("🆕 New Exams", style = MaterialTheme.typography.titleLarge) }
                items(data.newCourses) { course ->
                    Card(Modifier.fillMaxWidth().padding(bottom = 12.dp).clickable { onOpenCourse(course.id) }) {
                        Column(Modifier.padding(16.dp)) { Text(course.title, fontWeight = FontWeight.Bold) }
                    }
                }
            }
        }
    }
}