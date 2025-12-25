package com.dw.eduspot.data.local.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "courses")
data class CourseEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val subtitle: String?,
    val category: String?,
    val language: String?,
    val totalTests: Int,
    val mrp: Int,
    val offerPrice: Int,
    val isBestSeller: Boolean,
    val isNew: Boolean,
    val updatedAt: Long,

)