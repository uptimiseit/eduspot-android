package com.dw.eduspot.ui.testlist.model

import com.dw.eduspot.ui.testlist.TestItem

sealed class TestListUiState {
    object Loading : TestListUiState()
    data class Error(val message: String) : TestListUiState()
    data class Success(
        val tests: List<TestItem>,
        val attempts: Map<String, Boolean>
    ) : TestListUiState()
}

