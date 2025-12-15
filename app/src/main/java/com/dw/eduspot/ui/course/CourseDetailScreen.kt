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
    onPurchaseSuccess: () -> Unit,
    onOpenTests: () -> Unit
) {
    val course = remember { CourseFakeData.getCourseById(courseId) }
    val viewModel: CourseViewModel = viewModel()
    val purchased by viewModel.purchased.collectAsState()

    LaunchedEffect(purchased) {
        if (purchased) {
            onPurchaseSuccess()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(course.title) }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {

            Text(
                text = course.description,
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Price: ${course.price}",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { viewModel.buyCourse(course) }
            ) {
                Text("Buy Course")
            }
        }
    }
}