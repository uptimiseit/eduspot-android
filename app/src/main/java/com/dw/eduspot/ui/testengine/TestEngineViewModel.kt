package com.dw.eduspot.ui.testengine

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dw.eduspot.data.fake.DemoTestData
import com.dw.eduspot.data.fake.FakeAttemptRepository
import com.dw.eduspot.data.fake.FakeCourseAttemptRepository
import com.dw.eduspot.data.fake.FakeQuestionData
import com.dw.eduspot.domain.model.AttemptResult
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

    // ---------------- QUESTIONS ----------------
    private val questions =
        if (isDemo) DemoTestData.getDemoQuestions()
        else FakeQuestionData.getQuestions()

    // ---------------- UI STATE ----------------
    private val _uiState = MutableStateFlow(
        TestUiState(
            questions = questions,
            timeLeftSeconds = totalTimeSeconds
        )
    )
    val uiState: StateFlow<TestUiState> = _uiState

    init {
        // 🔒 Single-attempt guard (per attemptId)
        if (!isDemo &&
            FakeAttemptRepository.hasAttempted(attemptId, testId)
        ) {
            _uiState.value = _uiState.value.copy(isFinished = true)
        } else {
            startTimer()
        }
    }

    // ---------------- TIMER ----------------
    private fun startTimer() {
        viewModelScope.launch {
            while (
                _uiState.value.timeLeftSeconds > 0 &&
                !_uiState.value.isFinished
            ) {
                delay(1000)
                _uiState.value =
                    _uiState.value.copy(
                        timeLeftSeconds = _uiState.value.timeLeftSeconds - 1
                    )
            }

            if (!_uiState.value.isFinished) {
                submitTest()
            }
        }
    }

    // ---------------- ANSWERS ----------------
    fun selectAnswer(index: Int) {
        val state = _uiState.value
        val question = state.questions[state.currentIndex]
        val updated = state.selectedAnswers.toMutableMap()
        updated[question.id] = index
        _uiState.value = state.copy(selectedAnswers = updated)
    }

    fun nextQuestion() {
        val state = _uiState.value
        if (state.currentIndex < state.questions.lastIndex) {
            _uiState.value =
                state.copy(currentIndex = state.currentIndex + 1)
        } else {
            submitTest()
        }
    }

    fun previousQuestion() {
        val state = _uiState.value
        if (state.currentIndex > 0) {
            _uiState.value =
                state.copy(currentIndex = state.currentIndex - 1)
        }
    }

    // ---------------- SUBMIT ----------------
    fun submitTest() {
        val state = _uiState.value
        if (state.isFinished) return

        val attempted = state.selectedAnswers.size
        val correct = attempted / 2
        val wrong = attempted - correct
        val unAttempted = state.questions.size - attempted

        val questionResults =
            state.questions.map { q ->
                QuestionResult(
                    questionId = q.id,
                    question = q.question,
                    options = q.options,
                    correctAnswerIndex = q.correctAnswerIndex,
                    selectedAnswerIndex = state.selectedAnswers[q.id]
                )
            }

        // ✅ SAVE RESULT (final API)
        FakeAttemptRepository.saveResult(
            AttemptResult(
                attemptId = attemptId,
                testId = testId,
                testName = if (isDemo) "Demo Test" else "Mock Test",
                totalQuestions = state.questions.size,
                correct = correct,
                wrong = wrong,
                unAttempted = unAttempted,
                score = correct,
                timeTakenSeconds =
                    totalTimeSeconds - state.timeLeftSeconds,
                attemptedAt = System.currentTimeMillis(),
                questions = questionResults
            )
        )

        // 🔹 Mark progress for course attempt
        FakeCourseAttemptRepository.markTestCompleted(
            attemptId = attemptId,
            testId = testId
        )

        _uiState.value = state.copy(isFinished = true)
    }
}