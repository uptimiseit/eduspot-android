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
import com.dw.eduspot.data.fake.FakeCourseAttemptRepository
import com.dw.eduspot.ui.attempts.AttemptSelectionScreen
import com.dw.eduspot.ui.course.CourseDetailScreen
import com.dw.eduspot.ui.dashboard.DashboardScreen
import com.dw.eduspot.ui.mycourses.MyCoursesScreen
import com.dw.eduspot.ui.profile.ProfileScreen
import com.dw.eduspot.ui.results.ResultsHistoryScreen
import com.dw.eduspot.ui.testlist.TestListScreen

@Composable
fun MainScaffold(
    rootNavController: NavHostController
) {
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

                    // 🔥 FIXED ENTRY LOGIC
                    onOpenCourse = { courseId ->

                        val attempts =
                            FakeCourseAttemptRepository
                                .attempts
                                .value
                                .filter { it.courseId == courseId }

                        when {
                            // ❌ Not purchased yet
                            attempts.isEmpty() -> {
                                bottomNavController.navigate(
                                    "${Routes.COURSE_DETAIL}/$courseId"
                                )
                            }

                            // ✅ Exactly one attempt → go directly to test list
                            attempts.size == 1 -> {
                                bottomNavController.navigate(
                                    "${Routes.TEST_LIST}/$courseId/${attempts.first().attemptId}"
                                )
                            }

                            // 🔥 Multiple attempts → select attempt
                            else -> {
                                bottomNavController.navigate(
                                    "${Routes.ATTEMPT_SELECTION}/$courseId"
                                )
                            }
                        }
                    },

                    onOpenTestList = { courseId, attemptId ->
                        bottomNavController.navigate(
                            "${Routes.TEST_LIST}/$courseId/$attemptId"
                        )
                    },

                    onResumeTest = { testId ->
                        rootNavController.navigate(
                            "${Routes.TEST_GUIDELINES}/$testId"
                        )
                    },

                    onOpenResult = { testId ->
                        rootNavController.navigate(
                            "${Routes.RESULT_DETAIL}/$testId"
                        )
                    },

                    onOpenSettings = {
                        // TODO: Settings disabled for now
                    }
                )
            }

            /* ---------------- MY COURSES ---------------- */
            composable(Routes.MY_COURSES) {
                MyCoursesScreen(
                    onOpenCourse = { courseId ->

                        val attempts =
                            FakeCourseAttemptRepository
                                .attempts
                                .value
                                .filter { it.courseId == courseId }

                        when {
                            attempts.isEmpty() -> {
                                bottomNavController.navigate(
                                    "${Routes.COURSE_DETAIL}/$courseId"
                                )
                            }

                            attempts.size == 1 -> {
                                bottomNavController.navigate(
                                    "${Routes.TEST_LIST}/$courseId/${attempts.first().attemptId}"
                                )
                            }

                            else -> {
                                bottomNavController.navigate(
                                    "${Routes.ATTEMPT_SELECTION}/$courseId"
                                )
                            }
                        }
                    },

                    onOpenTests = { courseId, attemptId ->
                        bottomNavController.navigate(
                            "${Routes.TEST_LIST}/$courseId/$attemptId"
                        ) {
                            launchSingleTop = true
                        }
                    }
                )
            }

            /* ---------------- COURSE DETAIL ---------------- */
            composable("${Routes.COURSE_DETAIL}/{courseId}") { backStack ->
                val courseId = backStack.arguments!!.getString("courseId")!!

                CourseDetailScreen(
                    courseId = courseId,
                    onPurchaseSuccess = {
                        bottomNavController.navigate(Routes.MY_COURSES) {
                            popUpTo(Routes.HOME)
                            launchSingleTop = true
                        }
                    },
                    onOpenTests = { attemptId ->
                        bottomNavController.navigate(
                            "${Routes.TEST_LIST}/$courseId/$attemptId"
                        )
                    }
                )
            }

            /* ---------------- TEST LIST ---------------- */
            composable("${Routes.TEST_LIST}/{courseId}/{attemptId}") { backStack ->
                val courseId = backStack.arguments!!.getString("courseId")!!
                val attemptId = backStack.arguments!!.getString("attemptId")!!

                TestListScreen(
                    courseId = courseId,
                    attemptId = attemptId,
                    onStartTest = { testId ->
                        rootNavController.navigate(
                            "${Routes.TEST_GUIDELINES}/$attemptId/$testId"
                        )
                    }
                )
            }

            /* ---------------- ATTEMPT SELECTION ---------------- */
            composable("${Routes.ATTEMPT_SELECTION}/{courseId}") { backStack ->
                val courseId = backStack.arguments!!.getString("courseId")!!

                AttemptSelectionScreen(
                    courseId = courseId,
                    onAttemptSelected = { attemptId ->
                        bottomNavController.navigate(
                            "${Routes.TEST_LIST}/$courseId/$attemptId"
                        )
                    }
                )
            }

            /* ---------------- RESULTS ---------------- */
            composable(Routes.RESULT) {
                ResultsHistoryScreen(
                    onOpenResult = { attemptId, testId ->
                        rootNavController.navigate(
                            "${Routes.RESULT_DETAIL}/$attemptId/$testId"
                        )
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



        }
    }
}