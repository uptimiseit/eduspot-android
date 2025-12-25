package com.dw.eduspot.ui.designsystem

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

object TextStyles {

    @Composable
    fun ScreenTitle() = MaterialTheme.typography.headlineSmall

    @Composable
    fun SectionTitle() = MaterialTheme.typography.titleLarge

    @Composable
    fun CardTitle() = MaterialTheme.typography.titleMedium

    @Composable
    fun Body() = MaterialTheme.typography.bodyMedium

    @Composable
    fun Caption() = MaterialTheme.typography.bodySmall
}