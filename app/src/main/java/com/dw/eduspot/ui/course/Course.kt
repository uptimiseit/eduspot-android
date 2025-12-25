package com.dw.eduspot.ui.course

data class Course(
    val id: String,
    val title: String,
    val subtitle: String? = "",
    val category: String?,
    val totalTests: Int,
    val language: String?,
    val mrp: Int,
    val offerPrice: Int,
    val isBestSeller: Boolean = false,
    val isNew: Boolean = false
)