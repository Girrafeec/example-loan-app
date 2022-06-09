package com.girrafeecstud.final_loan_app_zalessky.data.network.login.api

import com.girrafeecstud.final_loan_app_zalessky.data.network.ApiUrlConfig
import com.girrafeecstud.final_loan_app_zalessky.data.network.login.dto.LoginRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApi {

    @POST(ApiUrlConfig.LOGIN_API_URL)
    suspend fun login(@Body loginRequest: LoginRequest): Response<String>

}