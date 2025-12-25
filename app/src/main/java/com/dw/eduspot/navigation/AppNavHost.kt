package com.dw.eduspot.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavType

import androidx.navigation.navArgument
  // ✅ make sure this file exists in this package or import correctly
import com.dw.eduspot.ui.splash.SplashScreen
import com.dw.eduspot.ui.onboarding.OnboardingScreen
import com.dw.eduspot.ui.auth.LoginScreen
import com.dw.eduspot.ui.guidelines.TestGuidelinesScreen
import com.dw.eduspot.ui.preparing.TestPreparingScreen
import com.dw.eduspot.ui.testengine.TestEngineScreen
import com.dw.eduspot.ui.results.ResultScreen
import com.dw.eduspot.navigation.MainScaffold  // ✅ FIXED IMPORT

@Composable
fun AppNavHost() {
    val rootNavController = rememberNavController()

    NavHost(
        navController = rootNavController,
        startDestination = Routes.SPLASH
    ) {

        composable(Routes.SPLASH) {
            SplashScreen(
                onNavigateToOnboarding = {
                    rootNavController.navigate(Routes.ONBOARDING) {
                        popUpTo(Routes.SPLASH) { inclusive = true }
                    }
                },
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

        composable(Routes.ONBOARDING) {
            OnboardingScreen(
                onFinished = {
                    rootNavController.navigate(Routes.LOGIN) {
                        popUpTo(Routes.ONBOARDING) { inclusive = true }
                    }
                }
            )
        }

        composable(Routes.LOGIN) {
            LoginScreen(
                onNavigateDashboard = {
                    rootNavController.navigate(Routes.MAIN) {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                }
            )
        }

        composable(Routes.MAIN) {
            MainScaffold(root = rootNavController)
        }

        // -------- TEST GUIDELINES --------
        composable(
            route = "test_guidelines/{attemptId}/{testId}",
            arguments = listOf(
                navArgument("attemptId") { type = NavType.StringType },
                navArgument("testId") { type = NavType.StringType }
            )
        ) { backStack ->
            val attemptId = backStack.arguments?.getString("attemptId") ?: return@composable
            val testId = backStack.arguments?.getString("testId") ?: return@composable

            TestGuidelinesScreen(
                testId = testId,
                onBack = { rootNavController.popBackStack() },
                onReady = {
                    rootNavController.navigate("test_preparing/$attemptId/$testId")
                }
            )
        }

        // -------- TEST PREPARING --------
        composable(
            route = "test_preparing/{attemptId}/{testId}",
            arguments = listOf(
                navArgument("attemptId") { type = NavType.StringType },
                navArgument("testId") { type = NavType.StringType }
            )
        ) { backStack ->
            val attemptId = backStack.arguments?.getString("attemptId") ?: return@composable
            val testId = backStack.arguments?.getString("testId") ?: return@composable

            TestPreparingScreen(
                onPrepared = {
                    rootNavController.navigate("test_engine/$attemptId/$testId")
                }
            )
        }

        // -------- TEST ENGINE --------
        composable(
            route = "test_engine/{attemptId}/{testId}",
            arguments = listOf(
                navArgument("attemptId") { type = NavType.StringType },
                navArgument("testId") { type = NavType.StringType }
            )
        ) { backStack ->
            val attemptId = backStack.arguments?.getString("attemptId") ?: return@composable
            val testId = backStack.arguments?.getString("testId") ?: return@composable

            TestEngineScreen(
                attemptId = attemptId,
                testId = testId,
                onTestFinished = {
                    rootNavController.navigate("result_detail/$attemptId/$testId") {
                        popUpTo(Routes.MAIN) { inclusive = false }
                    }
                }
            )
        }

        // -------- RESULT DETAIL --------
        composable(
            route = "result_detail/{attemptId}/{testId}",
            arguments = listOf(
                navArgument("attemptId") { type = NavType.StringType },
                navArgument("testId") { type = NavType.StringType }
            )
        ) { backStack ->
            val attemptId = backStack.arguments?.getString("attemptId") ?: return@composable
            val testId = backStack.arguments?.getString("testId") ?: return@composable

            ResultScreen(
                attemptId = attemptId,
                testId = testId,
                onBack = { rootNavController.popBackStack() }
            )
        }
    }
}