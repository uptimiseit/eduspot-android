@file:OptIn(ExperimentalMaterial3Api::class)

package com.dw.eduspot.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dw.eduspot.ui.course.CourseDetailScreen
import com.dw.eduspot.ui.dashboard.DashboardScreen
import com.dw.eduspot.ui.mycourses.MyCoursesScreen
import com.dw.eduspot.ui.profile.ProfileScreen
import com.dw.eduspot.ui.results.ResultsHistoryScreen
import com.dw.eduspot.ui.settings.SettingsScreen
import com.dw.eduspot.ui.testlist.TestListScreen

@Composable
fun MainScaffold(
    rootNavController: NavHostController
) {
    // 🔹 Bottom navigation controller (tabs only)
    val bottomNavController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomBar(bottomNavController)
        }
    ) { padding ->

        NavHost(
            navController = bottomNavController,
            startDestination = Routes.HOME,
            modifier = Modifier.padding(padding)
        ) {

            /* ---------------- DASHBOARD ---------------- */
            composable(Routes.HOME) {
                DashboardScreen(
                    onOpenCourse = { courseId ->
                        bottomNavController.navigate("${Routes.COURSE_DETAIL}/$courseId")
                    },
                    onResumeTest = { testId ->
                        // 🔥 ALWAYS root
                        rootNavController.navigate("${Routes.TEST_ENGINE}/$testId")
                    },
                    onOpenResult = { courseId ->
                        // 🔥 Course completed → result history/detail
                        bottomNavController.navigate(Routes.RESULT)
                    },
                    onOpenSettings = {
                        bottomNavController.navigate(Routes.SETTINGS)
                    }
                )
            }

            /* ---------------- MY COURSES ---------------- */
            composable(Routes.MY_COURSES) {
                MyCoursesScreen(
                    onOpenCourse = { courseId ->
                        bottomNavController.navigate("${Routes.COURSE_DETAIL}/$courseId")
                    },
                    onOpenTests = { courseId ->
                        bottomNavController.navigate("${Routes.TEST_LIST}/$courseId")
                    }
                )
            }

            /* ---------------- RESULTS ---------------- */
            composable(Routes.RESULT) {
                ResultsHistoryScreen(
                    onOpenResult = { testId ->
                        rootNavController.navigate("${Routes.RESULT_DETAIL}/$testId")
                    }
                )
            }

            /* ---------------- PROFILE ---------------- */
            composable(Routes.PROFILE) {
                ProfileScreen(
                    onLogout = {
                        rootNavController.navigate(Routes.LOGIN) {
                            popUpTo(Routes.MAIN) { inclusive = true }
                        }
                    }
                )
            }

            /* ---------------- SETTINGS ---------------- */
            composable(Routes.SETTINGS) {
                SettingsScreen(
                    onBack = {
                        bottomNavController.popBackStack()
                    }
                )
            }

            /* ---------------- COURSE DETAIL ---------------- */
            composable("${Routes.COURSE_DETAIL}/{courseId}") { backStack ->
                val courseId = backStack.arguments?.getString("courseId")!!

                CourseDetailScreen(
                    courseId = courseId,
                    onPurchaseSuccess = {
                        bottomNavController.navigate(Routes.MY_COURSES) {
                            popUpTo(Routes.HOME)
                        }
                    },
                    onOpenTests = {
                        bottomNavController.navigate("${Routes.TEST_LIST}/$courseId")
                    }
                )
            }

            /* ---------------- TEST LIST ---------------- */
            composable("${Routes.TEST_LIST}/{courseId}") { backStack ->
                val courseId = backStack.arguments?.getString("courseId")!!

                TestListScreen(
                    courseId = courseId,
                    onStartTest = { testId ->
                        // 🔥 ALWAYS root
                        rootNavController.navigate("${Routes.TEST_ENGINE}/$testId")
                    }
                )
            }
        }
    }
}