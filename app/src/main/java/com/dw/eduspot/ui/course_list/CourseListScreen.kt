package com.dw.eduspot.ui.course_list

import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.*
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.dw.eduspot.ui.course_list.components.CourseListContent
import com.dw.eduspot.ui.course_list.components.FilterBottomSheet
import com.dw.eduspot.ui.course_list.components.FilterSidePanel
import com.dw.eduspot.utils.LocalAppWindowInfo

@Composable
fun CourseListScreen(
    viewModel: CourseListViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val windowInfo = LocalAppWindowInfo.current
    var showFilters by remember { mutableStateOf(false) }

    // 🔽 This was missing in your call
    Box(Modifier.fillMaxSize()) {
        if (showFilters) {
            if (windowInfo.isTablet) {
                FilterSidePanel(
                    currentFilters = uiState.activeFilters,
                    currentSort = uiState.sortOption,
                    onApply = { filters, sort ->
                        viewModel.applyFilters(uiState.selectedCategory, filters, sort)
                        showFilters = false
                    }
                )
            } else {
                FilterBottomSheet(
                    currentFilters = uiState.activeFilters,
                    currentSort = uiState.sortOption,
                    onApply = { filters, sort ->
                        viewModel.applyFilters(uiState.selectedCategory, filters, sort)
                        showFilters = false
                    },
                    onDismiss = { showFilters = false }
                )
            }
        }

        CourseListContent(
            uiState = uiState,
            onLoadMore = viewModel::loadNextPage,
            onRefresh = viewModel::refresh, // ✔ now passed
            onFilterClick = { showFilters = true }
        )
    }
}