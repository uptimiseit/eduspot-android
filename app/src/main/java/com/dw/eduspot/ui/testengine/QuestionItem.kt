package com.dw.eduspot.ui.testengine

data class QuestionItem(
    val id: String,
    val question: String,
    val options: List<String>,
    val correctAnswerIndex: Int,
    val imageUrl: String? = null
)