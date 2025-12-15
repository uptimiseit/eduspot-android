package com.dw.eduspot.ui.theme

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dw.eduspot.domain.model.ThemeMode
import com.dw.eduspot.domain.usecase.GetThemeModeUseCase
import com.dw.eduspot.domain.usecase.SetThemeModeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ThemeViewModel @Inject constructor(
    private val getThemeMode: GetThemeModeUseCase,
    private val setThemeMode: SetThemeModeUseCase
) : ViewModel() {

    val themeMode: StateFlow<ThemeMode> =
        getThemeMode().stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            ThemeMode.SYSTEM
        )

    fun updateTheme(mode: ThemeMode) {
        viewModelScope.launch {
            setThemeMode(mode)
        }
    }
}