package com.dw.eduspot.ui.course

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CourseDetailScreen(
    courseId: String,
    onPurchaseSuccess: (String) -> Unit,
    onOpenTests: (String) -> Unit
) {
    val course = remember { CourseFakeData.getCourseById(courseId) }
    val viewModel: CourseViewModel = viewModel()

    val attemptId by viewModel.purchaseResult.collectAsState()
    LaunchedEffect(attemptId) {
        attemptId?.let { onPurchaseSuccess(it) }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(course.title) })
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {

            Text(course.description)
            Spacer(modifier = Modifier.height(24.dp))
            Text("Price: ${course.price}")

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { viewModel.buyCourse(course) }
            ) {
                Text("Buy Again") // 🔥 STEP 10 UX
            }
        }
    }
}