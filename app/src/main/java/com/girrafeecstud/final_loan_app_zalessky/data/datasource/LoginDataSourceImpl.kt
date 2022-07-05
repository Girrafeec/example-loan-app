package com.girrafeecstud.final_loan_app_zalessky.data.datasource

import com.girrafeecstud.final_loan_app_zalessky.data.network.ApiErrorConverter
import com.girrafeecstud.final_loan_app_zalessky.data.network.login.api.LoginApi
import com.girrafeecstud.final_loan_app_zalessky.data.network.login.dto.LoginRequest
import com.girrafeecstud.final_loan_app_zalessky.data.network.login.ApiResult
import com.girrafeecstud.final_loan_app_zalessky.utils.NoNetworkException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import java.net.SocketTimeoutException
import javax.inject.Inject

class LoginDataSourceImpl @Inject constructor(
    private val loginApi: LoginApi,
    private val apiErrorConverter: ApiErrorConverter
): LoginDataSource {

    override suspend fun login(userName: String, userPassword: String): Flow<ApiResult<Any>> {
        val loginRequest = LoginRequest(userName = userName, userPassword = userPassword)
        return flow {

            try {

                val response = loginApi.login(loginRequest = loginRequest)
                val responseBody = response.body()

                if (response.isSuccessful && responseBody != null) {
                    emit(ApiResult.Success(_data = response.body()))
                } else {
                    val error = apiErrorConverter.convertHttpError(
                        errorStatusCode = response.code(),
                        errorMessage = response.message()
                    )
                    emit(ApiResult.Error(apiError = error))
                }
            }catch (exception: NoNetworkException) {
                val error = apiErrorConverter.convertConnectionError()
                emit(ApiResult.Error(apiError = error))
            }catch (exception: SocketTimeoutException) {
                val error = apiErrorConverter.convertTimeoutError()
                emit(ApiResult.Error(apiError = error))
            }catch (exception: IOException) {
                val error = apiErrorConverter.convertTimeoutError()
                emit(ApiResult.Error(apiError = error))
            }
        }
    }

}