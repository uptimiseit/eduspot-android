package com.dw.eduspot.ui.auth

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import com.airbnb.lottie.compose.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.dw.eduspot.R
import com.dw.eduspot.ui.theme.EduBackground
import com.dw.eduspot.ui.theme.EduTextPrimary
import com.dw.eduspot.ui.theme.EduTextSecondary
import com.google.android.gms.auth.api.signin.GoogleSignIn

@Composable
fun LoginScreen(
    onNavigateDashboard: () -> Unit,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val googleAuthManager = remember { GoogleAuthManager(context) }

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState.errorMessage) {
        uiState.errorMessage?.let { message ->
            snackbarHostState.showSnackbar(message)
            viewModel.clearErrorMessage()
        }
    }

    val launcher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            val task =
                GoogleSignIn.getSignedInAccountFromIntent(result.data)

            runCatching { task.result.idToken }
                .onSuccess { token ->
                    if (token != null) {
                        viewModel.loginWithGoogle(token) {
                            onNavigateDashboard()
                        }
                    }
                }
        }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = EduBackground,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer(modifier = Modifier.height(56.dp))

                /* ---------------- LOGO + BRAND ---------------- */
                Image(
                    painter = painterResource(R.drawable.ic_splash_logo),
                    contentDescription = "EduSpot Logo",
                    modifier = Modifier.size(72.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "EduSpot",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = EduTextPrimary
                )

                Text(
                    text = "Smart Exam Preparation",
                    style = MaterialTheme.typography.bodyMedium,
                    color = EduTextSecondary
                )

                Spacer(modifier = Modifier.height(36.dp))


                /* ---------------- HERO LOTTIE ---------------- */

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    val heroComposition by rememberLottieComposition(
                        LottieCompositionSpec.RawRes(R.raw.study)
                    )

                    LottieAnimation(
                        composition = heroComposition,
                        iterations = LottieConstants.IterateForever,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(260.dp)
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                /* ---------------- VALUE PROPOSITION ---------------- */
                Text(
                    text = "Prepare Smarter.\nScore Better.",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center,
                    color = EduTextPrimary
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Real exam mock tests, detailed analytics, and\npersonalized practice for serious aspirants.",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    color = EduTextSecondary
                )
                /* ---------------- GOOGLE LOGIN BUTTON ---------------- */
                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = {
                        if (!uiState.isLoading) {
                            launcher.launch(googleAuthManager.getSignInIntent())
                        }
                    },
                    enabled = !uiState.isLoading,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color.Black
                    ),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_google),
                            contentDescription = "Google",
                            modifier = Modifier.size(20.dp)
                        )

                        Spacer(modifier = Modifier.width(12.dp))

                        Text(
                            text = "Continue with Google",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
                /* ---------------- TERMS TEXT ---------------- */
                    Spacer(modifier = Modifier.weight(1f))

                    Text(
                        text = "By continuing, you agree to our Terms & Privacy Policy",
                        style = MaterialTheme.typography.labelSmall,
                        color = EduTextSecondary,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(24.dp))
                }

                /* ---------------- LOADING OVERLAY ---------------- */
                if (uiState.isLoading) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Black.copy(alpha = 0.35f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {

                            val loadingComposition by rememberLottieComposition(
                                LottieCompositionSpec.RawRes(R.raw.loading)
                            )

                            LottieAnimation(
                                composition = loadingComposition,
                                iterations = LottieConstants.IterateForever,
                                modifier = Modifier.size(120.dp)
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            Text(
                                text = "Signing you in…",
                                color = Color.White,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }

        }
    }
