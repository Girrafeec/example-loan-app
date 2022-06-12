package com.girrafeecstud.final_loan_app_zalessky.data.datasource

import com.girrafeecstud.final_loan_app_zalessky.data.network.loan.api.LoanApi
import com.girrafeecstud.final_loan_app_zalessky.data.network.login.ApiResult
import com.girrafeecstud.final_loan_app_zalessky.domain.entities.LoanConditions
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoanDataSourceImpl @Inject constructor(
    private val loanApi: LoanApi
) {
    suspend fun getLoanConditions(bearerToken: String?): Flow<ApiResult<Any>> {
        return flow {
            val response = loanApi.getLoanConditions(authorizationToken = bearerToken)
            val responseBody = response.body()
            if (response.isSuccessful && responseBody != null) {
                val loanConditions = LoanConditions(
                    maxAmount = responseBody.maxLoanAmount,
                    percent = responseBody.loanPercent,
                    period = responseBody.loanPeriod
                )
                emit(ApiResult.Success(loanConditions))
            }
            else {
                val errorMsg = response.errorBody()?.string()
                response.errorBody()?.close()
                emit(ApiResult.Error(exception = errorMsg.toString()))
            }
        }
    }
}
