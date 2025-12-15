package com.dw.eduspot.data.fake

import com.dw.eduspot.ui.testengine.QuestionItem

object DemoTestData {

    fun getDemoQuestions(): List<QuestionItem> {
        return listOf(
            QuestionItem(
                id = "D1",
                question = "What is the capital of India?",
                options = listOf("Delhi", "Mumbai", "Kolkata", "Chennai", "Unattempted"),
                correctAnswerIndex = 0
            ),
            QuestionItem(
                id = "D2",
                question = "2 + 2 = ?",
                options = listOf("3", "4", "5", "6", "Unattempted"),
                correctAnswerIndex = 1
            )
        )
    }
}