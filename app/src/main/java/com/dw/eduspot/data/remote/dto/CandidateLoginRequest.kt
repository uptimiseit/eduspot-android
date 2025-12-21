package com.dw.eduspot.data.remote.dto


data class CandidateLoginRequest(

    val firebase_uid: String,
    val email: String?,
    val name: String?,
    val firebase_id_token: String
)