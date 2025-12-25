package com.dw.eduspot.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dw.eduspot.data.local.db.dao.CourseDao
import com.dw.eduspot.data.local.db.dao.DashboardDao
import com.dw.eduspot.data.local.db.entity.*

@Database(
    entities = [
        CourseEntity::class,
        ExamCourseEntity::class,
        ExamCategoryEntity::class,
        ResumeCourseEntity::class,
        TestEntity::class   // 🔥 ADD THIS
    ],
    version = 1,
    exportSchema = false
)
abstract class EduSpotDatabase : RoomDatabase() {
    abstract fun courseDao(): CourseDao
    abstract fun dashboardDao(): DashboardDao
}