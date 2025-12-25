package com.dw.eduspot.ui.testengine

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dw.eduspot.domain.model.AttemptResult
import com.dw.eduspot.data.repository.DashboardRepository
import com.dw.eduspot.domain.model.QuestionResult
import com.dw.eduspot.domain.repository.TestAttemptRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class TestEngineViewModel @Inject constructor(
    private val attemptRepo: TestAttemptRepository,
    private val dashboardRepo: DashboardRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(TestUiState())
    val uiState: StateFlow<TestUiState> = _uiState

    fun startTest(
        attemptId: String,
        testId: String,
        questions: List<QuestionItem>,
        durationSeconds: Int
    ) {
        if (attemptRepo.hasAttempted(attemptId, testId)) {
            _uiState.value = _uiState.value.copy(isFinished = true)
            return
        }

        _uiState.value = TestUiState(
            questions = questions,
            currentIndex = 0,
            selectedAnswers = emptyMap(),
            timeLeftSeconds = durationSeconds,
            isFinished = false
        )

        // Start Timer
        viewModelScope.launch {
            while (_uiState.value.timeLeftSeconds > 0 && !_uiState.value.isFinished) {
                delay(1000)
                _uiState.value = _uiState.value.copy(
                    timeLeftSeconds = _uiState.value.timeLeftSeconds - 1
                )
            }
            if (!_uiState.value.isFinished) submitTestResult(attemptId, testId)
        }
    }

    fun selectAnswer(index: Int) {
        val q = _uiState.value.questions[_uiState.value.currentIndex]
        _uiState.value = _uiState.value.copy(
            selectedAnswers = _uiState.value.selectedAnswers + (q.id to index)
        )
    }

    fun nextQuestion() {
        if (_uiState.value.currentIndex < _uiState.value.questions.lastIndex) {
            _uiState.value = _uiState.value.copy(currentIndex = _uiState.value.currentIndex + 1)
        }
    }

    fun previousQuestion() {
        if (_uiState.value.currentIndex > 0) {
            _uiState.value = _uiState.value.copy(currentIndex = _uiState.value.currentIndex - 1)
        }
    }

    fun submitTest() {
        // UI calls this → triggers final submit
        submitTestResult(_uiState.value.attemptId, _uiState.value.testId)
    }

    // 🔥 Renamed to avoid collision
    private fun submitTestResult(attemptId: String, testId: String) {
        val state = _uiState.value
        val attempted = state.selectedAnswers.size
        val score = attempted

        attemptRepo.saveResult(
            AttemptResult(
                attemptId = attemptId,
                testId = testId,
                testName = "Mock Test",
                totalQuestions = state.questions.size,
                correct = score,
                wrong = 0,
                unAttempted = state.questions.size - attempted,
                score = score,
                timeTakenSeconds = 0,
                attemptedAt = System.currentTimeMillis(),
                questions = state.questions.map {
                    QuestionResult(
                        questionId = it.id,
                        question = it.question,
                        options = it.options,
                        correctAnswerIndex = it.correctAnswerIndex,
                        selectedAnswerIndex = state.selectedAnswers[it.id]
                    )
                }
            )
        )

        _uiState.value = state.copy(isFinished = true)
    }
}