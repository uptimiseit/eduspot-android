package com.dw.eduspot

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

@HiltAndroidApp
class EduSpotApp : Application() {

    // Global app scope for background work (safe & recommended)
    val appScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun onCreate() {
        super.onCreate()

        // TODO: Add Firebase init later (FCM token, analytics)
        // Firebase.messaging.token.addOnSuccessListener { token -> log(token) }
    }
}