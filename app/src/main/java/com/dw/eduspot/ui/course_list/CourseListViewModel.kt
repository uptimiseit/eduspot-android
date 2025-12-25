package com.dw.eduspot.ui.course_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dw.eduspot.data.repository.CourseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CourseListViewModel @Inject constructor(
    private val repository: CourseRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CourseListUiState())
    val uiState: StateFlow<CourseListUiState> = _uiState

    fun refresh() = loadCourses(true)

    fun loadNextPage() {
        if (_uiState.value.endReached) return
        loadCourses(false)
    }

    fun applyFilters(category: String?, filters: CourseFilter, sort: SortOption) {
        _uiState.value = _uiState.value.copy(
            selectedCategory = category,
            filters = filters,
            sortOption = sort,
            page = 1,
            courses = emptyList(),
            endReached = false
        )
        loadCourses(true)
    }

    private fun loadCourses(reset: Boolean) {
        viewModelScope.launch {
            val s = _uiState.value
            _uiState.value = s.copy(isLoading = true)

            val data = repository.getCourses(
                page = s.page,
                category = s.selectedCategory,
                language = s.filters.language,
                priceType = s.filters.priceType?.name,
                sort = s.sortOption.name
            )

            _uiState.value = s.copy(
                isLoading = false,
                courses = if (reset) data else s.courses + data,
                page = s.page + 1,
                endReached = data.isEmpty()
            )
        }
    }
}