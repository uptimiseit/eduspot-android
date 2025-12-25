package com.dw.eduspot.data.repository

import com.dw.eduspot.data.local.db.dao.CourseDao
import com.dw.eduspot.data.mapper.toDomain
import com.dw.eduspot.data.mapper.toEntity
import com.dw.eduspot.data.remote.api.CourseApi
import com.dw.eduspot.data.mapper.toTestEntity
import com.dw.eduspot.ui.testlist.model.TestItem
import com.dw.eduspot.ui.course.Course

import javax.inject.Inject
import kotlin.collections.map

class CourseRepositoryImpl @Inject constructor(
    private val api: CourseApi,
    private val dao: CourseDao
) : CourseRepository {

    override suspend fun getCourses(
        page: Int,
        category: String?,
        language: String?,
        priceType: String?,
        sort: String
    ): List<Course> = try {
        val remote = api.getCourses(page, category, language, priceType, sort)
        val entities = remote.map { it.toEntity() }
        dao.insertAll(entities)
        entities.map { it.toDomain() }
    } catch (e: Exception) {
        dao.getCourseList().map { it.toDomain() }  // ✅ FIXED: correct dao call
    }

    override suspend fun buyCourse(courseId: String): String {
        val response = api.buyCourse(courseId)
        return response.attemptId
    }

    override suspend fun getCourseEntities(): List<CourseEntity> {
        return dao.getCourseList()  // ✅ FIXED: consistent function
    }

    override suspend fun getTestsForCourse(courseId: String): List<TestItem> {
        val remote = api.getTestsForCourse(courseId)
        val entities = remote.map { it.toTestEntity(courseId) }
        dao.insertTestEntities(entities)

        return entities.map {
            TestItem(
                id = it.testId,
                title = it.testTitle,
                totalQuestions = it.totalQuestions,
                durationMinutes = it.durationMinutes,
                courseId = it.courseId
            )
        }
    }
}