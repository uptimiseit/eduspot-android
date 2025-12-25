package com.dw.eduspot.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dw.eduspot.ui.dashboard.DashboardScreen
import com.dw.eduspot.ui.mycourses.MyCoursesScreen
import com.dw.eduspot.ui.profile.ProfileScreen
import com.dw.eduspot.ui.results.ResultsHistoryScreen

@Composable
fun MainScaffold(root: androidx.navigation.NavHostController) {
    val nav = rememberNavController()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = { BottomBar(nav) }
    ) { padding ->
        NavHost(
            navController = nav,
            startDestination = Routes.HOME,
            modifier = Modifier.fillMaxSize().padding(padding)
        ) {
            composable(Routes.HOME) {
                DashboardScreen(
                    onOpenCourse = { id -> root.navigate("course_detail/$id") },
                    onOpenTestList = { c, a -> root.navigate("test_list/$c/$a") },
                    onResumeTest = { id -> root.navigate("test_guidelines/$id/demo") },
                    onOpenResult = { id -> root.navigate("result_detail/$id") }
                )
            }

            composable("my_courses") {
                MyCoursesScreen(
                    onOpenCourse = { root.navigate("course_detail/$it") },
                    onOpenTests = { c, a -> root.navigate("test_list/$c/$a") }
                )
            }

            composable("result") {
                ResultsHistoryScreen(
                    onOpenResult = { attemptId, testId ->
                        root.navigate("result_detail/$attemptId/$testId")
                    }
                )
            }

            composable("profile") {
                ProfileScreen(onLogout = { root.navigate(Routes.LOGIN) })
            }
        }
    }
}