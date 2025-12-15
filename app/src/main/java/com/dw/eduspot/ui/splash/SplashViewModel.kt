package com.dw.eduspot.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dw.eduspot.data.fake.FakeSessionRepository
import com.dw.eduspot.di.IoDispatcher
import com.dw.eduspot.domain.usecase.GetLoginStateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class SplashDestination {
    object Login : SplashDestination()
    object Dashboard : SplashDestination()
}

@HiltViewModel
class SplashViewModel @Inject constructor(
    getLoginStateUseCase: GetLoginStateUseCase
) : ViewModel() {

    private val _destination = MutableStateFlow<SplashDestination?>(null)
    val destination: StateFlow<SplashDestination?> = _destination

    init {
        viewModelScope.launch {
            getLoginStateUseCase().collect { loggedIn ->
                _destination.value =
                    if (loggedIn) SplashDestination.Dashboard
                    else SplashDestination.Login
            }
        }
    }
}