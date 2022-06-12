package com.girrafeecstud.final_loan_app_zalessky.data.repository

import com.girrafeecstud.final_loan_app_zalessky.data.datasource.LoginDataSourceImpl
import com.girrafeecstud.final_loan_app_zalessky.data.network.login.ApiResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val dataSource: LoginDataSourceImpl
) {
    suspend fun login(userName: String, userPassword: String): Flow<ApiResult<Any>> {
        return dataSource.login(userName = userName, userPassword = userPassword)
    }
}