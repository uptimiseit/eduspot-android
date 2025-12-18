package com.dw.eduspot.ui.results

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ResultViewModelFactory(
    private val attemptId: String,
    private val testId: String
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ResultViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ResultViewModel(
                attemptId = attemptId,
                testId = testId
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}