package com.dw.eduspot.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dw.eduspot.data.fake.FakeAttemptRepository
import com.dw.eduspot.ui.dashboard.model.ResumeCourseItem
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class DashboardViewModel : ViewModel() {

    /**
     * 🔥 Reactive resume section
     * Automatically updates when a test is submitted
     */
    val resumeCourses: StateFlow<List<ResumeCourseItem>> =
        FakeAttemptRepository.attemptsFlow
            .map {
                FakeAttemptRepository.getResumeCourses()
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = emptyList()
            )
}