package com.dw.eduspot.domain.model

data class AttemptResult(
    val attemptId: String,   // 🔥 REQUIRED
    val testId: String,
    val testName: String,
    val totalQuestions: Int,
    val correct: Int,
    val wrong: Int,
    val unAttempted: Int,
    val score: Int,
    val timeTakenSeconds: Int,
    val attemptedAt: Long,
    val questions: List<QuestionResult>
)