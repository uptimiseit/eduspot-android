package com.dw.eduspot.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dw.eduspot.ui.auth.LoginScreen
import com.dw.eduspot.ui.results.ResultScreen
import com.dw.eduspot.ui.splash.SplashScreen
import com.dw.eduspot.ui.testengine.TestEngineScreen
import com.dw.eduspot.ui.guidelines.TestGuidelinesScreen
import com.dw.eduspot.ui.onboarding.OnboardingScreen
import com.dw.eduspot.ui.onboarding.OnboardingViewModel
import com.dw.eduspot.ui.preparing.TestPreparingScreen

@Composable
fun AppNavHost() {

    val rootNavController = rememberNavController()

    NavHost(
        navController = rootNavController,
        startDestination = Routes.SPLASH
    ) {

        /* ---------------- SPLASH ---------------- */
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

        /* ---------------- LOGIN ---------------- */
        composable(Routes.LOGIN) {
            LoginScreen(
                onNavigateDashboard = {
                    rootNavController.navigate(Routes.MAIN) {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                }
            )
        }

        /* ---------------- MAIN ---------------- */
        composable(Routes.MAIN) {
            MainScaffold(rootNavController)
        }

        /* ---------------- TEST FLOW ---------------- */
        composable("${Routes.TEST_GUIDELINES}/{attemptId}/{testId}") {
            val attemptId = it.arguments!!.getString("attemptId")!!
            val testId = it.arguments!!.getString("testId")!!

            TestGuidelinesScreen(
                testId = testId,
                onBack = { rootNavController.popBackStack() },
                onReady = {
                    rootNavController.navigate(
                        "${Routes.TEST_PREPARING}/$attemptId/$testId"
                    )
                }
            )
        }

        composable("${Routes.TEST_PREPARING}/{attemptId}/{testId}") {
            val attemptId = it.arguments!!.getString("attemptId")!!
            val testId = it.arguments!!.getString("testId")!!

            TestPreparingScreen(
                onPrepared = {
                    rootNavController.navigate(
                        "${Routes.TEST_ENGINE}/$attemptId/$testId"
                    )
                }
            )
        }

        composable("${Routes.TEST_ENGINE}/{attemptId}/{testId}") {
            val attemptId = it.arguments!!.getString("attemptId")!!
            val testId = it.arguments!!.getString("testId")!!

            TestEngineScreen(
                attemptId = attemptId,
                testId = testId,
                onTestFinished = {
                    rootNavController.navigate(
                        "${Routes.RESULT_DETAIL}/$attemptId/$testId"
                    )
                }
            )
        }

        composable("${Routes.RESULT_DETAIL}/{attemptId}/{testId}") {
            val attemptId = it.arguments!!.getString("attemptId")!!
            val testId = it.arguments!!.getString("testId")!!

            ResultScreen(
                attemptId = attemptId,
                testId = testId,
                onBack = {
                    rootNavController.popBackStack(Routes.MAIN, false)
                }
            )
        }
    }
}