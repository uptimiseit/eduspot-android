package com.dw.eduspot.ui.testengine

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dw.eduspot.data.fake.DemoTestData
import com.dw.eduspot.data.fake.FakeAttemptRepository
import com.dw.eduspot.data.fake.FakeQuestionData
import com.dw.eduspot.domain.model.QuestionResult
import com.dw.eduspot.utils.DemoConstants
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TestEngineViewModel(
    private val testId: String
) : ViewModel() {

    private val isDemoTest = testId == DemoConstants.DEMO_TEST_ID
    private val totalTimeSeconds = 10 * 60 // 10 minutes

    // ✅ Load questions FIRST
    private val questions = if (isDemoTest) {
        DemoTestData.getDemoQuestions()
    } else {
        FakeQuestionData.getQuestions()
    }

    // ✅ UI State after questions are ready
    private val _uiState = MutableStateFlow(
        TestUiState(
            questions = questions,
            timeLeftSeconds = totalTimeSeconds
        )
    )
    val uiState: StateFlow<TestUiState> = _uiState

    private var timerJob: Job? = null

    init {
        // ✅ Single-attempt guard ONLY for paid tests
        if (!isDemoTest && FakeAttemptRepository.hasAttempted(testId)) {
            _uiState.value = _uiState.value.copy(isFinished = true)
        } else {
            startTimer()
        }
    }

    // ---------------- TIMER ----------------
    private fun startTimer() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (
                _uiState.value.timeLeftSeconds > 0 &&
                !_uiState.value.isFinished
            ) {
                delay(1000)
                _uiState.value = _uiState.value.copy(
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
        if (state.isFinished) return

        val question = state.questions[state.currentIndex]
        val updated = state.selectedAnswers.toMutableMap()
        updated[question.id] = index

        _uiState.value = state.copy(selectedAnswers = updated)
    }

    fun markUnattempted() {
        val state = _uiState.value
        if (state.isFinished) return

        val question = state.questions[state.currentIndex]
        val updated = state.selectedAnswers.toMutableMap()
        updated.remove(question.id)

        _uiState.value = state.copy(selectedAnswers = updated)
    }

    // ---------------- NAVIGATION ----------------
    fun nextQuestion() {
        val state = _uiState.value
        if (state.isFinished) return

        if (state.currentIndex < state.questions.lastIndex) {
            _uiState.value = state.copy(
                currentIndex = state.currentIndex + 1
            )
        } else {
            submitTest()
        }
    }

    fun previousQuestion() {
        if (_uiState.value.currentIndex > 0) {
            _uiState.value = _uiState.value.copy(
                currentIndex = _uiState.value.currentIndex - 1
            )
        }
    }

    fun skipQuestion() {
        nextQuestion()
    }

    // ---------------- SUBMIT ----------------
    fun submitTest() {
        val state = _uiState.value
        if (state.isFinished) return

        timerJob?.cancel()

        val totalQuestions = state.questions.size
        val attempted = state.selectedAnswers.size

        // --- fake scoring logic (replace later) ---
        val correct = attempted / 2
        val wrong = attempted - correct
        val unAttempted = totalQuestions - attempted
        val score = correct // simple score rule for now

        FakeAttemptRepository.saveResult(
            testId = testId,
            testName = if (isDemoTest) "Demo Test" else "Mock Test",
            totalQuestions = totalQuestions,
            correct = correct,
            wrong = wrong,
            unAttempted = unAttempted,
            score = score,
            timeTakenSeconds = totalTimeSeconds - state.timeLeftSeconds,
            questions = state.questions.map { q ->
                QuestionResult(
                    questionId = q.id,
                    question = q.question,
                    options = q.options,
                    correctAnswerIndex = q.correctAnswerIndex,
                    selectedAnswerIndex = state.selectedAnswers[q.id]
                )
            }
        )

        _uiState.value = state.copy(isFinished = true)
    }

    override fun onCleared() {
        timerJob?.cancel()
        super.onCleared()
    }
}