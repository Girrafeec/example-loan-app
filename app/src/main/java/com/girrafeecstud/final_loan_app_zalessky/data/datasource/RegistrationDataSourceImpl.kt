package com.girrafeecstud.final_loan_app_zalessky.data.datasource

import com.girrafeecstud.final_loan_app_zalessky.data.network.ApiResult
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

        }
    }

}