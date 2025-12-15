package com.dw.eduspot.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dw.eduspot.ui.auth.LoginScreen
import com.dw.eduspot.ui.results.ResultScreen
import com.dw.eduspot.ui.splash.SplashScreen
import com.dw.eduspot.ui.testengine.TestEngineScreen

@Composable
fun AppNavHost() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.SPLASH
    ) {

        // --------------------
        // Splash
        // --------------------
        composable(Routes.SPLASH) {
            SplashScreen(
                onNavigateToLogin = {
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(Routes.SPLASH) { inclusive = true }
                    }
                },
                onNavigateToDashboard = {
                    navController.navigate(Routes.MAIN) {
                        popUpTo(Routes.SPLASH) { inclusive = true }
                    }
                }
            )
        }

        // --------------------
        // Login
        // --------------------
        composable(Routes.LOGIN) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Routes.MAIN) {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                }
            )
        }

        // --------------------
        // Main App (Bottom Nav)
        // --------------------
        composable(Routes.MAIN) {
            MainScaffold(
                onStartTest = { testId ->
                    navController.navigate("${Routes.TEST_ENGINE}/$testId")
                },
                onOpenResult = { testId ->
                    navController.navigate("${Routes.RESULT_DETAIL}/$testId")
                }
            )
        }

        // --------------------
        // Test Engine (Full Screen)
        // --------------------
        composable("${Routes.TEST_ENGINE}/{testId}") { backStackEntry ->
            val testId =
                backStackEntry.arguments?.getString("testId")
                    ?: return@composable

            TestEngineScreen(
                testId = testId,
                onTestFinished = {
                    navController.navigate("${Routes.RESULTS}/$testId") {
                        popUpTo("${Routes.TEST_ENGINE}/$testId") {
                            inclusive = true
                        }
                    }
                }
            )
        }

        // --------------------
        // Result Screen
        // --------------------
        composable("${Routes.RESULTS}/{testId}") { backStackEntry ->
            val testId =
                backStackEntry.arguments?.getString("testId")
                    ?: return@composable

            ResultScreen(
                testId = testId,
                onBack = {
                    navController.popBackStack(Routes.MAIN, false)
                }
            )
        }
    }
}