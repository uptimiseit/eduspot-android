package com.dw.eduspot.ui.auth

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dw.eduspot.data.auth.AuthRepository
import com.dw.eduspot.data.local.datastore.AppPreferences
import com.dw.eduspot.data.remote.api.AuthApi
import com.dw.eduspot.data.remote.dto.CandidateLoginRequest
import dagger.hilt.android.lifecycle.HiltViewModel
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

    fun loginWithGoogle(
        googleIdToken: String,
        onResult: (AuthDestination) -> Unit
    ) {
        viewModelScope.launch {

            // 1️⃣ Firebase Auth
            val firebaseResult =
                authRepository.signInWithGoogle(googleIdToken)

            if (firebaseResult.isFailure) return@launch

            val firebaseUser = firebaseResult.getOrThrow()

            // 2️⃣ Backend Login
            val response =
                authApi.loginCandidate(
                    CandidateLoginRequest(
                        firebase_uid = firebaseUser.uid,
                        email = firebaseUser.email,
                        name = firebaseUser.name,
                        firebase_id_token = firebaseUser.firebaseToken
                    )
                )

            // 🔍 Debug (keep for now)
            Log.d("AUTH", "Raw response = $response")

            // 3️⃣ Resolve user (NEW or EXISTING)
            val candidate =
                response.candidate ?: response.newCandidate
                ?: throw IllegalStateException(
                    "Candidate is null from backend"
                )

            // 4️⃣ Save session (authoritative)
            preferences.setJwt(response.token)
            preferences.setUserId(candidate.id)
            preferences.setLoggedIn(true)

            // onboardingSeen untouched

            // 5️⃣ Navigate
            onResult(AuthDestination.DASHBOARD)
        }
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