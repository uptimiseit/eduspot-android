package com.dw.eduspot.ui.auth

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.airbnb.lottie.compose.*
import com.dw.eduspot.R
import com.dw.eduspot.ui.theme.EduTextSecondary
import com.google.android.gms.auth.api.signin.GoogleSignIn
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onNavigateDashboard: () -> Unit,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val state = viewModel.uiState.collectAsState().value
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val googleAuthManager = remember { GoogleAuthManager(context) }
    val snackbarHostState = remember { SnackbarHostState() }

    // Show errors in snackbar
    LaunchedEffect(state.error) {
        state.error?.let { msg ->
            scope.launch { snackbarHostState.showSnackbar(msg) }
            viewModel.clearErrorMessage()
        }
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val token = googleAuthManager.getIdTokenFromIntent(result.data)
        if (token != null) {
            viewModel.loginWithGoogle(
                googleIdToken = token,
                onSuccess = { onNavigateDashboard() },
                onError = { err ->
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            err.message ?: "Google login failed"
                        )
                    }
                }
            )
        } else {
            scope.launch { snackbarHostState.showSnackbar("Google login failed") }
        }
    }

    Scaffold(
        Modifier.fillMaxSize(),
        containerColor = Color.White,
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { pad ->
        Box(Modifier
            .fillMaxSize()
            .padding(pad)) {
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(Modifier.height(48.dp))
                Image(painterResource(R.drawable.ic_splash_logo), null, Modifier.size(64.dp))
                Spacer(Modifier.height(32.dp))
                Button(
                    { launcher.launch(googleAuthManager.getSignInIntent()) },
                    enabled = !state.isLoading,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(2.dp),
                    elevation = ButtonDefaults.buttonElevation(2.dp)
                ) {
                    Text("Continue with Google", fontWeight = FontWeight.Medium)
                }
                Spacer(modifier = Modifier.weight(1f))

            }

            if (state.isLoading) {
                val loadingComposition by rememberLottieComposition(
                    LottieCompositionSpec.RawRes(R.raw.loading)
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.35f)),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        LottieAnimation(
                            composition = loadingComposition,
                            iterations = LottieConstants.IterateForever,
                            modifier = Modifier.size(120.dp)
                        )
                        Spacer(Modifier.height(16.dp))
                        Text("Signing you in…", color = Color.White)
                    }
                }
            }
        }
    }
}