package com.dw.eduspot.ui.results

data class ResultsHistoryItem(
    val testId: String,
    val title: String,
    val scoreText: String,
    val dateText: String
)

data class ResultsHistoryUiState(
    val results: List<ResultsHistoryItem> = emptyList()
)