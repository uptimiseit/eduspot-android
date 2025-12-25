package com.dw.eduspot.ui.course_list.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dw.eduspot.ui.course.Course

@Composable
fun CourseCard(course: Course) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Text(course.title, style = MaterialTheme.typography.titleMedium)

            Spacer(Modifier.height(4.dp))

            Text(
                text = "${course.totalTests} Tests • ${course.language}",
                style = MaterialTheme.typography.bodySmall
            )

            Spacer(Modifier.height(8.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "₹${course.offerPrice}",
                    style = MaterialTheme.typography.titleMedium
                )
                Button(onClick = { /* buy later */ }) {
                    Text("Buy Now")
                }
            }
        }
    }
}