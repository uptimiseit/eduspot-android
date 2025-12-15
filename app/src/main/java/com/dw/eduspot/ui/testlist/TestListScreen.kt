package com.dw.eduspot.ui.testlist

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dw.eduspot.data.fake.FakeAttemptRepository

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TestListScreen(
    courseId: String,
    onStartTest: (String) -> Unit
) {
    val tests = TestFakeData.getTestsForCourse(courseId)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mock Tests") }
            )
        }
    ) { padding ->

        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {

            items(tests) { test ->

                val isAttempted =
                    FakeAttemptRepository.hasAttempted(test.id)

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp),
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {

                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {

                        Text(
                            text = test.title,
                            style = MaterialTheme.typography.titleMedium
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = "${test.totalQuestions} Questions • ${test.durationMinutes} mins",
                            style = MaterialTheme.typography.bodyMedium
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        // ✅ SINGLE SOURCE OF TRUTH FOR ATTEMPT
                        Button(
                            onClick = {
                                onStartTest(test.id)
                            },
                            enabled = !isAttempted,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = if (isAttempted)
                                    "Already Attempted"
                                else
                                    "Start Test"
                            )
                        }

                        // ✅ OPTIONAL MESSAGE
                        if (isAttempted) {
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "You can attempt this test only once",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                }
            }
        }
    }
}