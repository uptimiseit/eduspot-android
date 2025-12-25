package com.dw.eduspot.ui.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dw.eduspot.ui.dashboard.components.TagChip
import com.dw.eduspot.ui.course.Course
import com.dw.eduspot.ui.dashboard.components.CourseMetaRow
import com.dw.eduspot.ui.dashboard.components.PriceRow

@Composable
fun ExamCard(
    course: Course,
    onBuyNow: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(260.dp)
            .height(320.dp)
            .padding(end = 16.dp),
        shape = MaterialTheme.shapes.large,
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            // 🔖 Badge row
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TagChip(course.category)

                if (course.isBestSeller) {
                    TagChip("Best Seller", MaterialTheme.colorScheme.tertiary)
                }
            }

            Spacer(Modifier.height(12.dp))

            // 🧠 Title
            Text(
                text = course.title,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 3
            )

            Spacer(Modifier.height(6.dp))

            // ✍️ Subtitle
            Text(
                text = course.title,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(Modifier.height(12.dp))

            Divider()

            Spacer(Modifier.height(12.dp))

            // 📊 Info
            CourseMetaRow(
                tests = course.totalTests,
                language = course.language
            )

            Spacer(Modifier.weight(1f))

            // 💰 Price
            PriceRow(
                mrp = course.mrp,
                offer = course.offerPrice
            )

            Spacer(Modifier.height(8.dp))

            Button(
                onClick = onBuyNow,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Buy Now")
            }
        }
    }
}