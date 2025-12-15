package com.dw.eduspot.data.fake

import com.dw.eduspot.ui.course.Course
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object FakeCourseRepository {

    // -------------------------
    // Purchased courses
    // -------------------------
    private val _myCourses = MutableStateFlow<List<Course>>(emptyList())
    val myCourses: StateFlow<List<Course>> = _myCourses

    fun addCourse(course: Course) {
        if (_myCourses.value.none { it.id == course.id }) {
            _myCourses.value = _myCourses.value + course
        }
    }

    // -------------------------
    // Course → total tests mapping (NEW)
    // -------------------------
    private val courseTestCount = mapOf(
        "NEET" to 2,
        "JEE" to 3,
        "UPSC" to 5
    )

    fun getTotalTests(courseId: String): Int {
        return courseTestCount[courseId] ?: 1
    }
}