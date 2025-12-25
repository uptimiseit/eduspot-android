package com.dw.eduspot.data.remote.dto

data class CandidateLoginResponse(
    val message: String,
    val candidate: CandidateDto?,
    val newCandidate: CandidateDto?,
    val token: String
)