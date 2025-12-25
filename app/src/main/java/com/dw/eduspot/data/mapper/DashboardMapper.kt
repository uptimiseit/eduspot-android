package com.dw.eduspot.data.mapper

import com.dw.eduspot.data.local.db.entity.CourseEntity
import com.dw.eduspot.data.local.db.entity.ExamCategoryEntity
import com.dw.eduspot.data.local.db.entity.ResumeCourseEntity
import com.dw.eduspot.data.remote.dto.CourseDto
import com.dw.eduspot.data.remote.dto.DashboardResponse
import com.dw.eduspot.ui.dashboard.model.DashboardUi
import com.dw.eduspot.ui.dashboard.model.ExamCategory
import com.dw.eduspot.ui.dashboard.model.ResumeCourseItem
import com.dw.eduspot.ui.course.Course

fun CourseDto.toEntity(): CourseEntity = CourseEntity(
    id = id,
    title = title,
    subtitle = subtitle ?: "",
    category = category ?: "",
    language = language ?: "",
    totalTests = totalTests,
    mrp = mrp.toInt(),
    offerPrice = offerPrice.toInt(),
    isBestSeller = isBestSeller,
    isNew = isNew,
    updatedAt = updatedAt
)

fun CourseEntity.toUiModel(): Course = Course(
    id = id,
    title = title,
    subtitle = subtitle,
    category = category,
    totalTests = totalTests,
    language = category, // UI uses category for now
    description = subtitle,
    price = offerPrice
)

fun DashboardResponse.toUi(): DashboardUi = DashboardUi(
    categories = categories.map { ExamCategory(it.id, it.title) },
    popularCourses = popularCourses.map { it.toEntity().toUiModel() },
    newCourses = newCourses.map { it.toEntity().toUiModel() },
    resumeCourses = resumeCourses.map {
        ResumeCourseItem(
            attemptId = it.attemptId,
            courseId = it.courseId,
            courseTitle = it.courseTitle,
            attemptNumber = 1,
            totalTests = it.totalTests,
            attemptedTests = it.attemptedTests,
            progressPercent = if (it.totalTests == 0) 0 else (it.attemptedTests * 100) / it.totalTests,
            lastCompletedTestId = null
        )
    }
)