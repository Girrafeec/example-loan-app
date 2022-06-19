package com.girrafeecstud.final_loan_app_zalessky.data.repository

import com.girrafeecstud.final_loan_app_zalessky.data.datasource.LocalLoanDataSourceImpl
import com.girrafeecstud.final_loan_app_zalessky.data.datasource.RemoteLoanDataSourceImpl
import com.girrafeecstud.final_loan_app_zalessky.data.network.login.ApiResult
import com.girrafeecstud.final_loan_app_zalessky.domain.entities.Loan
import com.girrafeecstud.final_loan_app_zalessky.domain.entities.LoanRequest
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class LoanRepositoryImpl @Inject constructor(
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

        lateinit var localDataSourceResult: List<Loan>
        localDataSource.getLoansList().collect { daoLoansList ->
            localDataSourceResult = daoLoansList
        }

        return remoteDataSource.getLoansList(bearerToken = bearerToken)
            .onEach { apiResult ->
                when (apiResult) {
                    is ApiResult.Success -> {
                        localDataSource.clearLoans()
                        localDataSource.fillLoansList(loans = apiResult.data as List<Loan>)
                    }
                }
            }
            .onStart {
                emit(ApiResult.Success(_data = localDataSourceResult))
            }
    }

    suspend fun getLoanById(bearerToken: String?, loanId: Long): Flow<ApiResult<Any>> {

        lateinit var localDataSourceResult: Loan
        localDataSource.getLoanById(loanId = loanId).collect { daoLoan ->
            localDataSourceResult = daoLoan
        }

        return remoteDataSource.getLoanById(
            bearerToken = bearerToken,
            loanId = loanId
        ).onStart {
            emit(ApiResult.Success(_data = localDataSourceResult))
        }
    }
}
