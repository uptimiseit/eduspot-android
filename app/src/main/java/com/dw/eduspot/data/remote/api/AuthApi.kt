package com.dw.eduspot.data.remote.api

import com.dw.eduspot.data.remote.dto.CandidateLoginRequest
import com.dw.eduspot.data.remote.dto.CandidateLoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("lapi/candidates/login")
    suspend fun loginCandidate(
        @Body request: CandidateLoginRequest
    ): CandidateLoginResponse
}