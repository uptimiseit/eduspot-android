package com.dw.eduspot.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dw.eduspot.data.fake.FakeCourseAttemptRepository
import com.dw.eduspot.ui.dashboard.model.ResumeCourseItem
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class DashboardViewModel : ViewModel() {

    val resumeCourses: StateFlow<List<ResumeCourseItem>> =
        FakeCourseAttemptRepository.attempts
            .map { attempts ->

                // Group attempts by course (NEET, UPSC, etc.)
                attempts
                    .groupBy { it.courseId }
                    .flatMap { (_, courseAttempts) ->

                        // Sort attempts chronologically → Attempt #1, #2, #3
                        courseAttempts
                            .sortedBy { it.startedAt }
                            .mapIndexedNotNull { index, attempt ->

                                val attemptedTests = attempt.completedTestIds.size

                                // ❌ Fully completed attempt → no resume card
                                if (attemptedTests >= attempt.totalTests) return@mapIndexedNotNull null

                                ResumeCourseItem(
                                    attemptId = attempt.attemptId,
                                    courseId = attempt.courseId,
                                    courseTitle = attempt.courseTitle,

                                    // ✅ STEP 9: visual separation
                                    attemptNumber = index + 1,

                                    totalTests = attempt.totalTests,
                                    attemptedTests = attemptedTests,
                                    progressPercent =
                                        (attemptedTests * 100) / attempt.totalTests,

                                    lastCompletedTestId =
                                        attempt.completedTestIds.lastOrNull()
                                )
                            }
                    }
            }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5_000),
                emptyList()
            )
}