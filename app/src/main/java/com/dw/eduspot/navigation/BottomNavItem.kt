package com.dw.eduspot.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(
    val route: String,
    val label: String,
    val icon: ImageVector
) {
    object Dashboard : BottomNavItem(
        route = Routes.HOME,
        label = "Dashboard",
        icon = Icons.Filled.Home
    )

    object MyCourses : BottomNavItem(
        route = Routes.MY_COURSES,
        label = "My Courses",
        icon = Icons.Filled.School
    )

    object Results : BottomNavItem(
        route = Routes.RESULTS,
        label = "Results",
        icon = Icons.Filled.BarChart
    )

    object Profile : BottomNavItem(
        route = Routes.PROFILE,
        label = "Profile",
        icon = Icons.Filled.Person
    )
}