package com.dw.eduspot.domain.model

data class TestAttempt(
    val attemptId: String,    // courseAttemptId
    val testId: String,       // REET-T1
    val score: Int,
    val correct: Int,
    val wrong: Int,
    val unAttempted: Int,
    val timeTakenSeconds: Int,
    val attemptedAt: Long
)