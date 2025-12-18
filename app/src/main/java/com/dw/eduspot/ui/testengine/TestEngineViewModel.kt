package com.dw.eduspot.ui.testengine

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dw.eduspot.data.fake.*
import com.dw.eduspot.domain.model.QuestionResult
import com.dw.eduspot.utils.DemoConstants
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TestEngineViewModel(
    private val attemptId: String,
    private val testId: String
) : ViewModel() {

    private val isDemo = testId == DemoConstants.DEMO_TEST_ID
    private val totalTimeSeconds = 10 * 60

    private val questions =
        if (isDemo) DemoTestData.getDemoQuestions()
        else FakeQuestionData.getQuestions()

    private val _uiState = MutableStateFlow(
        TestUiState(
            questions = questions,
            timeLeftSeconds = totalTimeSeconds
        )
    )
    val uiState: StateFlow<TestUiState> = _uiState

    init {
        if (!isDemo &&
            FakeAttemptRepository.hasAttempted(attemptId, testId)
        ) {
            _uiState.value = _uiState.value.copy(isFinished = true)
        } else {
            startTimer()
        }
    }

    private fun startTimer() {
        viewModelScope.launch {
            while (_uiState.value.timeLeftSeconds > 0 &&
                !_uiState.value.isFinished
            ) {
                delay(1000)
                _uiState.value =
                    _uiState.value.copy(
                        timeLeftSeconds = _uiState.value.timeLeftSeconds - 1
                    )
            }
            if (!_uiState.value.isFinished) submitTest()
        }
    }

    fun selectAnswer(index: Int) {
        val state = _uiState.value
        val q = state.questions[state.currentIndex]
        val map = state.selectedAnswers.toMutableMap()
        map[q.id] = index
        _uiState.value = state.copy(selectedAnswers = map)
    }

    fun nextQuestion() {
        val s = _uiState.value
        if (s.currentIndex < s.questions.lastIndex)
            _uiState.value = s.copy(currentIndex = s.currentIndex + 1)
        else submitTest()
    }

    fun previousQuestion() {
        val s = _uiState.value
        if (s.currentIndex > 0)
            _uiState.value = s.copy(currentIndex = s.currentIndex - 1)
    }

    fun submitTest() {
        val s = _uiState.value
        if (s.isFinished) return

        val attempted = s.selectedAnswers.size
        val correct = attempted / 2

        FakeAttemptRepository.saveResult(
            attemptId,
            testId,
            if (isDemo) "Demo Test" else "Mock Test",
            s.questions.size,
            correct,
            attempted - correct,
            s.questions.size - attempted,
            correct,
            totalTimeSeconds - s.timeLeftSeconds,
            s.questions.map {
                QuestionResult(
                    it.id,
                    it.question,
                    it.options,
                    it.correctAnswerIndex,
                    s.selectedAnswers[it.id]
                )
            }
        )

        FakeCourseAttemptRepository.markTestCompleted(attemptId, testId)
        _uiState.value = s.copy(isFinished = true)
    }
}