package com.dw.eduspot.ui.testengine

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class TestEngineViewModelFactory(
    private val testId: String
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TestEngineViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TestEngineViewModel(testId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}