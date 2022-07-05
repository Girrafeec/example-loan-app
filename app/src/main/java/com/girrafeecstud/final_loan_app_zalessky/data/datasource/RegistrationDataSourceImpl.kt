package com.girrafeecstud.final_loan_app_zalessky.data.datasource

import com.girrafeecstud.final_loan_app_zalessky.data.network.ApiErrorConverter
import com.girrafeecstud.final_loan_app_zalessky.data.network.login.ApiResult
import com.girrafeecstud.final_loan_app_zalessky.data.network.registration.RegistrationApiResponseConverter
import com.girrafeecstud.final_loan_app_zalessky.data.network.registration.api.RegistrationApi
import com.girrafeecstud.final_loan_app_zalessky.data.network.registration.dto.RegistrationRequest
import com.girrafeecstud.final_loan_app_zalessky.utils.NoNetworkException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import java.net.SocketTimeoutException
import javax.inject.Inject

class RegistrationDataSourceImpl @Inject constructor(
    private val registrationApi: RegistrationApi,
    private val registrationApiResponseConverter: RegistrationApiResponseConverter,
    private val apiErrorConverter: ApiErrorConverter
): RegistrationDataSource {

    override suspend fun registration(userName: String, userPassword: String): Flow<ApiResult<Any>> {
        val registrationRequest = RegistrationRequest(userName = userName, userPassword = userPassword)
        return flow {

            try {
                val response =
                    registrationApi.registration(registrationRequest = registrationRequest)
                val responseBody = response.body()

                if (response.isSuccessful && responseBody != null) {
                    val auth = registrationApiResponseConverter.getAuthFromRegistrationResponse(
                        registrationResponse = responseBody
                    )
                    emit(ApiResult.Success(_data = auth))
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