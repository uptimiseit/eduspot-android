package com.dw.eduspot.ui.auth

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class GoogleAuthManager(
    context: Context
) {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val signInClient: GoogleSignInClient =
        GoogleSignIn.getClient(
            context,
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(
                    context.getString(
                        context.resources.getIdentifier(
                            "default_web_client_id",
                            "string",
                            context.packageName
                        )
                    )
                )
                .requestEmail()
                .build()
        )

    fun getSignInIntent() = signInClient.signInIntent

    fun signOut() {
        auth.signOut()
        signInClient.signOut()
    }

    fun firebaseAuthWithGoogle(
        idToken: String,
        onSuccess: () -> Unit,
        onError: (Throwable) -> Unit
    ) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onError(it) }
    }
}