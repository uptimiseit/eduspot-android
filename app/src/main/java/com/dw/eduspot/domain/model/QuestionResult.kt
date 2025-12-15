package com.dw.eduspot.domain.model

data class QuestionResult(
    val questionId: String,
    val question: String,
    val options: List<String>,
    val correctAnswerIndex: Int,
    val selectedAnswerIndex: Int? // null = unattempted
)