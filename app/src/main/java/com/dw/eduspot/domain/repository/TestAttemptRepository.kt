package com.dw.eduspot.domain.repository

import com.dw.eduspot.domain.model.AttemptResult
import kotlinx.coroutines.flow.StateFlow

interface TestAttemptRepository {

    val attemptsFlow: StateFlow<List<AttemptResult>>

    fun saveResult(result: AttemptResult)

    fun hasAttempted(
        attemptId: String,
        testId: String
    ): Boolean

    fun getAttempt(
        attemptId: String,
        testId: String
    ): AttemptResult?
}