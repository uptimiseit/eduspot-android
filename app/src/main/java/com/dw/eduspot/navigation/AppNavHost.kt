package com.dw.eduspot.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dw.eduspot.ui.auth.LoginScreen
import com.dw.eduspot.ui.results.ResultScreen
import com.dw.eduspot.ui.splash.SplashScreen
import com.dw.eduspot.ui.testengine.TestEngineScreen
import com.dw.eduspot.ui.guidelines.TestGuidelinesScreen
import com.dw.eduspot.ui.preparing.TestPreparingScreen

@Composable
fun AppNavHost() {

    // ✅ ONE root NavController for whole app
    val rootNavController = rememberNavController()

    NavHost(
        navController = rootNavController,
        startDestination = Routes.SPLASH
    ) {

        /* ---------------- SPLASH ---------------- */
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

        /* ---------------- LOGIN ---------------- */
        composable(Routes.LOGIN) {
            LoginScreen(
                onLoginSuccess = {
                    rootNavController.navigate(Routes.MAIN) {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                }
            )
        }

        /* ---------------- MAIN (Bottom Tabs) ---------------- */
        composable(Routes.MAIN) {
            MainScaffold(
                rootNavController = rootNavController
            )
        }

        /* ---------------- TEST GUIDELINES ---------------- */
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



        /* ---------------- TEST PREPARING ---------------- */
        composable("${Routes.TEST_PREPARING}/{attemptId}/{testId}") {
            val attemptId = it.arguments!!.getString("attemptId")!!
            val testId = it.arguments!!.getString("testId")!!

            TestPreparingScreen(
                onPrepared = {
                    rootNavController.navigate(
                        "${Routes.TEST_ENGINE}/$attemptId/$testId"
                    ) {
                        popUpTo("${Routes.TEST_GUIDELINES}/$attemptId/$testId") {
                            inclusive = true
                        }
                    }
                }
            )
        }

        /* ---------------- TEST ENGINE ---------------- */
        composable("${Routes.TEST_ENGINE}/{attemptId}/{testId}") { backStack ->
            val attemptId = backStack.arguments!!.getString("attemptId")!!
            val testId = backStack.arguments!!.getString("testId")!!

            TestEngineScreen(
                attemptId = attemptId,
                testId = testId,
                onTestFinished = {
                    rootNavController.navigate(
                        "${Routes.RESULT_DETAIL}/$attemptId/$testId"
                    ) {
                        launchSingleTop = true
                    }
                }
            )
        }

        /* ---------------- RESULT DETAIL ---------------- */
        composable("${Routes.RESULT_DETAIL}/{attemptId}/{testId}") { backStack ->
            val attemptId = backStack.arguments!!.getString("attemptId")!!
            val testId = backStack.arguments!!.getString("testId")!!

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