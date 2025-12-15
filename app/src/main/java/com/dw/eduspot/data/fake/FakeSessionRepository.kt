package com.dw.eduspot.data.fake

import com.dw.eduspot.domain.model.AppSession
import kotlinx.coroutines.delay
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FakeSessionRepository @Inject constructor() {

    suspend fun getSession(): AppSession {
        delay(1200) // simulate IO
        return AppSession(isLoggedIn = false)
    }
}