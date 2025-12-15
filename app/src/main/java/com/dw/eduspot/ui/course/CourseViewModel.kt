package com.dw.eduspot.ui.course

import androidx.lifecycle.ViewModel
import com.dw.eduspot.data.fake.FakeCourseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CourseViewModel : ViewModel() {

    private val _purchased = MutableStateFlow(false)
    val purchased: StateFlow<Boolean> = _purchased

    fun buyCourse(course: Course) {
        FakeCourseRepository.addCourse(course)
        _purchased.value = true
    }
}