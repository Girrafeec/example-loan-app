package com.girrafeecstud.final_loan_app_zalessky.data.repository

import com.girrafeecstud.final_loan_app_zalessky.data.datasource.RegistrationDataSourceImpl
import com.girrafeecstud.final_loan_app_zalessky.data.network.login.ApiResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RegistrationRepositoryImpl @Inject constructor(
    private val dataSource: RegistrationDataSourceImpl
) {

    suspend fun registration(
        userName: String,
        userPassword: String
    ): Flow<ApiResult<Any>> {
        return dataSource.registration(userName = userName, userPassword = userPassword)
    }

}