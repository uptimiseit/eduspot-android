package com.dw.eduspot

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.CompositionLocalProvider
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import dagger.hilt.android.AndroidEntryPoint
import com.dw.eduspot.navigation.AppNavHost
import com.dw.eduspot.ui.theme.EduSpotTheme
import com.dw.eduspot.utils.LocalAppWindowInfo
import com.dw.eduspot.utils.toAppWindowInfo

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {

        // Android 12+ splash
        installSplashScreen()
        super.onCreate(savedInstanceState)

        setContent {

            // ✅ MUST be inside setContent (Composable scope)
            val windowSizeClass = calculateWindowSizeClass(this)

            // ✅ Provide window info to whole app
            CompositionLocalProvider(
                LocalAppWindowInfo provides windowSizeClass.toAppWindowInfo()
            ) {
                EduSpotTheme {
                    AppNavHost()
                }
            }
        }
    }
}