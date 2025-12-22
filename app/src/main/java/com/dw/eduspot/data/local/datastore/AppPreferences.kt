package com.dw.eduspot.data.local.datastore

import kotlinx.coroutines.flow.Flow

interface AppPreferences {

    // -------- Session --------
    val isLoggedIn: Flow<Boolean>
    val userId: Flow<String?>
    val jwt: Flow<String?>

    // -------- App State --------
    val onboardingSeen: Flow<Boolean>


    // -------- Setters --------
    suspend fun setLoggedIn(value: Boolean)
    suspend fun setUserId(userId: String?)
    suspend fun setJwt(token: String?)
    suspend fun setOnboardingSeen(seen: Boolean)

}