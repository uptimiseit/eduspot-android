package com.dw.eduspot.data.remote.api

import com.dw.eduspot.data.remote.dto.BuyCourseResponse
import com.dw.eduspot.data.remote.dto.CourseDto
import com.dw.eduspot.data.remote.dto.TestDto
import com.dw.eduspot.ui.testlist.model.TestItem
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface CourseApi {

    @GET("lapi/courses")
    suspend fun getCourses(
        @Query("page") page: Int,
        @Query("category") category: String?,
        @Query("language") language: String?,
        @Query("priceType") priceType: String?,
        @Query("sort") sort: String
    ): List<CourseDto>

    @POST("lapi/courses/buy/{courseId}")
    suspend fun buyCourse(
        @Path("courseId") courseId: String
    ): BuyCourseResponse


    // ✅ Fixed return type (no unresolved DTO anymore)
    @GET("courses/tests")
    suspend fun getTestsForCourse(
        @Query("courseId") courseId: String
    ): List<TestDto>

}



//package com.dw.eduspot.data.remote.api
//
//import com.dw.eduspot.data.remote.dto.BuyCourseResponse
//import retrofit2.http.POST
//import retrofit2.http.Path
//
//interface CourseApi {
//
//    @POST("lapi/courses/buy/{courseId}")
//    suspend fun buyCourse(
//        @Path("courseId") courseId: String
//    ): BuyCourseResponse
//
//    // other functions unchanged
//}