package com.dw.eduspot.ui.guidelines

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TestGuidelinesScreen(
    testId: String,
    onReady: () -> Unit,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Test Guidelines") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = androidx.compose.material.icons.Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            Column {
                Text(
                    text = "Please read carefully before starting the test:",
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text("• Each question has one correct answer.")
                Text("• You can move between questions.")
                Text("• Timer will not stop once started.")
                Text("• Test will auto-submit on timeout.")
                Text("• Do not close the app during the test.")

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Test ID: $testId",
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Button(
                onClick = onReady,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("I am Ready")
            }
        }
    }
}