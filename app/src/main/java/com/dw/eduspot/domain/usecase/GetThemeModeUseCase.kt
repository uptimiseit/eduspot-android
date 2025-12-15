package com.dw.eduspot.domain.usecase

import com.dw.eduspot.data.local.datastore.AppPreferences
import com.dw.eduspot.domain.model.ThemeMode
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetThemeModeUseCase @Inject constructor(
    private val prefs: AppPreferences
) {
    operator fun invoke(): Flow<ThemeMode> = prefs.themeMode
}