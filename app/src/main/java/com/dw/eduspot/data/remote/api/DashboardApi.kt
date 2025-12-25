package com.dw.eduspot.data.remote.api

import com.dw.eduspot.data.remote.dto.DashboardResponseDto
import retrofit2.http.GET

interface DashboardApi {

    @GET("dashboard")
    suspend fun getDashboard(): DashboardResponseDto
}