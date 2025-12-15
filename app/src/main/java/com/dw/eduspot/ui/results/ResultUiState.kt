package com.dw.eduspot.ui.results

import com.dw.eduspot.domain.model.QuestionResult

data class ResultUiState(
    val testId: String = "",
    val scoreText: String = "",
    val correct: Int = 0,
    val wrong: Int = 0,
    val unAttempted: Int = 0,
    val timeTakenText: String = "",
    val questions: List<QuestionResult> = emptyList()
)