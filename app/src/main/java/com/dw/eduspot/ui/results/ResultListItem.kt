package com.dw.eduspot.ui.results

data class ResultListItem(
    val attemptId: String,
    val testId: String,
    val title: String,
    val attemptNumber: Int, // 🔥
    val scoreText: String,
    val dateText: String
)