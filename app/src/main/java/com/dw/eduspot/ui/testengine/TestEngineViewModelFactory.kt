package com.dw.eduspot.ui.testengine

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class TestEngineViewModelFactory(
    private val attemptId: String,
    private val testId: String
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(c: Class<T>): T {
        return TestEngineViewModel(attemptId, testId) as T
    }
}