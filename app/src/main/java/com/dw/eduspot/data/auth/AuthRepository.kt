package com.dw.eduspot.data.auth

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.tasks.await

data class AuthUser(
    val uid: String,
    val email: String?,
    val name: String?,
    val firebaseToken: String
)

class AuthRepository(
    private val firebaseAuth: FirebaseAuth
) {
    fun getCurrentUser() = firebaseAuth.currentUser

    suspend fun signInWithGoogle(idToken: String): Result<AuthUser> {
        return try {
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            val authResult = firebaseAuth.signInWithCredential(credential).await()
            val user = authResult.user ?: return Result.failure(Exception("User is null"))
            val firebaseToken = user.getIdToken(true).await().token
                ?: return Result.failure(Exception("Firebase token is null"))

            Result.success(AuthUser(user.uid, user.email, user.displayName, firebaseToken))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun logout() {
        firebaseAuth.signOut()
    }
}