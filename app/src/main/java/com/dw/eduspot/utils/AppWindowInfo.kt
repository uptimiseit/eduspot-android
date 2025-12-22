package com.dw.eduspot.utils

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.staticCompositionLocalOf

/**
 * App-level window info abstraction
 */
data class AppWindowInfo(
    val isCompact: Boolean,
    val isMedium: Boolean,
    val isExpanded: Boolean,
    val isTablet: Boolean
)

/**
 * CompositionLocal to access window info anywhere in UI
 */
val LocalAppWindowInfo = staticCompositionLocalOf<AppWindowInfo> {
    error("AppWindowInfo not provided. Did you forget to wrap your app with CompositionLocalProvider?")
}

/**
 * Extension function to convert WindowSizeClass → AppWindowInfo
 */
fun WindowSizeClass.toAppWindowInfo(): AppWindowInfo {
    return AppWindowInfo(
        isCompact = widthSizeClass == WindowWidthSizeClass.Compact,
        isMedium = widthSizeClass == WindowWidthSizeClass.Medium,
        isExpanded = widthSizeClass == WindowWidthSizeClass.Expanded,
        isTablet = widthSizeClass != WindowWidthSizeClass.Compact
    )
}