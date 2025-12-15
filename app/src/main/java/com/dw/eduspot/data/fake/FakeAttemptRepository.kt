package com.dw.eduspot.data.fake

import com.dw.eduspot.domain.model.AttemptResult
import com.dw.eduspot.domain.model.QuestionResult
import com.dw.eduspot.ui.dashboard.model.ResumeCourseItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object FakeAttemptRepository {

    private val attempts = mutableMapOf<String, AttemptResult>()
    private val _attemptsFlow =
        MutableStateFlow<List<AttemptResult>>(emptyList())

    val attemptsFlow: StateFlow<List<AttemptResult>> = _attemptsFlow

    // -------------------------
    // Save attempt (called ONCE from TestEngine)
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

        // 🔥 THIS IS THE KEY LINE
        _attemptsFlow.value = attempts.values.toList()
    }

    // -------------------------
    // Single attempt lookup
    // -------------------------
    fun getAttempt(testId: String): AttemptResult? {
        return attempts[testId]
    }

    // -------------------------
    // All attempts (Results History)
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
        return attempts.values.map {
            ResumeCourseItem(
                courseId = it.testId.substringBefore("-"),
                courseTitle = it.testName,
                progressPercent = ((it.correct + it.wrong) * 100) / it.totalQuestions,
                lastTestId = it.testId
            )
        }
    }
}