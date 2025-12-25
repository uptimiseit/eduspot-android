package com.dw.eduspot.navigation

import androidx.compose.material3.*
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.compose.runtime.*
import androidx.compose.ui.res.painterResource
import com.dw.eduspot.R

@Composable
fun BottomBar(nav: NavHostController) {
    val backStack by nav.currentBackStackEntryAsState()
    val currentRoute = backStack?.destination?.route

    val items = listOf(
        Routes.HOME to R.raw.ic_home,
        Routes.MY_COURSES to R.raw.course,
        Routes.RESULT to R.raw.result
    )

    NavigationBar {
        items.forEach { (route, icon) ->
            NavigationBarItem(
                selected = currentRoute == route,
                onClick = {
                    nav.navigate(route) {
                        popUpTo(Routes.HOME) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = { Icon(painterResource(icon), null) },
                label = { Text(route.replace("_", " ").replaceFirstChar { it.uppercase() }) }
            )
        }
    }
}