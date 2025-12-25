package com.dw.eduspot.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dw.eduspot.data.local.db.entity.ExamCategoryEntity
import com.dw.eduspot.data.local.db.entity.CourseEntity
import com.dw.eduspot.data.local.db.entity.ResumeCourseEntity
import com.dw.eduspot.data.mapper.toDomain
import com.dw.eduspot.ui.dashboard.model.DashboardUi

@Dao
interface DashboardDao {

    @Query("SELECT * FROM exam_categories")
    suspend fun getCategories(): List<ExamCategoryEntity>

    @Query("SELECT * FROM courses WHERE isBestSeller = 1")
    suspend fun getPopularCourses(): List<CourseEntity>

    @Query("SELECT * FROM courses WHERE isNew = 1")
    suspend fun getNewCourses(): List<CourseEntity>

    @Query("SELECT * FROM resume_courses")
    suspend fun getResumeCourses(): List<ResumeCourseEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategories(items: List<ExamCategoryEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCourses(items: List<CourseEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertResumeCourses(items: List<ResumeCourseEntity>)

}