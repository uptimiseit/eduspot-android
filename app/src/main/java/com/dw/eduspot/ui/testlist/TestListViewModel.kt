package com.dw.eduspot.ui.testlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dw.eduspot.domain.repository.TestAttemptRepository
import com.dw.eduspot.data.local.db.entity.CourseEntity
import com.dw.eduspot.data.repository.CourseRepository
import com.dw.eduspot.ui.testlist.model.TestListUiState
import com.dw.eduspot.ui.testlist.model.TestItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class TestListViewModel @Inject constructor(
    private val courseRepo: CourseRepository,
    private val attemptRepo: TestAttemptRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<TestListUiState>(TestListUiState.Loading)
    val uiState: StateFlow<TestListUiState> = _uiState

    init {
        loadTests()
    }

    private fun loadTests() {
        viewModelScope.launch {
            try {
                val courses: List<CourseEntity> = courseRepo.getCourseEntities() // fetch from DB if needed

                val testItems = mutableListOf<TestItem>()
                val attemptStatus = mutableMapOf<String, Boolean>()

                courses.forEach { course ->
                    val tests = courseRepo.getTestsForCourse(course.id) // you must implement this API later

                    tests.forEach { test ->
                        testItems.add(
                            TestItem(
                                id = test.id,
                                title = test.title,
                                totalQuestions = test.totalQuestions,
                                durationMinutes = test.durationMinutes
                            )
                        )

                        // 🔥 Correct: pass both attemptId and testId
                        val hasAttempt = attemptRepo.hasAttempted(
                            attemptId = test.id,
                            testId = test.id
                        )

                        attemptStatus[test.id] = hasAttempt
                    }
                }

                _uiState.value = TestListUiState.Success(
                    tests = testItems,
                    attempts = attemptStatus
                )

            } catch (e: Exception) {
                _uiState.value = TestListUiState.Error("Failed to load tests.")
            }
        }
    }
}