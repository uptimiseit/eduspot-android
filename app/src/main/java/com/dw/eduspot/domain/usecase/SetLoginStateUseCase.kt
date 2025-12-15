package com.dw.eduspot.domain.usecase

import com.dw.eduspot.data.local.datastore.AppPreferences
import javax.inject.Inject

class SetLoginStateUseCase @Inject constructor(
    private val prefs: AppPreferences
) {
    suspend operator fun invoke(value: Boolean) =
        prefs.setLoggedIn(value)
}