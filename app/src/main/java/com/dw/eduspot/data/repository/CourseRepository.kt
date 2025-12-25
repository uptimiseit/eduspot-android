package com.dw.eduspot.data.repository

import com.dw.eduspot.data.local.db.entity.CourseEntity
import com.dw.eduspot.ui.testlist.model.TestItem
import com.dw.eduspot.ui.course.Course

interface CourseRepository {
    suspend fun getCourses(
        page: Int,
        category: String?,
        language: String?,
        priceType: String?,
        sort: String
    ): List<Course>

    suspend fun buyCourse(courseId: String): String

    suspend fun getCourseEntities(): List<CourseEntity>

    suspend fun getTestsForCourse(courseId: String): List<TestItem>
}

//package com.dw.eduspot.data.repository
//
//import com.dw.eduspot.data.remote.api.CourseApi
//import com.dw.eduspot.data.remote.dto.BuyCourseResponse
//import javax.inject.Inject
//
//class CourseRepository @Inject constructor(
//    private val api: CourseApi
//) {
//    suspend fun getCourses(/* existing code unchanged */) = api.buyCourse()
//
//    // 🔥 ADD THIS
//    suspend fun buyCourse(courseId: String): BuyCourseResponse {
//        return api.buyCourse(courseId)
//    }
//}