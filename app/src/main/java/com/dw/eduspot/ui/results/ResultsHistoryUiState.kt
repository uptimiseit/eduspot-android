package com.dw.eduspot.ui.results

data class ResultsHistoryItem(
    val attemptId: String,
    val testId: String,
    val title: String,
    val scoreText: String,
    val dateText: String,
    val attemptNumber: Int
)

data class ResultsHistoryUiState(
    val results: List<ResultsHistoryItem> = emptyList()
)