package com.dw.eduspot.ui.dashboard.model

data class ResumeCourseItem(
    val courseId: String,
    val courseTitle: String,
    val progressPercent: Int,
    val lastTestId: String
)