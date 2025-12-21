package com.dw.eduspot.ui.onboarding

import androidx.lifecycle.ViewModel
import com.dw.eduspot.data.local.datastore.AppPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OnboardingVM @Inject constructor(
    val preferences: AppPreferences
) : ViewModel()