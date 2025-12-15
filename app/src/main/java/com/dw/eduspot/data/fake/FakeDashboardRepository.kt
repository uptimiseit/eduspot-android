package com.dw.eduspot.data.fake

import com.dw.eduspot.ui.dashboard.model.ResumeCourseItem

/**
 * Fake data ONLY for dashboard preview / testing
 * Must strictly follow ResumeCourseItem contract
 */
object FakeDashboardRepository {

    fun getResumeCourses(): List<ResumeCourseItem> {

        // Fake scenario:
        // Course has 2 tests, user completed only 1
        val courseId = "NEET"
        val courseTitle = "NEET Full Course"

        val totalTests = 2
        val attemptedTests = 1   // ✅ THIS WAS MISSING / MIS-SCOPED

        val progressPercent = (attemptedTests * 100) / totalTests

        // If fully completed → do not show in resume
        if (attemptedTests >= totalTests) {
            return emptyList()
        }

        return listOf(
            ResumeCourseItem(
                courseId = courseId,
                courseTitle = courseTitle,

                totalTests = totalTests,
                attemptedTests = attemptedTests,
                progressPercent = progressPercent,

                // Resume opens NEXT test
                nextTestId = "$courseId-T${attemptedTests + 1}",

                // Result screen uses LAST completed test
                lastCompletedTestId = "$courseId-T$attemptedTests"
            )
        )
    }
}