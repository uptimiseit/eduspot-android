package com.dw.eduspot.domain.model

data class ResumeCourse(
    val courseId: String,
    val courseName: String,
    val attemptedTests: Int,
    val totalTests: Int,
    val lastAttemptedTestId: String,
    val progressPercent: Int
)