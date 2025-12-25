package com.dw.eduspot.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dw.eduspot.data.auth.AuthRepository
import com.dw.eduspot.data.local.datastore.AppPreferences
import com.dw.eduspot.data.remote.api.AuthApi
import com.dw.eduspot.data.remote.dto.CandidateLoginRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

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
        onSuccess: () -> Unit,
        onError: (Throwable) -> Unit
    ) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            try {
                val firebaseUser = authRepository.signInWithGoogle(googleIdToken).getOrThrow()

                val serverResponse = authApi.loginCandidate(
                    CandidateLoginRequest(
                        firebase_uid = firebaseUser.uid,
                        email = firebaseUser.email,
                        name = firebaseUser.name,
                        firebase_id_token = firebaseUser.firebaseToken
                    )
                )

                preferences.setJwt(serverResponse.token)
                preferences.setLoggedIn(true)
                preferences.setUserId(firebaseUser.uid)

                _uiState.value = _uiState.value.copy(isLoading = false, error = null)
                onSuccess()

            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(isLoading = false, error = e.localizedMessage)
                onError(e)
            }
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
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