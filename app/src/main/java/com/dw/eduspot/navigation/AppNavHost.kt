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

    // ✅ ONE root NavController for whole app
    val rootNavController = rememberNavController()

    NavHost(
        navController = rootNavController,
        startDestination = Routes.SPLASH
    ) {

        // ---------------- SPLASH ----------------
        composable(Routes.SPLASH) {
            SplashScreen(
                onNavigateToLogin = {
                    rootNavController.navigate(Routes.LOGIN) {
                        popUpTo(Routes.SPLASH) { inclusive = true }
                    }
                },
                onNavigateToDashboard = {
                    rootNavController.navigate(Routes.MAIN) {
                        popUpTo(Routes.SPLASH) { inclusive = true }
                    }
                }
            )
        }

        // ---------------- LOGIN ----------------
        composable(Routes.LOGIN) {
            LoginScreen(
                onLoginSuccess = {
                    rootNavController.navigate(Routes.MAIN) {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                }
            )
        }

        // ---------------- MAIN (Bottom Tabs) ----------------
        composable(Routes.MAIN) {
            MainScaffold(
                rootNavController = rootNavController
            )
        }

        // ---------------- TEST ENGINE ----------------
        composable("${Routes.TEST_ENGINE}/{testId}") { backStack ->
            val testId = backStack.arguments?.getString("testId")!!

            TestEngineScreen(
                testId = testId,
                onTestFinished = {
                    rootNavController.navigate(
                        "${Routes.RESULT_DETAIL}/$testId"
                    )
                }
            )
        }

        // ---------------- RESULT DETAIL ----------------
        composable("${Routes.RESULT_DETAIL}/{testId}") { backStack ->
            val testId = backStack.arguments?.getString("testId")!!

            ResultScreen(
                testId = testId,
                onBack = {
                    // Go back to dashboard tab safely
                    rootNavController.popBackStack(
                        Routes.MAIN,
                        inclusive = false
                    )
                }
            )
        }
    }
}