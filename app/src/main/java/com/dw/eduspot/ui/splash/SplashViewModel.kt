package com.dw.eduspot.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dw.eduspot.data.local.datastore.AppPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    preferences: AppPreferences
) : ViewModel() {

    val destination: StateFlow<SplashDestination?> =
        combine(
            preferences.onboardingSeen,
            preferences.jwt
        ) { onboardingSeen, jwt ->

            when {
                !onboardingSeen ->
                    SplashDestination.Onboarding

                jwt.isNullOrEmpty() ->
                    SplashDestination.Login

                else ->
                    SplashDestination.Dashboard
            }

        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = null
        )
}