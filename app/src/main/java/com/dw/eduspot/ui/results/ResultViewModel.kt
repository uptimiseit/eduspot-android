package com.dw.eduspot.ui.results

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dw.eduspot.domain.model.AttemptResult
import com.dw.eduspot.domain.repository.TestAttemptRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class ResultViewModel @Inject constructor(
    private val attemptRepo: TestAttemptRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ResultUiState())
    val uiState: StateFlow<ResultUiState> = _uiState

    fun loadResult(attemptId: String, testId: String) {
        viewModelScope.launch {
            val result = attemptRepo.getAttempt(attemptId, testId) ?: return@launch

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
    }

    private fun formatTime(seconds: Int): String {
        val m = seconds / 60
        val s = seconds % 60
        return "${m}m ${s}s"
    }
}