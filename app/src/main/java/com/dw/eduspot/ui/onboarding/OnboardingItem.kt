package com.dw.eduspot.ui.onboarding

import androidx.annotation.RawRes

data class OnboardingItem(
    @RawRes val animation: Int,
    val title: String,
    val description: String
)