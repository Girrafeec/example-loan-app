package com.girrafeecstud.final_loan_app_zalessky.domain.repository

import com.girrafeecstud.final_loan_app_zalessky.data.network.login.ApiResult
import com.girrafeecstud.final_loan_app_zalessky.domain.entities.LoanRequest
import kotlinx.coroutines.flow.Flow

interface LoanRepository {
    suspend fun getLoanConditions(bearerToken: String?): Flow<ApiResult<Any>>

    suspend fun applyLoan(bearerToken: String?, loanRequest: LoanRequest): Flow<ApiResult<Any>>

    suspend fun getLoansList(bearerToken: String?): Flow<ApiResult<Any>>

    suspend fun getLoanById(bearerToken: String?, loanId: Long): Flow<ApiResult<Any>>
}