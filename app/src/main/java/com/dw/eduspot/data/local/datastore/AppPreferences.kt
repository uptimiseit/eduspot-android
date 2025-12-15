package com.dw.eduspot.data.local.datastore

import com.dw.eduspot.domain.model.ThemeMode
import kotlinx.coroutines.flow.Flow

interface AppPreferences {
    val isLoggedIn: Flow<Boolean>
    val themeMode: Flow<ThemeMode>

    suspend fun setLoggedIn(value: Boolean)
    suspend fun setThemeMode(mode: ThemeMode)
}