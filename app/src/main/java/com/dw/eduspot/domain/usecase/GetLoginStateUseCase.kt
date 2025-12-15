package com.dw.eduspot.domain.usecase

import com.dw.eduspot.data.local.datastore.AppPreferences
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLoginStateUseCase @Inject constructor(
    private val prefs: AppPreferences
) {
    operator fun invoke(): Flow<Boolean> = prefs.isLoggedIn
}