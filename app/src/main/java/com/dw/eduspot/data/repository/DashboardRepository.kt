package com.dw.eduspot.data.repository

import com.dw.eduspot.data.local.db.dao.DashboardDao
import com.dw.eduspot.data.local.db.entity.ExamCategoryEntity
import com.dw.eduspot.data.local.db.entity.ResumeCourseEntity
import com.dw.eduspot.data.mapper.toEntity
import com.dw.eduspot.data.mapper.toUiModel
import com.dw.eduspot.data.remote.api.DashboardApi
import com.dw.eduspot.ui.dashboard.model.DashboardUi
import javax.inject.Inject

class DashboardRepository @Inject constructor(
    private val api: DashboardApi,
    private val dao: DashboardDao
) {
    suspend fun refreshDashboard() {
        val r = api.getDashboard()

        val courseEntities = (r.popularCourses + r.newCourses).map {
            it.toEntity()  // ✅ FIXED: no args passed
        }

        val categoryEntities = r.categories.map {
            ExamCategoryEntity(it.id, it.title)
        }

        val resumeEntities = r.resumeCourses.map {
            ResumeCourseEntity(
                attemptId = it.attemptId,
                courseId = it.courseId,
                courseTitle = it.courseTitle,
                totalTests = it.totalTests,
                attemptedTests = it.attemptedTests,
                lastCompletedTestId = null
            )
        }

        dao.insertCategories(categoryEntities)
        dao.insertCourses(courseEntities)
        dao.insertResumeCourses(resumeEntities)
    }

    suspend fun getDashboardUi(): DashboardUi {
        return DashboardUi(
            categories = dao.getCategories().map { it.toUiModel() },
            popularCourses = dao.getPopularCourses().map { it.toUiModel() },
            newCourses = dao.getNewCourses().map { it.toUiModel() },
            resumeCourses = dao.getResumeCourses().map { it.toUiModel() }
        )
    }
}