package com.girrafeecstud.final_loan_app_zalessky.data.datasource

import com.girrafeecstud.final_loan_app_zalessky.data.network.loan.api.LoanApi
import com.girrafeecstud.final_loan_app_zalessky.data.network.login.ApiResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoanDataSourceImpl @Inject constructor(
    private val loanApi: LoanApi
) {
    suspend fun getLoanConditions(bearerToken: String?): Flow<ApiResult<Any>> {
        return flow {
            val response = loanApi.getLoanConditions(authorizationToken = bearerToken)

            if (response.isSuccessful) {
                emit(ApiResult.Success(response.body()))
            }
            else {
                val errorMsg = response.errorBody()?.string()
                response.errorBody()?.close()
                emit(ApiResult.Error(exception = errorMsg.toString()))
            }
        }
    }
}
