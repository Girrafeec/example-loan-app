package com.girrafeecstud.final_loan_app_zalessky.data.datasource

import com.girrafeecstud.final_loan_app_zalessky.data.network.ApiErrorConverter
import com.girrafeecstud.final_loan_app_zalessky.data.network.login.api.LoginApi
import com.girrafeecstud.final_loan_app_zalessky.data.network.login.dto.LoginRequest
import com.girrafeecstud.final_loan_app_zalessky.data.network.login.ApiResult
import com.girrafeecstud.final_loan_app_zalessky.utils.NoNetworkException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginDataSourceImpl @Inject constructor(
    private val loginApi: LoginApi,
    private val apiErrorConverter: ApiErrorConverter
) {

    suspend fun login(userName: String, userPassword: String): Flow<ApiResult<Any>> {
        val loginRequest = LoginRequest(userName = userName, userPassword = userPassword)
        return flow {

            try {

                val response = loginApi.login(loginRequest = loginRequest)
                val responseBody = response.body()

                if (response.isSuccessful && responseBody != null) {
                    emit(ApiResult.Success(response.body()))
                } else {
                    val errorMsg = response.errorBody()?.string()
                    response.errorBody()?.close()
                    val error = apiErrorConverter.convertHttpError(
                        errorStatusCode = response.code(),
                        errorMessage = response.message()
                    )

                    emit(ApiResult.Error(apiError = error))
                }
            }catch (exception: NoNetworkException) {
                val error = apiErrorConverter.convertConnectionError(exceptionMessage = exception.message.toString())
                emit(ApiResult.Error(apiError = error))
            }
        }
    }

}