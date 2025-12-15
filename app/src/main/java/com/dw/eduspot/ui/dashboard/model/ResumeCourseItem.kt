package com.dw.eduspot.ui.dashboard.model

data class ResumeCourseItem(
    val courseId: String,
    val courseTitle: String,

    // Progress info
    val totalTests: Int,
    val attemptedTests: Int,
    val progressPercent: Int,

    // Navigation
    val nextTestId: String?,       // null = course completed
    val lastCompletedTestId: String? // used to open result
)