package com.girrafeecstud.final_loan_app_zalessky.data.datasource

import com.girrafeecstud.final_loan_app_zalessky.data.network.loan.api.LoanApi
import com.girrafeecstud.final_loan_app_zalessky.data.network.loan.dto.LoanApiRequest
import com.girrafeecstud.final_loan_app_zalessky.data.network.login.ApiResult
import com.girrafeecstud.final_loan_app_zalessky.domain.entities.Loan
import com.girrafeecstud.final_loan_app_zalessky.domain.entities.LoanConditions
import com.girrafeecstud.final_loan_app_zalessky.domain.entities.LoanRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.LocalDateTime
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
                emit(ApiResult.Success(_data = loanConditions))
            }
            else {
                val errorMsg = response.errorBody()?.string()
                response.errorBody()?.close()
                emit(ApiResult.Error(exception = errorMsg.toString()))
            }
        }
    }

    suspend fun applyLoan(bearerToken: String?, loanRequest: LoanRequest): Flow<ApiResult<Any>> {
        val loanApiRequest = LoanApiRequest(
            loanAmount = loanRequest.loanAmount,
            loanPeriod = loanRequest.loanPeriod,
            loanPercent = loanRequest.loanPercent,
            borrowerFirstName = loanRequest.borrowerFirstName,
            borrowerLastName = loanRequest.borrowerLastName,
            borrowerPhoneNumber = loanRequest.borrowerPhoneNumber
        )

        return flow {
            val response = loanApi.applyLoan(
                authorizationToken = bearerToken,
                loanApiRequest = loanApiRequest
            )

            val responseBody = response.body()

            if (response.isSuccessful && responseBody != null) {
                val loan = Loan(
                    loanAmount = responseBody.loanAmount,
                    loanIssueDate = responseBody.loanIssueDate,
                    borrowerFirstName = responseBody.borrowerFirstName,
                    loanId = responseBody.loanId,
                    borrowerLastName = responseBody.borrowerLastName,
                    loanPercent = responseBody.loanPercent,
                    loanPeriod = responseBody.loanPeriod,
                    borrowerPhoneNumber = responseBody.borrowerPhoneNumber,
                    loanState = responseBody.loanState
                )
                emit(ApiResult.Success(_data = loan))
            }
            else {
                val errorMsg = response.errorBody()?.string()
                response.errorBody()?.close()
                emit(ApiResult.Error(exception = errorMsg.toString()))
            }
        }
    }
}
