package com.dw.eduspot.data.fake

import com.dw.eduspot.domain.model.AttemptResult
import com.dw.eduspot.domain.model.QuestionResult
import com.dw.eduspot.ui.dashboard.model.ResumeCourseItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object FakeAttemptRepository {

    // -------------------------
    // In-memory attempt store
    // -------------------------
    private val attempts = mutableMapOf<String, AttemptResult>()

    private val _attemptsFlow =
        MutableStateFlow<List<AttemptResult>>(emptyList())
    val attemptsFlow: StateFlow<List<AttemptResult>> = _attemptsFlow

    // -------------------------
    // Save attempt (called from TestEngine)
    // -------------------------
    fun saveResult(
        testId: String,
        testName: String,
        totalQuestions: Int,
        correct: Int,
        wrong: Int,
        unAttempted: Int,
        score: Int,
        timeTakenSeconds: Int,
        questions: List<QuestionResult>
    ) {
        attempts[testId] = AttemptResult(
            testId = testId,
            testName = testName,
            totalQuestions = totalQuestions,
            correct = correct,
            wrong = wrong,
            unAttempted = unAttempted,
            score = score,
            timeTakenSeconds = timeTakenSeconds,
            attemptedAt = System.currentTimeMillis(),
            questions = questions
        )

        // 🔥 CRITICAL: notify observers
        _attemptsFlow.value = attempts.values.toList()
    }

    // -------------------------
    // Single attempt lookup
    // -------------------------
    fun getAttempt(testId: String): AttemptResult? {
        return attempts[testId]
    }

    // -------------------------
    // Results history
    // -------------------------
    fun getAllAttempts(): List<AttemptResult> {
        return attempts.values.sortedByDescending { it.attemptedAt }
    }

    // -------------------------
    // Single-attempt rule
    // -------------------------
    fun hasAttempted(testId: String): Boolean {
        return attempts.containsKey(testId)
    }

    // -------------------------
    // Resume section (Dashboard)
    // -------------------------
    fun getResumeCourses(): List<ResumeCourseItem> {
        return attempts.values
            .groupBy { it.testId.substringBefore("-") } // courseId
            .map { (courseId, courseAttempts) ->

                val totalTests = 2 // ⚠️ TEMP (later from Course data)
                val attemptedTests = courseAttempts.size
                val progressPercent = (attemptedTests * 100) / totalTests

                val lastCompleted = courseAttempts.maxBy { it.attemptedAt }

                ResumeCourseItem(
                    courseId = courseId,
                    courseTitle = lastCompleted.testName,
                    totalTests = totalTests,
                    attemptedTests = attemptedTests,
                    progressPercent = progressPercent,
                    nextTestId =
                        if (attemptedTests < totalTests)
                            "${courseId}-T${attemptedTests + 1}"
                        else null,
                    lastCompletedTestId = lastCompleted.testId
                )
            }
            .filter { it.nextTestId != null } // hide completed courses
    }
}