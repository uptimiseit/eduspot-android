package com.dw.eduspot.ui.dashboard

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.TextFieldValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.dw.eduspot.utils.DemoConstants

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    onOpenCourse: (String) -> Unit,
    onResumeTest: (String) -> Unit,
    onOpenSettings: () -> Unit
) {

    val viewModel: DashboardViewModel = hiltViewModel()

    val resumeCourses by viewModel.resumeCourses.collectAsState()
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }

    var selectedCategory by remember {
        mutableStateOf(DashboardCategories.categories.first())
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("EDUSPOT") },
                actions = {
                    IconButton(onClick = onOpenSettings) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Settings"
                        )
                    }
                }
            )
        }
    ) { padding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {

            /* ---------------- SEARCH ---------------- */
            item {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("Search exams...") },
                    singleLine = true
                )
            }

            /* ---------------- CATEGORIES ---------------- */
            item {
                CategorySection(
                    categories = DashboardCategories.categories,
                    selectedCategoryId = selectedCategory.id,
                    onCategorySelected = { category ->
                        selectedCategory = category
                        // Later: ViewModel filter trigger
                    }
                )
            }

            /* ---------------- BANNER ---------------- */
            item {
                BannerSection()
            }

            /* ---------------- DEMO EXAM ---------------- */
            item {
                DemoExamCard(
                    onStartDemo = {
                        onResumeTest(DemoConstants.DEMO_TEST_ID)
                    }
                )
            }

            /* ---------------- RESUME TEST ---------------- */
            if (resumeCourses.isNotEmpty()) {
                item {
                    ResumeTestSection(
                        courses = resumeCourses,
                        onResumeTest = onResumeTest
                    )
                }
            }

            /* ---------------- POPULAR EXAMS ---------------- */
            item {
                SectionTitle("Popular Exams")
            }

            item {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp)
                ) {
                    items(DashboardFakeData.popularExams) { exam ->
                        ExamCard(
                            exam = exam,
                            onClick = { onOpenCourse(exam.id) }
                        )
                    }
                }
            }

            /* ---------------- NEW EXAMS ---------------- */
            item {
                SectionTitle("New Exams")
            }

            items(DashboardFakeData.newExams) { exam ->
                ExamListItem(exam = exam)
            }
        }
    }
}

/* ===================================================== */
/* ================== UI COMPONENTS ==================== */
/* ===================================================== */

@Composable
private fun ExamCard(
    exam: ExamItem,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(220.dp)
            .padding(end = 12.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = exam.title,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = exam.description,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
private fun ExamListItem(exam: ExamItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = exam.title,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = exam.description,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
private fun SectionTitle(title: String) {
    Text(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
        text = title,
        style = MaterialTheme.typography.titleLarge
    )
}