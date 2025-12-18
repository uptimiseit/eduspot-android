package com.dw.eduspot.ui.testengine

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class TestEngineViewModelFactory(
    private val attemptId: String,
    private val testId: String
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TestEngineViewModel::class.java)) {
            return TestEngineViewModel(
                attemptId = attemptId,
                testId = testId
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}