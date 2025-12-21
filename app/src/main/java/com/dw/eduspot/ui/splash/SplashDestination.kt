package com.dw.eduspot.ui.splash

sealed class SplashDestination {
    object Onboarding : SplashDestination()
    object Login : SplashDestination()
    object Dashboard : SplashDestination()
}