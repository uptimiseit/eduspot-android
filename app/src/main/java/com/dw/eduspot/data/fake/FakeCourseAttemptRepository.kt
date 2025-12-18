package com.dw.eduspot.data.fake

import com.dw.eduspot.domain.model.CourseAttempt
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.UUID

object FakeCourseAttemptRepository {

    private val _attempts = MutableStateFlow<List<CourseAttempt>>(emptyList())
    val attempts: StateFlow<List<CourseAttempt>> = _attempts

    fun createCourseAttempt(
        courseId: String,
        courseTitle: String,
        totalTests: Int
    ): String {
        val attemptId = "$courseId-${UUID.randomUUID()}"

        _attempts.value += CourseAttempt(
            attemptId = attemptId,
            courseId = courseId,
            courseTitle = courseTitle,
            totalTests = totalTests,
            startedAt = System.currentTimeMillis(),
            completedTestIds = emptyList()
        )

        return attemptId
    }

    fun markTestCompleted(attemptId: String, testId: String) {
        _attempts.value = _attempts.value.map {
            if (it.attemptId == attemptId)
                it.copy(
                    completedTestIds = (it.completedTestIds + testId).distinct()
                )
            else it
        }
    }
}