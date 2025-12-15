package com.dw.eduspot.ui.splash

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.fillMaxSize
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun SplashScreen(
    onNavigateToLogin: () -> Unit,
    onNavigateToDashboard: () -> Unit
) {
    val viewModel: SplashViewModel = hiltViewModel()
    val destination by viewModel.destination.collectAsState()

    LaunchedEffect(destination) {
        when (destination) {
            SplashDestination.Login -> onNavigateToLogin()
            SplashDestination.Dashboard -> onNavigateToDashboard()
            null -> Unit
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "EDUSPOT",
            style = MaterialTheme.typography.headlineLarge
        )
    }
}