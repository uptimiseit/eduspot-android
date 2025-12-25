package com.dw.eduspot.ui.course_list.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dw.eduspot.ui.course_list.CourseFilter
import com.dw.eduspot.ui.course_list.PriceType
import com.dw.eduspot.ui.course_list.SortOption

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterBottomSheet(
    currentFilters: CourseFilter,
    currentSort: SortOption,
    onApply: (CourseFilter, SortOption) -> Unit,
    onDismiss: () -> Unit
) {
    var tempFilters by remember { mutableStateOf(currentFilters) }
    var tempSort by remember { mutableStateOf(currentSort) }

    ModalBottomSheet(
        onDismissRequest = onDismiss
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {

            Text(
                text = "Sort By",
                style = MaterialTheme.typography.titleMedium
            )

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

            Spacer(Modifier.height(24.dp))

            Text(
                text = "Price Type",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(Modifier.height(12.dp))

            Row {
                FilterChip(
                    selected = tempFilters.priceType == PriceType.FREE,
                    onClick = {
                        tempFilters = tempFilters.copy(priceType = PriceType.FREE)
                    },
                    label = { Text("Free") }
                )

                Spacer(Modifier.width(8.dp))

                FilterChip(
                    selected = tempFilters.priceType == PriceType.PAID,
                    onClick = {
                        tempFilters = tempFilters.copy(priceType = PriceType.PAID)
                    },
                    label = { Text("Paid") }
                )
            }

            Spacer(Modifier.height(32.dp))

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    onApply(tempFilters, tempSort)
                }
            ) {
                Text("Apply Filters")
            }

            Spacer(Modifier.height(16.dp))
        }
    }
}

@Composable
fun SortRadio(
    title: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start
    ) {
        RadioButton(selected = selected, onClick = onClick)
        Spacer(Modifier.width(8.dp))
        Text(title)
    }
}