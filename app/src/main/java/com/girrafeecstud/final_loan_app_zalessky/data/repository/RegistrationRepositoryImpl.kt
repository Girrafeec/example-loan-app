package com.girrafeecstud.final_loan_app_zalessky.data.repository

import android.util.Log
import com.girrafeecstud.final_loan_app_zalessky.data.datasource.RegistrationDataSourceImpl
import com.girrafeecstud.final_loan_app_zalessky.data.network.ApiResult
import com.girrafeecstud.final_loan_app_zalessky.data.network.registration.api.RegistrationApi
import com.girrafeecstud.final_loan_app_zalessky.data.network.registration.dto.RegistrationRequest
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RegistrationRepositoryImpl @Inject constructor(
    private val registrationDataSourceImpl: RegistrationDataSourceImpl
) {

    suspend fun registration(
        userName: String,
        userPassword: String
    ): Flow<ApiResult<Any>> {
        return registrationDataSourceImpl.registration(userName = userName, userPassword = userPassword)
    }

}