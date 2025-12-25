package com.dw.eduspot.ui.results

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dw.eduspot.domain.repository.TestAttemptRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class ResultsHistoryViewModel @Inject constructor(
    private val attemptRepo: TestAttemptRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ResultsHistoryUiState())
    val uiState: StateFlow<ResultsHistoryUiState> = _uiState

    init {
        loadResults()
    }

    private fun loadResults() {
        viewModelScope.launch {
            val attempts = _uiState.value.results // start empty, then fill
            val repoAttempts = attemptRepo.attemptsFlow.value
            val grouped = repoAttempts.groupBy { it.testId }

            _uiState.value = ResultsHistoryUiState(
                results = repoAttempts.map { attempt ->
                    val attemptNumber =
                        grouped[attempt.testId]
                            ?.sortedBy { it.attemptedAt }
                            ?.indexOfFirst { it.testId == attempt.testId }
                            ?.plus(1) ?: 1

                    ResultsHistoryItem(
                        attemptId = attempt.attemptId,
                        testId = attempt.testId,
                        title = attempt.testName,
                        scoreText = "Score: ${attempt.score}/${attempt.totalQuestions}",
                        dateText = formatDate(attempt.attemptedAt),
                        attemptNumber = attemptNumber
                    )
                }
            )
        }
    }

    private fun formatDate(timestamp: Long): String {
        val sdf = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        return sdf.format(Date(timestamp))
    }
}