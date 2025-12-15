package com.dw.eduspot.data.fake

import com.dw.eduspot.ui.testengine.QuestionItem

object FakeQuestionData {

    fun getQuestions(): List<QuestionItem> {
        return listOf(
            QuestionItem(
                id = "Q1",
                question = "What is the capital of India?",
                options = listOf(
                    "Delhi",
                    "Mumbai",
                    "Kolkata",
                    "Chennai"
                ),
                correctAnswerIndex = 0
            ),
            QuestionItem(
                id = "Q2",
                question = "Which planet is known as the Red Planet?",
                options = listOf(
                    "Earth",
                    "Mars",
                    "Jupiter",
                    "Venus"
                ),
                correctAnswerIndex = 1,
                imageUrl = "https://upload.wikimedia.org/wikipedia/commons/0/02/OSIRIS_Mars_true_color.jpg"
            )
        )
    }
}