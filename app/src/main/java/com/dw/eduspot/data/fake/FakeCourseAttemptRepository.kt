package com.dw.eduspot.data.fake

import com.dw.eduspot.domain.model.CourseAttempt
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.UUID

/**
 * Handles COURSE-LEVEL attempts only
 * (one purchase = one attempt)
 */
object FakeCourseAttemptRepository {

    private val _attempts =
        MutableStateFlow<List<CourseAttempt>>(emptyList())

    val attempts: StateFlow<List<CourseAttempt>> = _attempts

    /**
     * BUY COURSE → creates a NEW attempt
     * Re-purchase = new attemptId
     */
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

    /**
     * MARK TEST COMPLETED (inside an attempt)
     * Idempotent & safe
     */
    fun markTestCompleted(
        attemptId: String,
        testId: String
    ) {
        _attempts.value =
            _attempts.value.map { attempt ->
                if (attempt.attemptId == attemptId) {
                    attempt.copy(
                        completedTestIds =
                            (attempt.completedTestIds + testId).distinct()
                    )
                } else attempt
            }
    }

    /**
     * Helper — used by UI if needed
     */
    fun getAttempt(attemptId: String): CourseAttempt? =
        _attempts.value.firstOrNull { it.attemptId == attemptId }
}