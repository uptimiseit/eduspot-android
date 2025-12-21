package com.dw.eduspot.data.auth.model

data class AuthUser(
    val uid: String,
    val email: String?,
    val name: String?,
    val firebaseToken: String
)