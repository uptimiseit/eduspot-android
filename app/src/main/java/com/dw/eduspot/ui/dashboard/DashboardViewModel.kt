package com.dw.eduspot.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dw.eduspot.data.local.db.dao.DashboardDao
import com.dw.eduspot.data.mapper.buildDashboardUi
import com.dw.eduspot.data.repository.DashboardRepository
import com.dw.eduspot.ui.common.UiState
import com.dw.eduspot.ui.dashboard.model.DashboardUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val repository: DashboardRepository,
    private val dao: DashboardDao
) : ViewModel() {

    private val _dashboardState = MutableStateFlow<UiState<DashboardUi>>(UiState.Loading)
    val dashboardState: StateFlow<UiState<DashboardUi>> = _dashboardState

    init { loadDashboard() }

    private fun loadDashboard() {
        viewModelScope.launch {
            try {
                repository.refreshDashboard()
                _dashboardState.value = UiState.Success(dao.buildDashboardUi())
            } catch (e: Exception) {
                _dashboardState.value = UiState.Error("Dashboard load failed", e)
            }
        }
    }
}