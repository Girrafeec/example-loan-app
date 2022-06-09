package com.girrafeecstud.final_loan_app_zalessky.data.repository

import android.util.Log
import com.girrafeecstud.final_loan_app_zalessky.data.network.login.api.LoginApi
import com.girrafeecstud.final_loan_app_zalessky.data.network.login.dto.LoginRequest
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val loginApi: LoginApi
) {
    suspend fun login(loginRequest: LoginRequest) {
        val response = loginApi.login(loginRequest = loginRequest)
        Log.i("tag log rep", response.message())
    }
}