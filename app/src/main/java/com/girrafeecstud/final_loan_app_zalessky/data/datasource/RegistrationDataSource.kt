package com.girrafeecstud.final_loan_app_zalessky.data.datasource

import com.girrafeecstud.final_loan_app_zalessky.data.network.login.ApiResult
import kotlinx.coroutines.flow.Flow

interface RegistrationDataSource {
    suspend fun registration(userName: String, userPassword: String): Flow<ApiResult<Any>>
}