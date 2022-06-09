package com.girrafeecstud.final_loan_app_zalessky.data.repository

import android.util.Log
import com.girrafeecstud.final_loan_app_zalessky.data.network.ApiStatus
import com.girrafeecstud.final_loan_app_zalessky.data.network.login.api.LoginApi
import com.girrafeecstud.final_loan_app_zalessky.data.network.login.dto.LoginRequest
import com.girrafeecstud.final_loan_app_zalessky.data.network.registration.ApiResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val loginApi: LoginApi
) {
    suspend fun login(userName: String, userPassword: String): Flow<ApiResult<Any>> {
        val loginRequest = LoginRequest(userName = userName, userPassword = userPassword)
        return flow {

            val response = loginApi.login(loginRequest = loginRequest)

            if (response.isSuccessful) {
                emit(ApiResult.Success(response.body()))
            }
            else {
                val errorMsg = response.errorBody()?.string()
                response.errorBody()?.close()
                emit(ApiResult.Error(exception = errorMsg.toString()))
            }
            Log.i("tag rep log", response.code().toString())
            Log.i("tag rep log", response.body().toString())
        }
    }
}