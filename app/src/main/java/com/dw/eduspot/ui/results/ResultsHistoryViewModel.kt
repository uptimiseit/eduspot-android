package com.dw.eduspot.ui.results

import androidx.lifecycle.ViewModel
import com.dw.eduspot.data.fake.FakeAttemptRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.text.SimpleDateFormat
import java.util.*

class ResultsHistoryViewModel : ViewModel() {

    private val _results =
        MutableStateFlow<List<ResultListItem>>(emptyList())
    val results: StateFlow<List<ResultListItem>> = _results

    init {
        loadResults()
    }

    private fun loadResults() {
        val attempts = FakeAttemptRepository.getAllAttempts()
        val courseAttempts =
            attempts.groupBy { it.testId.substringBefore("-") }

        _results.value = attempts.map { attempt ->

            val attemptNumber =
                courseAttempts[attempt.testId.substringBefore("-")]
                    ?.sortedBy { it.attemptedAt }
                    ?.indexOfFirst { it.testId == attempt.testId }
                    ?.plus(1) ?: 1

            ResultListItem(
                attemptId = attempt.testId, // temp mapping
                testId = attempt.testId,
                title = attempt.testName,
                scoreText = "Score: ${attempt.score}/${attempt.totalQuestions}",
                dateText = formatDate(attempt.attemptedAt),
                attemptNumber = attemptNumber
            )
        }
    }

    private fun formatDate(timestamp: Long): String {
        val sdf = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        return sdf.format(Date(timestamp))
    }
}