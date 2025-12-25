package com.dw.eduspot.data.mapper

import com.dw.eduspot.data.local.db.entity.CourseEntity
import com.dw.eduspot.data.local.db.entity.ExamCategoryEntity
import com.dw.eduspot.data.local.db.entity.ResumeCourseEntity
import com.dw.eduspot.ui.course.Course

fun ExamCategoryEntity.toUi(): ExamCategory =
    ExamCategory(id = id, title = title)

fun CourseEntity.toUi(): Course =
    Course(
        id = id,
        title = title,
        category = category,
        totalTests = totalTests,
        language = language,
        mrp = mrp,
        offerPrice = offerPrice,
        isBestSeller = isBestSeller,
        isNew = isNew
    )

fun ResumeCourseEntity.toUi(): ResumeCourseItem =
    ResumeCourseItem(
        attemptId = attemptId,
        courseId = courseId,
        courseTitle = courseTitle,
        attemptNumber = 1,
        totalTests = totalTests,
        attemptedTests = attemptedTests,
        progressPercent = if (totalTests == 0) 0 else (attemptedTests * 100) / totalTests,
        lastCompletedTestId = null
    )