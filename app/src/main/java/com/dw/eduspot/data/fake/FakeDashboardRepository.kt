package com.dw.eduspot.data.fake

import com.dw.eduspot.domain.model.CourseItem
import com.dw.eduspot.ui.dashboard.model.ResumeCourseItem

object FakeDashboardRepository {

    // -----------------------------
    // Fake courses
    // -----------------------------
    private val courses = listOf(
        CourseItem(
            id = "NEET",
            title = "NEET Full Course",
            totalTests = 4
        ),
        CourseItem(
            id = "UPSC",
            title = "UPSC Prelims",
            totalTests = 3
        )
    )

    // -----------------------------
    // Resume courses (dashboard)
    // -----------------------------
    fun getResumeCourses(): List<ResumeCourseItem> {
        return listOf(
            ResumeCourseItem(
                courseId = "NEET",
                courseTitle = "NEET Full Course",
                progressPercent = 50,
                lastTestId = "NEET-T2"
            )
        )
    }

    // -----------------------------
    // Get course by ID (SAFE)
    // -----------------------------
    fun getCourseById(courseId: String): CourseItem? {
        return courses.find { it.id == courseId }
    }
}