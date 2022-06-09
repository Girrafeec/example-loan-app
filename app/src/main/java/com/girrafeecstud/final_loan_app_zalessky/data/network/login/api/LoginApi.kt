package com.girrafeecstud.final_loan_app_zalessky.data.network.login.api

import android.hardware.biometrics.BiometricManager
import com.girrafeecstud.final_loan_app_zalessky.data.network.ApiUrlConfig
import com.girrafeecstud.final_loan_app_zalessky.data.network.login.dto.LoginRequest
import com.girrafeecstud.final_loan_app_zalessky.data.network.login.dto.LoginResponse
import com.google.gson.JsonObject
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApi {

    @POST(ApiUrlConfig.LOGIN_API_URL)
    suspend fun login(@Body loginRequest: LoginRequest): Response<String>

}