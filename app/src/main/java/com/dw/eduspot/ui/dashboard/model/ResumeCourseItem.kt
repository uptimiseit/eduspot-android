package com.dw.eduspot.ui.dashboard.model

data class ResumeCourseItem(
    val attemptId: String,
    val courseId: String,
    val courseTitle: String,
    val attemptNumber: Int,        // 🔥 NEW
    val totalTests: Int,
    val attemptedTests: Int,
    val progressPercent: Int,
    val lastCompletedTestId: String?
)