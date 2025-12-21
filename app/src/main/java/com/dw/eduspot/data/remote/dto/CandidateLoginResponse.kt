package com.dw.eduspot.data.remote.dto

data class CandidateLoginResponse(
    val message: String,
    val candidate: CandidateDto?,      // existing user
    val newCandidate: CandidateDto?,   // new user
    val token: String
)