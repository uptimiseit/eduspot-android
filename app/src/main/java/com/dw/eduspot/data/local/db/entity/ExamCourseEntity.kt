package com.dw.eduspot.data.local.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exam_courses")
data class ExamCourseEntity(
    @PrimaryKey val id: String,
    val courseId: String,
    val title: String,
    val totalTests: Int,
    val createdAt: Long = System.currentTimeMillis()
)