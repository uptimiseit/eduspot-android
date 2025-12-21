package com.dw.eduspot.ui.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.*
import com.dw.eduspot.R
import com.dw.eduspot.ui.theme.EduTextPrimary
import com.dw.eduspot.ui.theme.EduTextSecondary

@Composable
fun OnboardingPage(
    page: OnboardingPageData
) {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(page.lottieRes)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        // 🔵 LOGO
        Image(
            painter = painterResource(id = R.drawable.ic_splash_logo),
            contentDescription = null,
            modifier = Modifier.size(72.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // 🎬 LOTTIE
        LottieAnimation(
            composition = composition,
            iterations = LottieConstants.IterateForever,
            modifier = Modifier.size(260.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = page.title,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = EduTextPrimary,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = page.subtitle,
            style = MaterialTheme.typography.bodyLarge,
            color = EduTextSecondary,
            textAlign = TextAlign.Center
        )
    }
}