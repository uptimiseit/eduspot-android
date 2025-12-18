package com.dw.eduspot.data.fake

import com.dw.eduspot.domain.model.AttemptResult
import com.dw.eduspot.domain.model.QuestionResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object FakeAttemptRepository {

    private val map = mutableMapOf<String, AttemptResult>()
    private val _flow = MutableStateFlow<List<AttemptResult>>(emptyList())
    val attemptsFlow: StateFlow<List<AttemptResult>> = _flow

    fun saveResult(
        attemptId: String,
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
        val key = "$attemptId-$testId"

        map[key] = AttemptResult(
            attemptId = attemptId,
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

        _flow.value = map.values.toList()
    }

    fun hasAttempted(attemptId: String, testId: String): Boolean =
        map.containsKey("$attemptId-$testId")

    fun getAttempt(attemptId: String, testId: String): AttemptResult? =
        map["$attemptId-$testId"]

    fun getAllAttempts(): List<AttemptResult> =
        map.values.sortedByDescending { it.attemptedAt }
}