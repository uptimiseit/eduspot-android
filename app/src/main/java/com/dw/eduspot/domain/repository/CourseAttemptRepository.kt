package com.dw.eduspot.domain.repository

import com.dw.eduspot.domain.model.CourseAttempt
import kotlinx.coroutines.flow.StateFlow

interface CourseAttemptRepository {

    val attempts: StateFlow<List<CourseAttempt>>

    fun createCourseAttempt(
        courseId: String,
        courseTitle: String,
        totalTests: Int
    ): String

    fun markTestCompleted(
        attemptId: String,
        testId: String
    )
}