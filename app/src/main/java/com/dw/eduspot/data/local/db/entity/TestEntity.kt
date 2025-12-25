package com.dw.eduspot.data.local.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tests")
data class TestEntity(
    @PrimaryKey val testId: String,
    val testTitle: String,
    val totalQuestions: Int,
    val durationMinutes: Int,
    val courseId: String
)