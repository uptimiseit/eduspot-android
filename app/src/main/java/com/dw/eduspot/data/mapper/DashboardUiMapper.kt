package com.dw.eduspot.data.mapper

import com.dw.eduspot.data.local.db.dao.DashboardDao
import com.dw.eduspot.ui.dashboard.model.DashboardUi
import com.dw.eduspot.data.local.db.entity.ResumeCourseEntity
import com.dw.eduspot.data.local.db.entity.ExamCategoryEntity
import com.dw.eduspot.data.local.db.entity.CourseEntity
import com.dw.eduspot.data.mapper.toDomain

import kotlinx.coroutines.runBlocking

fun DashboardDao.buildDashboardUi(): DashboardUi = runBlocking {
    DashboardUi(
        categories = getCategories().map { ExamCategory(it.id, it.title) },
        popularCourses = getPopularCourses().map { it.toDomain() },
        newCourses = getNewCourses().map { it.toDomain() },
        resumeCourses = getResumeCourses().map {
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
}