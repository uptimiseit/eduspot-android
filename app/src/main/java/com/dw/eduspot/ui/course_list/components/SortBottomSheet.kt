package com.dw.eduspot.ui.course_list.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.dw.eduspot.ui.course_list.FilterState
import com.dw.eduspot.ui.course_list.SortType

@Composable
fun SortBottomSheet(
    current: FilterState,
    onApply: (SortType) -> Unit
) {
    Column {
        Row {
            RadioButton(
                selected = current.sort == SortType.POPULAR,
                onClick = { onApply(SortType.POPULAR) }
            )
            Text("Most Popular")
        }

        Row {
            RadioButton(
                selected = current.sort == SortType.PRICE_LOW_TO_HIGH,
                onClick = { onApply(SortType.PRICE_LOW_TO_HIGH) }
            )
            Text("Price: Low to High")
        }
    }
}