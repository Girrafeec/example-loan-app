package com.girrafeecstud.final_loan_app_zalessky.domain.repository

import com.girrafeecstud.final_loan_app_zalessky.data.network.login.ApiResult
import kotlinx.coroutines.flow.Flow

interface RegistrationRepository {
    suspend fun registration(userName: String, userPassword: String): Flow<ApiResult<Any>>
}