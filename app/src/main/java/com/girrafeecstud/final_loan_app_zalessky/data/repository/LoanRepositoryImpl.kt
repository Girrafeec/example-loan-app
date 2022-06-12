package com.girrafeecstud.final_loan_app_zalessky.data.repository

import com.girrafeecstud.final_loan_app_zalessky.data.datasource.LoanDataSourceImpl
import com.girrafeecstud.final_loan_app_zalessky.data.network.login.ApiResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoanRepositoryImpl @Inject constructor(
    private val dataSource: LoanDataSourceImpl
) {
    suspend fun getLoanConditions(bearerToken: String?): Flow<ApiResult<Any>> {
        return dataSource.getLoanConditions(bearerToken = bearerToken)
    }
}
