package com.dw.eduspot.ui.course

import androidx.lifecycle.ViewModel
import com.dw.eduspot.data.fake.FakeCourseAttemptRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CourseViewModel : ViewModel() {

    private val _purchaseResult = MutableStateFlow<String?>(null)
    val purchaseResult: StateFlow<String?> = _purchaseResult

    fun buyCourse(course: Course) {
        _purchaseResult.value =
            FakeCourseAttemptRepository.createCourseAttempt(
                courseId = course.id,
                courseTitle = course.title,
                totalTests = course.totalTests
            )
    }
}