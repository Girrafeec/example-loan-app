package com.girrafeecstud.final_loan_app_zalessky.data.repository

import android.util.Log
import com.girrafeecstud.final_loan_app_zalessky.data.datasource.LocalLoanDataSourceImpl
import com.girrafeecstud.final_loan_app_zalessky.data.datasource.RemoteLoanDataSourceImpl
import com.girrafeecstud.final_loan_app_zalessky.data.network.login.ApiResult
import com.girrafeecstud.final_loan_app_zalessky.domain.entities.Loan
import com.girrafeecstud.final_loan_app_zalessky.domain.entities.LoanRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class RemoteLoanRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteLoanDataSourceImpl,
    private val localDataSource: LocalLoanDataSourceImpl

) {
    suspend fun getLoanConditions(bearerToken: String?): Flow<ApiResult<Any>> {
        return remoteDataSource.getLoanConditions(bearerToken = bearerToken)
    }

    suspend fun applyLoan(bearerToken: String?, loanRequest: LoanRequest): Flow<ApiResult<Any>> {
        return remoteDataSource.applyLoan(
            bearerToken = bearerToken,
            loanRequest = loanRequest
        )
    }

    suspend fun getLoansList(bearerToken: String?): Flow<ApiResult<Any>> {
        val result = remoteDataSource.getLoansList(bearerToken = bearerToken)

        result.collect { apiResilt ->
            when (apiResilt) {
                is ApiResult.Success -> {
                    localDataSource.clearLoans()
                    localDataSource.fillLoansList(loans = apiResilt.data as List<Loan>)
                }
            }
        }

        return result
    }

    suspend fun getLoanById(bearerToken: String?, loanId: Long): Flow<ApiResult<Any>> {
        return remoteDataSource.getLoanById(
            bearerToken = bearerToken,
            loanId = loanId
        )
    }
}
