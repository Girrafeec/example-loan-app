package com.girrafeecstud.final_loan_app_zalessky.data.repository

import com.girrafeecstud.final_loan_app_zalessky.data.datasource.LoanDataSourceImpl
import com.girrafeecstud.final_loan_app_zalessky.data.network.login.ApiResult
import com.girrafeecstud.final_loan_app_zalessky.domain.entities.LoanRequest
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoanRepositoryImpl @Inject constructor(
    private val dataSource: LoanDataSourceImpl
) {
    suspend fun getLoanConditions(bearerToken: String?): Flow<ApiResult<Any>> {
        return dataSource.getLoanConditions(bearerToken = bearerToken)
    }

    suspend fun applyLoan(bearerToken: String?, loanRequest: LoanRequest): Flow<ApiResult<Any>> {
        return dataSource.applyLoan(
            bearerToken = bearerToken,
            loanRequest = loanRequest
        )
    }

    suspend fun getLoansList(bearerToken: String?): Flow<ApiResult<Any>> {
        return dataSource.getLoansList(bearerToken = bearerToken)
    }

    suspend fun getLoanById(bearerToken: String?, loanId: Long): Flow<ApiResult<Any>> {
        return dataSource.getLoanById(
            bearerToken = bearerToken,
            loanId = loanId
        )
    }
}
