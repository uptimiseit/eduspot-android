package com.dw.eduspot.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dw.eduspot.data.local.db.entity.CourseEntity
import com.dw.eduspot.data.local.db.entity.TestEntity

@Dao
interface CourseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(courses: List<CourseEntity>)

    @Query(
        """
        SELECT * FROM courses
        WHERE (:category IS NULL OR category = :category)
        ORDER BY updatedAt DESC
        LIMIT :limit OFFSET :offset
        """
    )
    suspend fun getCourses(
        category: String?,
        limit: Int,
        offset: Int
    ): List<CourseEntity>

    @Query("DELETE FROM courses")
    suspend fun clearAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTestEntities(testEntities: List<TestEntity>)

    @Query("SELECT * FROM tests WHERE courseId = :courseId")
    suspend fun getAllTestsForCourse(courseId: String): List<TestEntity>

    @Query("DELETE FROM tests")
    suspend fun clearTests()
}