package com.dw.eduspot.ui.auth

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

class GoogleAuthManager(context: Context) {

    private val webClientId: String = context.getString(
        context.resources.getIdentifier("default_web_client_id", "string", context.packageName)
    )

    private val client: GoogleSignInClient = GoogleSignIn.getClient(
        context,
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(webClientId)
            .requestEmail()
            .build()
    )

    fun getSignInIntent() = client.signInIntent

    fun extractIdToken(intentData: android.content.Intent?): String? {
        val account = GoogleSignIn.getSignedInAccountFromIntent(intentData).getResult(Exception::class.java)
        return account.idToken
    }

    fun signOut() {
        client.signOut()
    }
}