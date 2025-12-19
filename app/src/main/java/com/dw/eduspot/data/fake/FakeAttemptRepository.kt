package com.dw.eduspot.data.fake

import com.dw.eduspot.domain.model.AttemptResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object FakeAttemptRepository {

    // 🔑 Key = attemptId-testId
    private val attempts = mutableMapOf<String, AttemptResult>()

    private val _attemptsFlow =
        MutableStateFlow<List<AttemptResult>>(emptyList())
    val attemptsFlow: StateFlow<List<AttemptResult>> = _attemptsFlow

    /* ---------------------------------------------------------
     * SAVE RESULT (single source of truth)
     * --------------------------------------------------------- */
    fun saveResult(result: AttemptResult) {
        val key = "${result.attemptId}-${result.testId}"
        attempts[key] = result
        _attemptsFlow.value = attempts.values.toList()
    }

    /* ---------------------------------------------------------
     * SINGLE-ATTEMPT GUARD
     * --------------------------------------------------------- */
    fun hasAttempted(
        attemptId: String,
        testId: String
    ): Boolean =
        attempts.containsKey("$attemptId-$testId")

    /* ---------------------------------------------------------
     * RESULT SCREENS
     * --------------------------------------------------------- */
    fun getAttempt(
        attemptId: String,
        testId: String
    ): AttemptResult? =
        attempts["$attemptId-$testId"]

    fun getAllAttempts(): List<AttemptResult> =
        attempts.values.sortedByDescending { it.attemptedAt }
}