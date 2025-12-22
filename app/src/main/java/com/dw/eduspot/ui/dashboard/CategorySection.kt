package com.dw.eduspot.ui.dashboard

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp
import com.dw.eduspot.ui.dashboard.model.ExamCategory

@Composable
fun CategorySection(
    categories: List<ExamCategory>,
    selectedCategoryId: String?,
    onAllClick: () -> Unit,
    onCategoryClick: (ExamCategory) -> Unit
) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(categories) { category ->
            val isSelected =
                category.id != "ALL" && category.id == selectedCategoryId

            FilterChip(
                selected = isSelected,
                onClick = {
                    if (category.id == "ALL") {
                        onAllClick()
                    } else {
                        onCategoryClick(category)
                    }
                },
                label = {
                    Text(category.title)
                }
            )
        }
    }
}