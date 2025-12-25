package com.dw.eduspot.data.local.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "resume_courses")
data class ResumeCourseEntity(
    @PrimaryKey
    val attemptId: String,
    val courseId: String,
    val courseTitle: String,
    val totalTests: Int,
    val attemptedTests: Int,
    val lastCompletedTestId: String?
)
