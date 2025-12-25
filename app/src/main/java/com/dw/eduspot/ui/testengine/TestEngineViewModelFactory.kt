package com.dw.eduspot.ui.testengine

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dw.eduspot.data.repository.DashboardRepository
import com.dw.eduspot.domain.repository.TestAttemptRepository
import javax.inject.Inject

class TestEngineViewModelFactory @Inject constructor(
    private val attemptRepo: TestAttemptRepository,
    private val dashboardRepo: DashboardRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TestEngineViewModel::class.java)) {
            return TestEngineViewModel(attemptRepo, dashboardRepo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}