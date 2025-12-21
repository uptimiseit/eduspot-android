package com.dw.eduspot.ui.splash

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.dw.eduspot.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onNavigateToOnboarding: () -> Unit,
    onNavigateToLogin: () -> Unit,
    onNavigateToDashboard: () -> Unit
) {
    val viewModel: SplashViewModel = hiltViewModel()
    val destination by viewModel.destination.collectAsState()

    // 🎯 Animation states
    val scale = remember { Animatable(0.85f) }
    val alpha = remember { Animatable(0f) }

    // 🎬 Run animation once
    LaunchedEffect(Unit) {
        scale.animateTo(1f, tween(700))
        alpha.animateTo(1f, tween(500))
    }

    // 🚦 Navigation decision
    LaunchedEffect(destination) {
        if (destination != null) {
            delay(400) // smooth exit
            when (destination) {
                SplashDestination.Onboarding -> onNavigateToOnboarding()
                SplashDestination.Login -> onNavigateToLogin()
                SplashDestination.Dashboard -> onNavigateToDashboard()
                else -> {}
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(R.drawable.ic_splash_logo),

            contentDescription = "EduSpot Logo",
            modifier = Modifier
                .size(140.dp)
                .alpha(alpha.value)
                .graphicsLayer {
                    scaleX = scale.value
                    scaleY = scale.value
                }
        )
    }
}