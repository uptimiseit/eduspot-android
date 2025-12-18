package com.dw.eduspot.domain.model

data class CourseAttempt(
    val attemptId: String,        // unique (UUID)
    val courseId: String,         // REET
    val courseTitle: String,      // REET Full Course
    val totalTests: Int,
    val startedAt: Long,
    val completedTestIds: List<String> // e.g. ["REET-T1"]
)