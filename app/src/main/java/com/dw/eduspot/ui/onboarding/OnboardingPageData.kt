package com.dw.eduspot.ui.onboarding

import androidx.annotation.RawRes
import com.dw.eduspot.R

data class OnboardingPageData(
    val title: String,
    val subtitle: String,
    @RawRes val lottieRes: Int
)

val onboardingPages = listOf(
    OnboardingPageData(
        title = "Learn Smarter",
        subtitle = "AI-powered mock tests designed for real exam patterns",
        lottieRes = R.raw.learning
    ),
    OnboardingPageData(
        title = "Track Your Progress",
        subtitle = "Detailed analytics for every attempt and subject",
        lottieRes = R.raw.analytics
    ),
    OnboardingPageData(
        title = "Achieve Success",
        subtitle = "Confidence, accuracy, and results — all in one place",
        lottieRes = R.raw.success
    )
)