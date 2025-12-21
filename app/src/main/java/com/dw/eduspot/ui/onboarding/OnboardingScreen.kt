package com.dw.eduspot.ui.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.graphics.Color
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.dw.eduspot.ui.theme.EduBackground
import com.dw.eduspot.ui.theme.EduBlue

@Composable
fun OnboardingScreen(
    onFinished: () -> Unit,
    viewModel: OnboardingViewModel = hiltViewModel()
) {
    val pagerState = rememberPagerState { onboardingPages.size }

    LaunchedEffect(Unit) {
        viewModel.events.collect {
            onFinished()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(EduBackground)
    ) {

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) { page ->
            OnboardingPage(onboardingPages[page])
        }

        // 🔵 PAGE INDICATORS
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(onboardingPages.size) { index ->
                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .size(if (pagerState.currentPage == index) 10.dp else 8.dp)
                        .background(
                            if (pagerState.currentPage == index) EduBlue else EduBlue.copy(0.3f),
                            shape = CircleShape
                        )
                )
            }
        }

        // 🔥 GET STARTED
        if (pagerState.currentPage == onboardingPages.lastIndex) {
            Button(
                onClick = { viewModel.onFinished() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                colors = ButtonDefaults.buttonColors(containerColor = EduBlue)
            ) {
                Text("Get Started", color = Color.White)
            }
        }
    }
}