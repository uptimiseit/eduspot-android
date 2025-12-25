package com.dw.eduspot.ui.course_list

data class FilterState(
    val category: String? = null,
    val language: String? = null,
    val priceType: PriceType? = null,
    val sort: SortType = SortType.POPULAR
)

enum class PriceType { FREE, PAID }

enum class SortType {
    POPULAR,
    PRICE_LOW_TO_HIGH,
    PRICE_HIGH_TO_LOW,
    NEWEST
}