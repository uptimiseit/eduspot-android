package com.dw.eduspot.data.remote.dto

data class ExamCategoryDto(
    val id: String,
    val title: String
)

data class CourseDto(
    val id: String,
    val title: String,
    val subtitle: String? = null,
    val category: String? = null,
    val language: String? = null,
    val totalTests: Int,
    val mrp: Double,
    val offerPrice: Double,
    val isBestSeller: Boolean,
    val isNew: Boolean,
    val updatedAt: Long
)

data class ResumeCourseDto(
    val attemptId: String,
    val courseId: String,
    val courseTitle: String,
    val totalTests: Int,
    val attemptedTests: Int,
    val lastCompletedTestId: String? = null
)

data class DashboardResponse(
    val message: String,
    val categories: List<ExamCategoryDto>,
    val popularCourses: List<CourseDto>,
    val newCourses: List<CourseDto>,
    val resumeCourses: List<ResumeCourseDto>,
    val timestamp: Long
)