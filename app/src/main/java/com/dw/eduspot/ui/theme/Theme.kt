package com.dw.eduspot.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat

@Composable
fun EduSpotTheme(
    content: @Composable () -> Unit
) {
    val colorScheme = lightColorScheme(
        primary = EduBlue,
        secondary = EduGold,
        background = EduBackground,
        surface = Color.White,
        onPrimary = Color.White,
        onBackground = EduTextPrimary,
        onSurface = EduTextPrimary
    )

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window

            // ✅ White status bar
            window.statusBarColor = Color.White.toArgb()
            window.navigationBarColor = Color.White.toArgb()

            // ✅ Dark icons
            WindowCompat.getInsetsController(window, view).apply {
                isAppearanceLightStatusBars = true
                isAppearanceLightNavigationBars = true
            }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        content = content
    )
}
object Dimens {

    // Padding
    val screenPadding = 16.dp
    val sectionSpacing = 24.dp
    val itemSpacing = 12.dp

    // Card sizes
    val examCardWidthCompact = 260.dp
    val examCardWidthExpanded = 320.dp

    // Hero
    val heroHeightCompact = 220.dp
    val heroHeightExpanded = 300.dp
}