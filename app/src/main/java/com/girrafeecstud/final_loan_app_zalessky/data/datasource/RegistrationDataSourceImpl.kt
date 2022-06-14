package com.girrafeecstud.final_loan_app_zalessky.data.datasource

import com.girrafeecstud.final_loan_app_zalessky.data.network.login.ApiResult
import com.girrafeecstud.final_loan_app_zalessky.data.network.registration.api.RegistrationApi
import com.girrafeecstud.final_loan_app_zalessky.data.network.registration.dto.RegistrationRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RegistrationDataSourceImpl @Inject constructor(
    private val registrationApi: RegistrationApi
) {

    suspend fun registration(userName: String, userPassword: String): Flow<ApiResult<Any>> {
        val registrationRequest = RegistrationRequest(userName = userName, userPassword = userPassword)
        return flow {
            val response = registrationApi.registration(registrationRequest = registrationRequest)

            if (response.isSuccessful) {
                emit(ApiResult.Success(response.body()))
            }
            else {
                val errorMsg = response.errorBody()?.string()
                response.errorBody()?.close()
                //emit(ApiResult.Error(exception = errorMsg.toString()))
            }
        }
    }

}