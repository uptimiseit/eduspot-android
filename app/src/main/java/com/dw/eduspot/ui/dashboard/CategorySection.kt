package com.dw.eduspot.ui.dashboard

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dw.eduspot.ui.dashboard.model.ExamCategory

@Composable
fun CategorySection(
    categories: List<ExamCategory>,
    selectedCategoryId: String,
    onCategorySelected: (ExamCategory) -> Unit
) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(categories) { category ->
            val isSelected = category.id == selectedCategoryId

            FilterChip(
                selected = isSelected,
                onClick = { onCategorySelected(category) },
                label = {
                    Text(category.title)
                },
                modifier = Modifier.padding(end = 8.dp)
            )
        }
    }
}