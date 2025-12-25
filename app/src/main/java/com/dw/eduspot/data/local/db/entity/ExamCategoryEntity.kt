package com.dw.eduspot.data.local.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exam_categories")
data class ExamCategoryEntity(
    @PrimaryKey val id: String,
    val title: String
)