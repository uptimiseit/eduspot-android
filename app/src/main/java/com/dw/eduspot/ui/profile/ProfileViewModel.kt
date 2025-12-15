package com.dw.eduspot.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dw.eduspot.data.local.datastore.AppPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val preferences: AppPreferences
) : ViewModel() {

    fun logout() {
        viewModelScope.launch {
            preferences.setLoggedIn(false)
        }
    }
}