package com.dw.eduspot.ui.course_list.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dw.eduspot.ui.course_list.CourseFilter
import com.dw.eduspot.ui.course_list.SortOption

@Composable
fun FilterContent(
    filters: CourseFilter,
    sort: SortOption,
    onFiltersChange: (CourseFilter) -> Unit,
    onSortChange: (SortOption) -> Unit,
    onApply: () -> Unit
) {
    Column(modifier = Modifier.padding(16.dp)) {

        Text("Sort By", style = MaterialTheme.typography.titleMedium)

        SortOption.entries.forEach {
            Row {
                RadioButton(
                    selected = sort == it,
                    onClick = { onSortChange(it) }
                )
                Text(it.name)
            }
        }

        Spacer(Modifier.height(24.dp))

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = onApply
        ) {
            Text("Apply Filters")
        }
    }
}