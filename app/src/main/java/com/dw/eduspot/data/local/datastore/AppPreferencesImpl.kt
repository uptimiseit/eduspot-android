package com.dw.eduspot.data.local.datastore

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton
import dagger.hilt.android.qualifiers.ApplicationContext

private val Context.dataStore by preferencesDataStore("eduspot_prefs")

@Singleton
class AppPreferencesImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : AppPreferences {

    private object Keys {
        val LOGGED_IN = booleanPreferencesKey("logged_in")
        val USER_ID = stringPreferencesKey("user_id")
        val JWT = stringPreferencesKey("jwt")
        val ONBOARDING = booleanPreferencesKey("onboarding_seen")
    }

    // These already match interface types (Flow values)
    override val isLoggedIn: Flow<Boolean> =
        context.dataStore.data.map { it[Keys.LOGGED_IN] ?: false }

    override val userId: Flow<String?> =
        context.dataStore.data.map { it[Keys.USER_ID] }

    override val jwt: Flow<String?> =
        context.dataStore.data.map { it[Keys.JWT] }

    override val onboardingSeen: Flow<Boolean> =
        context.dataStore.data.map { it[Keys.ONBOARDING] ?: false }

    // FIXED overrides → now return Unit
    override suspend fun setLoggedIn(value: Boolean) {
        context.dataStore.edit { it[Keys.LOGGED_IN] = value }
    }

    override suspend fun setUserId(userId: String?) {
        context.dataStore.edit { prefs ->
            if (userId == null) prefs.remove(Keys.USER_ID)
            else prefs[Keys.USER_ID] = userId
        }
    }

    override suspend fun setJwt(token: String?) {
        context.dataStore.edit { prefs ->
            if (token == null) prefs.remove(Keys.JWT)
            else prefs[Keys.JWT] = token
        }
    }

    override suspend fun setOnboardingSeen(seen: Boolean) {
        context.dataStore.edit { it[Keys.ONBOARDING] = seen }
    }
}