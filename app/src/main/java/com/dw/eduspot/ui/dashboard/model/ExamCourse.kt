package com.dw.eduspot.ui.dashboard.model

data class ExamCourse(
    val id: String,
    val title: String,
    val subtitle: String,
    val category: String, // SSC, UPSC, etc.
    val totalTests: Int,
    val language: String,
    val mrp: Int,
    val offerPrice: Int,
    val isBestSeller: Boolean = false,
    val isNew: Boolean = false
)