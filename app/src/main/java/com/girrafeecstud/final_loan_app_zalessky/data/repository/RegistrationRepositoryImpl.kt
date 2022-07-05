package com.girrafeecstud.final_loan_app_zalessky.data.repository

import com.girrafeecstud.final_loan_app_zalessky.data.datasource.RegistrationDataSource
import com.girrafeecstud.final_loan_app_zalessky.data.network.login.ApiResult
import com.girrafeecstud.final_loan_app_zalessky.domain.repository.RegistrationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RegistrationRepositoryImpl @Inject constructor(
    private val dataSource: RegistrationDataSource
): RegistrationRepository {

    override suspend fun registration(
        userName: String,
        userPassword: String
    ): Flow<ApiResult<Any>> {
        return dataSource.registration(userName = userName, userPassword = userPassword)
    }

}