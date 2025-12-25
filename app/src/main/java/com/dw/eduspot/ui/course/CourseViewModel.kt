package com.dw.eduspot.ui.course

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dw.eduspot.data.repository.CourseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CourseViewModel @Inject constructor(
    private val courseRepository: CourseRepository
) : ViewModel() {

    private val _purchaseResult = MutableStateFlow<String?>(null)
    val purchaseResult: StateFlow<String?> = _purchaseResult

    fun buyCourse(courseId: String) {
        viewModelScope.launch {
            try {
                val attemptId = courseRepository.buyCourse(courseId)
                _purchaseResult.value = attemptId
            } catch (e: Exception) {
                _purchaseResult.value = null
            }
        }
    }

    fun clearPurchaseResult() {
        _purchaseResult.value = null
    }

}

//package com.dw.eduspot.ui.course
//
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.dw.eduspot.data.repository.CourseRepository
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.launch
//import javax.inject.Inject
//import dagger.hilt.android.lifecycle.HiltViewModel
//
//@HiltViewModel
//class CourseViewModel @Inject constructor(
//    private val courseRepository: CourseRepository
//) : ViewModel() {
//
//    private val _purchaseResult = MutableStateFlow<String?>(null)
//    val purchaseResult: StateFlow<String?> = _purchaseResult
//
//    fun buyCourse(courseId: String) {
//        viewModelScope.launch {
//            try {
//                val response = courseRepository.buyCourse(courseId)
//                _purchaseResult.value = response.attemptId  // fixed field reference
//            } catch (e: Exception) {
//                _purchaseResult.value = null
//            }
//        }
//    }
//}