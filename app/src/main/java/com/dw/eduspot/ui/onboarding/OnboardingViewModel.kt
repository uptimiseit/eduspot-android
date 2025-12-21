package com.dw.eduspot.ui.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dw.eduspot.data.local.datastore.AppPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val preferences: AppPreferences
) : ViewModel() {

    private val _events = Channel<Unit>(Channel.BUFFERED)
    val events = _events.receiveAsFlow()

    fun onFinished() {
        viewModelScope.launch {
            preferences.setOnboardingSeen(true)
            _events.send(Unit)
        }
    }
}