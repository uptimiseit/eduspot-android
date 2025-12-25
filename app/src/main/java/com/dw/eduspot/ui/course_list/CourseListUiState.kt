package com.dw.eduspot.ui.course_list

import com.dw.eduspot.ui.course.Course

data class CourseListUiState(
    val isLoading: Boolean = false,
    val courses: List<Course> = emptyList(),
    val selectedCategory: String? = null,
    val filters: CourseFilter = CourseFilter(),
    val activeFilters: CourseFilter = CourseFilter(),
    val sortOption: SortOption = SortOption.POPULAR,
    val page: Int = 1,
    val endReached: Boolean = false,
    val errorMessage: String? = null
)

data class CourseFilter(
    val language: String? = null,
    val priceType: PriceType? = null
)

enum class SortOption {
    POPULAR,
    PRICE_LOW_TO_HIGH,
    PRICE_HIGH_TO_LOW,
    NEWEST
}

// ❗Removed enum class PriceType from here to avoid redeclaration
// It will now be imported from FilterState.kt