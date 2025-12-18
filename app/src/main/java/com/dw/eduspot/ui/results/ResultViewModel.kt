package com.dw.eduspot.ui.results

import androidx.lifecycle.ViewModel
import com.dw.eduspot.data.fake.FakeAttemptRepository
import com.dw.eduspot.domain.model.AttemptResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ResultViewModel(
    private val attemptId: String,
    private val testId: String
) : ViewModel() {

    private val _uiState = MutableStateFlow(ResultUiState())
    val uiState: StateFlow<ResultUiState> = _uiState

    init {
        loadResult()
    }

    private fun loadResult() {
        val result =
            FakeAttemptRepository.getAttempt(attemptId, testId)
                ?: return

        _uiState.value = ResultUiState(
            testId = result.testId,
            scoreText = "Score: ${result.score}/${result.totalQuestions}",
            correct = result.correct,
            wrong = result.wrong,
            unAttempted = result.unAttempted,
            timeTakenText = formatTime(result.timeTakenSeconds),
            questions = result.questions
        )
    }

    private fun formatTime(seconds: Int): String {
        val m = seconds / 60
        val s = seconds % 60
        return "${m}m ${s}s"
    }
}