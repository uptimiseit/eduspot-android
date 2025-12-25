package com.dw.eduspot.ui.dashboard.model

import com.dw.eduspot.ui.course.Course

data class DashboardUi(
    val categories: List<ExamCategory>,
    val popularCourses: List<Course>,
    val newCourses: List<Course>,
    val resumeCourses: List<ResumeCourseItem>
)

data class ExamCategory(
    val id: String,
    val title: String
)

data class ResumeCourseItem(
    val attemptId: String,
    val courseId: String,
    val courseTitle: String,
    val attemptNumber: Int,
    val totalTests: Int,
    val attemptedTests: Int,
    val progressPercent: Int,
    val lastCompletedTestId: String?
)