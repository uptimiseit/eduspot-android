package com.dw.eduspot.data.local.datastore

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.dw.eduspot.domain.model.ThemeMode
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore(name = "eduspot_prefs")

@Singleton
class AppPreferencesImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : AppPreferences {

    private object Keys {
        val LOGGED_IN = booleanPreferencesKey("logged_in")
        val USER_ID = stringPreferencesKey("user_id")
        val JWT = stringPreferencesKey("jwt")
        val ONBOARDING_SEEN = booleanPreferencesKey("onboarding_seen")
        val THEME_MODE = stringPreferencesKey("theme_mode")
    }

    // -------- Getters --------
    override val isLoggedIn: Flow<Boolean> =
        context.dataStore.data.map { it[Keys.LOGGED_IN] ?: false }

    override val userId: Flow<String?> =
        context.dataStore.data.map { it[Keys.USER_ID] }

    override val jwt: Flow<String?> =
        context.dataStore.data.map { it[Keys.JWT] }

    override val onboardingSeen: Flow<Boolean> =
        context.dataStore.data.map { it[Keys.ONBOARDING_SEEN] ?: false }

    override val themeMode: Flow<ThemeMode> =
        context.dataStore.data.map {
            ThemeMode.valueOf(
                it[Keys.THEME_MODE] ?: ThemeMode.SYSTEM.name
            )
        }

    // -------- Setters --------
    override suspend fun setLoggedIn(value: Boolean) {
        context.dataStore.edit { it[Keys.LOGGED_IN] = value }
    }

    override suspend fun setUserId(userId: String?) {
        context.dataStore.edit {
            if (userId == null) it.remove(Keys.USER_ID)
            else it[Keys.USER_ID] = userId
        }
    }

    override suspend fun setJwt(token: String?) {
        context.dataStore.edit {
            if (token == null) it.remove(Keys.JWT)
            else it[Keys.JWT] = token
        }
    }

    override suspend fun setOnboardingSeen(seen: Boolean) {
        context.dataStore.edit { it[Keys.ONBOARDING_SEEN] = seen }
    }

    override suspend fun setThemeMode(mode: ThemeMode) {
        context.dataStore.edit { it[Keys.THEME_MODE] = mode.name }
    }
}