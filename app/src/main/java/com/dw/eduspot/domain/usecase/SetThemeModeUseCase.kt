package com.dw.eduspot.domain.usecase

import com.dw.eduspot.data.local.datastore.AppPreferences
import com.dw.eduspot.domain.model.ThemeMode
import javax.inject.Inject

class SetThemeModeUseCase @Inject constructor(
    private val prefs: AppPreferences
) {
    suspend operator fun invoke(mode: ThemeMode) =
        prefs.setThemeMode(mode)
}