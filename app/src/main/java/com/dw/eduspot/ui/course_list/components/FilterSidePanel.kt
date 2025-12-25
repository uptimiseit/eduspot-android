package com.dw.eduspot.ui.course_list.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dw.eduspot.ui.course_list.CourseFilter
import com.dw.eduspot.ui.course_list.SortOption

@Composable
fun FilterSidePanel(
    currentFilters: CourseFilter,
    currentSort: SortOption,
    onApply: (CourseFilter, SortOption) -> Unit
) {
    var tempFilters by remember { mutableStateOf(currentFilters) }
    var tempSort by remember { mutableStateOf(currentSort) }

    Column(
        modifier = Modifier
            .width(320.dp)
            .padding(16.dp)
    ) {

        Text("Filters", style = MaterialTheme.typography.titleLarge)

        Spacer(Modifier.height(24.dp))

        Text("Sort By", style = MaterialTheme.typography.titleMedium)

        Spacer(Modifier.height(12.dp))

        SortRadio(
            title = "Most Popular",
            selected = tempSort == SortOption.POPULAR,
            onClick = { tempSort = SortOption.POPULAR }
        )

        SortRadio(
            title = "Price: Low to High",
            selected = tempSort == SortOption.PRICE_LOW_TO_HIGH,
            onClick = { tempSort = SortOption.PRICE_LOW_TO_HIGH }
        )

        SortRadio(
            title = "Price: High to Low",
            selected = tempSort == SortOption.PRICE_HIGH_TO_LOW,
            onClick = { tempSort = SortOption.PRICE_HIGH_TO_LOW }
        )

        Spacer(Modifier.height(32.dp))

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                onApply(tempFilters, tempSort)
            }
        ) {
            Text("Apply")
        }
    }
}