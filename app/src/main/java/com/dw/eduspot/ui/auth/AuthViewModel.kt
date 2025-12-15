package com.dw.eduspot.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dw.eduspot.data.local.datastore.AppPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val preferences: AppPreferences
) : ViewModel() {

    fun fakeLogin() {
        viewModelScope.launch {
            preferences.setLoggedIn(true)
        }
    }
}