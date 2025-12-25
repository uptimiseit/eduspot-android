package com.dw.eduspot.data.remote.dto

data class DashboardResponseDto(
    val categories: List<ExamCategoryDto>,
    val popularCourses: List<CourseDto>,
    val newCourses: List<CourseDto>,
    val resumeCourses: List<ResumeCourseDto>
)