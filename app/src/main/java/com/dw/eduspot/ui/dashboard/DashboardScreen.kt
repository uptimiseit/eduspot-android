package com.dw.eduspot.ui.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel

@Composable
fun DashboardScreen(
    onOpenCourse: (String) -> Unit,
    onOpenTestList: (String, String) -> Unit,
    onResumeTest: (String) -> Unit,
    onOpenResult: (String) -> Unit,
    onOpenSettings: () -> Unit
) {

    val viewModel: DashboardViewModel = hiltViewModel()
    val resumeCourses by viewModel.resumeCourses.collectAsState()
    val categories by viewModel.categories.collectAsState()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 24.dp)
    ) {

        /** ---------------- HERO ---------------- **/
        item {
            AnimatedHeroSection(
                onExploreClick = {
                    // Open ALL courses screen
                    onOpenCourse("ALL")
                }
            )
        }

        /** ---------------- CATEGORIES ---------------- **/
        item {

            var selectedCategoryId by remember { mutableStateOf<String?>(null) }

            CategorySection(
                categories = categories,
                selectedCategoryId = selectedCategoryId,
                onAllClick = {
                    selectedCategoryId = null
                    onOpenCourse("ALL")
                },
                onCategoryClick = { category ->
                    selectedCategoryId = category.id
                    onOpenCourse(category.id)
                }
            )
        }
        item {
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            SectionHeader(
                title = "Popular Exams",
                subtitle = "Most purchased by students",
                onSeeAllClick = {
                    // navigate to course list (POPULAR)
                }
            )
        }
        item {
            Spacer(modifier = Modifier.height(12.dp))
        }

        item {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp)
            ) {
                items(DashboardFakeData.popularExams) { course ->
                    ExamCard(
                        course = course,
                        onBuyNow = {
                            // navigate to purchase / course detail
                            onOpenCourse(course.id)
                        },
                        onViewTests = {
                            // open bottom sheet later
                        }
                    )
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
        }

        item {
            Spacer(Modifier.height(32.dp))
            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 16.dp),
                color = MaterialTheme.colorScheme.outlineVariant
            )
            Spacer(Modifier.height(24.dp))
        }
        /** ---------------- Banner Section ---------------- **/
        item { BannerSection() }

        /** ---------------- NEW ---------------- **/
        item {
            Spacer(modifier = Modifier.height(24.dp))
        }
        item {
            SectionHeader(
                title = "Popular Exams",
                subtitle = "Most purchased by students",
                onSeeAllClick = {
                    // navigate to course list (POPULAR)
                }
            )

        }
        item {
            Spacer(modifier = Modifier.height(12.dp))
        }

        item {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp)
            ) {
                items(DashboardFakeData.newExams) { course ->
                    ExamCard(
                        course = course,
                        onBuyNow = {
                            // navigate to purchase / course detail
                            onOpenCourse(course.id)
                        },
                        onViewTests = {
                            // open bottom sheet later
                        }
                    )
                }
            }
        }

        /** ---------------- RESUME ---------------- **/
        if (resumeCourses.isNotEmpty()) {
            item {
                Spacer(Modifier.height(28.dp))
                Text(
                    text = "Continue Learning",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
            item {
                Spacer(modifier = Modifier.height(12.dp))
            }
            item {
                ResumeTestSection(
                    courses = resumeCourses,
                    onResume = { course ->
                        onOpenTestList(course.courseId, course.attemptId)
                    }
                )
            }
        }
        item {
            Spacer(modifier = Modifier.height(12.dp))
        }
        /** ---------------- POPULAR ---------------- **/
    }
}