package com.dw.eduspot.ui.testengine

data class TestUiState(
    val attemptId: String = "",
    val testId: String = "",
    val questions: List<QuestionItem> = emptyList(),
    val currentIndex: Int = 0,
    val selectedAnswers: Map<String, Int> = emptyMap(),
    val timeLeftSeconds: Int = 0,
    val isFinished: Boolean = false
)