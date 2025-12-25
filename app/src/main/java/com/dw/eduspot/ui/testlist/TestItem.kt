package com.dw.eduspot.ui.testlist

data class TestItem(
    val id: String,
    val title: String,
    val totalQuestions: Int,
    val durationMinutes: Int,
    val courseId: String
)