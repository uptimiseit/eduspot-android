package com.dw.eduspot.ui.course

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dw.eduspot.navigation.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CourseDetailScreen(
    courseId: String,
    onPurchaseSuccess: (String) -> Unit,
    onOpenTests: (String) -> Unit,
    viewModel: CourseViewModel = hiltViewModel()
) {
    val uiState by viewModel.purchaseResult.collectAsState()

    LaunchedEffect(uiState) {
        uiState?.let { onPurchaseSuccess(it) }
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Course Details") }) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Spacer(Modifier.height(20.dp))

            Button(
                modifier = Modifier.fillMaxWidth().height(50.dp),
                onClick = { viewModel.buyCourse(courseId) }
            ) {
                Text("Buy Course")
            }

            Spacer(Modifier.height(32.dp))

            Button(
                modifier = Modifier.fillMaxWidth().height(50.dp),
                onClick = { onOpenTests(courseId) }
            ) {
                Text("View Tests")
            }
        }
    }
}