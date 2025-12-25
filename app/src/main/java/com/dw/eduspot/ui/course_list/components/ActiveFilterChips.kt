package com.dw.eduspot.ui.course_list.components

import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.dw.eduspot.ui.course_list.FilterState

@Composable
fun ActiveFilterChips(
    state: FilterState,
    onRemove: (FilterState) -> Unit
) {
    LazyRow {
        if (state.language != null) {
            item {
                AssistChip(
                    onClick = {
                        onRemove(state.copy(language = null))
                    },
                    label = { Text(state.language) }
                )
            }
        }

        if (state.priceType != null) {
            item {
                AssistChip(
                    onClick = {
                        onRemove(state.copy(priceType = null))
                    },
                    label = { Text(state.priceType.name) }
                )
            }
        }
    }
}