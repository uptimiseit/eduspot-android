@file:OptIn(ExperimentalMaterial3Api::class)

package com.dw.eduspot.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dw.eduspot.ui.dashboard.DashboardScreen
import com.dw.eduspot.ui.mycourses.MyCoursesScreen
import com.dw.eduspot.ui.results.ResultsHistoryScreen
import com.dw.eduspot.ui.purchase.PurchaseHistoryScreen
import com.dw.eduspot.ui.profile.ProfileScreen
import com.dw.eduspot.ui.support.SupportScreen
import com.dw.eduspot.ui.settings.SettingsScreen
import com.dw.eduspot.ui.course.CourseDetailScreen
import com.dw.eduspot.ui.testlist.TestListScreen

@Composable
fun MainScaffold(
    onStartTest: (String) -> Unit,
    onOpenResult: (String) -> Unit
) {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomBar(navController)
        }
    ) { padding ->

        NavHost(
            navController = navController,
            startDestination = Routes.HOME,
            modifier = Modifier.padding(padding)
        ) {

            // ---------------- DASHBOARD ----------------
            composable(Routes.HOME) {
                DashboardScreen(
                    onOpenCourse = { courseId ->
                        navController.navigate("${Routes.COURSE_DETAIL}/$courseId")
                    },
                    onResumeTest = { testId ->
                        onStartTest(testId) // delegate to ROOT
                    },
                    onOpenSettings = {
                        navController.navigate(Routes.SETTINGS)
                    }
                )
            }

            // ---------------- MY COURSES ----------------
            composable(Routes.MY_COURSES) {
                MyCoursesScreen(
                    onOpenCourse = { courseId ->
                        navController.navigate("${Routes.COURSE_DETAIL}/$courseId")
                    },
                    onOpenTests = { courseId ->
                        navController.navigate("${Routes.TEST_LIST}/$courseId")
                    }
                )
            }

            // ---------------- RESULTS (LIST ONLY) ----------------
            composable(Routes.RESULTS) {
                ResultsHistoryScreen(
                    onOpenResult = { testId ->
                        onOpenResult(testId) // delegate to ROOT
                    }
                )
            }

            // ---------------- PURCHASE HISTORY ----------------
            composable(Routes.PURCHASE_HISTORY) {
                PurchaseHistoryScreen()
            }

            // ---------------- PROFILE ----------------
            composable(Routes.PROFILE) {
                ProfileScreen(
                    onLogout = {
                        // Root (Splash) handles logout
                    }
                )
            }

            // ---------------- SUPPORT ----------------
            composable(Routes.SUPPORT) {
                SupportScreen()
            }

            // ---------------- SETTINGS ----------------
            composable(Routes.SETTINGS) {
                SettingsScreen(
                    onBack = { navController.popBackStack() }
                )
            }

            // ---------------- COURSE DETAIL ----------------
            composable("${Routes.COURSE_DETAIL}/{courseId}") { backStack ->
                val courseId = backStack.arguments?.getString("courseId") ?: ""

                CourseDetailScreen(
                    courseId = courseId,
                    onPurchaseSuccess = {
                        navController.navigate(Routes.MY_COURSES)
                    },
                    onOpenTests = {
                        navController.navigate("${Routes.TEST_LIST}/$courseId")
                    }
                )
            }

            // ---------------- TEST LIST ----------------
            composable("${Routes.TEST_LIST}/{courseId}") { backStack ->
                val courseId = backStack.arguments?.getString("courseId") ?: ""

                TestListScreen(
                    courseId = courseId,
                    onStartTest = { testId ->
                        onStartTest(testId) // delegate to ROOT
                    }
                )
            }
        }
    }
}