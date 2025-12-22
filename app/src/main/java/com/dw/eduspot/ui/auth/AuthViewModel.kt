package com.dw.eduspot.ui.auth

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dw.eduspot.data.auth.AuthRepository
import com.dw.eduspot.data.local.datastore.AppPreferences
import com.dw.eduspot.data.remote.api.AuthApi
import com.dw.eduspot.data.remote.dto.CandidateLoginRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class AuthDestination {
    DASHBOARD
}

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val authApi: AuthApi,
    private val preferences: AppPreferences
) : ViewModel() {

    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState

    fun loginWithGoogle(
        googleIdToken: String,
        onResult: (AuthDestination) -> Unit
    ) {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(
                    isLoading = true,
                    errorMessage = null
                )

                // 1️⃣ Firebase Auth
                val firebaseResult =
                    authRepository.signInWithGoogle(googleIdToken)

                if (firebaseResult.isFailure) {
                    throw firebaseResult.exceptionOrNull()
                        ?: Exception("Firebase login failed")
                }

                val firebaseUser = firebaseResult.getOrThrow()

                // 2️⃣ Backend login
                val response =
                    authApi.loginCandidate(
                        CandidateLoginRequest(
                            firebase_uid = firebaseUser.uid,
                            email = firebaseUser.email,
                            name = firebaseUser.name,
                            firebase_id_token = firebaseUser.firebaseToken
                        )
                    )

                val candidate =
                    response.candidate ?: response.newCandidate
                    ?: throw IllegalStateException(
                        "Candidate is null from backend"
                    )

                // 3️⃣ Save session
                preferences.setJwt(response.token)
                preferences.setUserId(candidate.id)
                preferences.setLoggedIn(true)

                // 4️⃣ Success
                _uiState.value = _uiState.value.copy(isLoading = false)
                onResult(AuthDestination.DASHBOARD)

            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = e.message ?: "Login failed"
                )
            }
        }
    }

    fun clearErrorMessage() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }

    fun logout() {
        viewModelScope.launch {
            preferences.setLoggedIn(false)
            preferences.setUserId(null)
            preferences.setJwt(null)
            authRepository.logout()
        }
    }
}