package com.dw.eduspot.data.mapper

import com.dw.eduspot.data.local.db.entity.CourseEntity
import com.dw.eduspot.data.remote.dto.CourseDto
import com.dw.eduspot.ui.course.Course

fun CourseDto.toEntity(): CourseEntity = CourseEntity(
    id = id,
    title = title,
    subtitle = subtitle ?: "",
    category = category ?: "GENERAL",
    language = language ?: "HINDI",
    totalTests = totalTests,
    mrp = mrp.toInt(),
    offerPrice = offerPrice.toInt(),
    isBestSeller = isBestSeller,
    isNew = isNew,
    updatedAt = updatedAt
)

fun CourseEntity.toDomain(): Course = Course(
    id = id,
    title = title,
    subtitle = subtitle,
    category = category,
    totalTests = totalTests,
    language = language,
    mrp = mrp,
    offerPrice = offerPrice,
    isBestSeller = isBestSeller,
    isNew = isNew
)