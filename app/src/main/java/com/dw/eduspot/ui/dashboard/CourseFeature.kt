package com.dw.eduspot.ui.dashboard

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CourseFeature(
    text: String,
    onClick: (() -> Unit)? = null
) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodySmall,
        modifier = Modifier
            .padding(vertical = 2.dp)
            .then(
                if (onClick != null)
                    Modifier.clickable { onClick() }
                else Modifier
            )
    )
}