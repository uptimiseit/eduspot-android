package com.dw.eduspot.ui.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.dw.eduspot.ui.dashboard.model.ExamCourse

@Composable
fun ExamCard(
    course: ExamCourse,
    onBuyNow: () -> Unit,
    onViewTests: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(260.dp)
            .height(280.dp) // 🔥 FIXED HEIGHT (KEY)
            .padding(end = 12.dp),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            Column {

                // 🔖 CATEGORY + TAG
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    TagChip(course.category)

                    if (course.isBestSeller) {
                        TagChip("BESTSELLER", isPrimary = true)
                    }
                    if (course.isNew) {
                        TagChip("NEW", color = Color(0xFF2E7D32))
                    }
                }

                Spacer(Modifier.height(12.dp))

                // 🧠 TITLE (2 lines max)
                Text(
                    text = course.title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2
                )

                Spacer(Modifier.height(6.dp))

                Text(
                    text = course.subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2
                )

                Spacer(Modifier.height(12.dp))

                FeatureText("✔ ${course.totalTests} Full Length Tests")
                FeatureText("✔ Language: ${course.language}")
                FeatureText("✔ Latest Exam Pattern")
            }
            Divider(modifier = Modifier.padding(vertical = 8.dp))

            Column {

                // 💰 PRICE
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "₹${course.mrp}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textDecoration = TextDecoration.LineThrough
                    )

                    Spacer(Modifier.width(8.dp))

                    Text(
                        text = "₹${course.offerPrice}",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = "Save ₹${course.mrp - course.offerPrice}",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color(0xFF2E7D32)
                    )
                }

                Spacer(Modifier.height(12.dp))

                // 🛒 CTA
                Button(
                    onClick = onBuyNow,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Text("Buy Now")
                }
            }
        }
    }
}

@Composable
fun FeatureText(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodySmall,
        modifier = Modifier.padding(vertical = 2.dp)
    )
}