package com.dw.eduspot.data.local.datastore

import kotlinx.coroutines.flow.Flow

interface AppPreferences {
    val isLoggedIn: Flow<Boolean>
    val userId: Flow<String?>
    val jwt: Flow<String?>
    val onboardingSeen: Flow<Boolean>

    suspend fun setLoggedIn(value: Boolean)
    suspend fun setUserId(userId: String?)
    suspend fun setJwt(token: String?)
    suspend fun setOnboardingSeen(seen: Boolean)
}