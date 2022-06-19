package com.girrafeecstud.final_loan_app_zalessky.data.datasource

import com.girrafeecstud.final_loan_app_zalessky.data.network.ApiErrorConverter
import com.girrafeecstud.final_loan_app_zalessky.data.network.loan.LoanApiResponseConverter
import com.girrafeecstud.final_loan_app_zalessky.data.network.loan.api.LoanApi
import com.girrafeecstud.final_loan_app_zalessky.data.network.loan.dto.LoanApiRequest
import com.girrafeecstud.final_loan_app_zalessky.data.network.login.ApiResult
import com.girrafeecstud.final_loan_app_zalessky.domain.entities.Loan
import com.girrafeecstud.final_loan_app_zalessky.domain.entities.LoanConditions
import com.girrafeecstud.final_loan_app_zalessky.domain.entities.LoanRequest
import com.girrafeecstud.final_loan_app_zalessky.utils.NoNetworkException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import java.net.SocketTimeoutException
import javax.inject.Inject

class RemoteLoanDataSourceImpl @Inject constructor(
    private val loanApi: LoanApi,
    private val loanApiResponseConverter: LoanApiResponseConverter,
    private val apiErrorConverter: ApiErrorConverter
) {
    suspend fun getLoanConditions(bearerToken: String?): Flow<ApiResult<Any>> {
        return flow {

            try {

                val response = loanApi.getLoanConditions(authorizationToken = bearerToken)
                val responseBody = response.body()

                if (response.isSuccessful && responseBody != null) {
                    val loanConditions = LoanConditions(
                        maxAmount = responseBody.maxLoanAmount,
                        percent = responseBody.loanPercent,
                        period = responseBody.loanPeriod
                    )
                    emit(ApiResult.Success(_data = loanConditions))
                } else {
                    val error = apiErrorConverter.convertHttpError(
                        errorStatusCode = response.code(),
                        errorMessage = response.message()
                    )
                    emit(ApiResult.Error(apiError = error))
                }
            }catch (exception: NoNetworkException) {
                val error = apiErrorConverter.convertConnectionError()
                emit(ApiResult.Error(apiError = error))
            }catch (exception: SocketTimeoutException) {
                val error = apiErrorConverter.convertTimeoutError()
                emit(ApiResult.Error(apiError = error))
            }catch (exception: IOException) {
                val error = apiErrorConverter.convertTimeoutError()
                emit(ApiResult.Error(apiError = error))
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

            try {
                val response = loanApi.applyLoan(
                    authorizationToken = bearerToken,
                    loanApiRequest = loanApiRequest
                )
                val responseBody = response.body()

                if (response.isSuccessful && responseBody != null) {
                    val loan =
                        loanApiResponseConverter.getLoanFromLoanResponse(loanResponse = responseBody)
                    emit(ApiResult.Success(_data = loan))
                } else {
                    val error = apiErrorConverter.convertHttpError(
                        errorStatusCode = response.code(),
                        errorMessage = response.message()
                    )
                    emit(ApiResult.Error(apiError = error))
                }
            }catch (exception: NoNetworkException) {
                val error = apiErrorConverter.convertConnectionError()
                emit(ApiResult.Error(apiError = error))
            }catch (exception: SocketTimeoutException) {
                val error = apiErrorConverter.convertTimeoutError()
                emit(ApiResult.Error(apiError = error))
            }catch (exception: IOException) {
                val error = apiErrorConverter.convertTimeoutError()
                emit(ApiResult.Error(apiError = error))
            }
        }
    }

    suspend fun getLoansList(bearerToken: String?) : Flow<ApiResult<Any>> {
        return flow {

            try {
                val response = loanApi.getLoansList(authorizationToken = bearerToken)
                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null) {
                    var loansList = listOf<Loan>()
                    for (loan in responseBody)
                        loansList += loanApiResponseConverter.getLoanFromLoanResponse(loanResponse = loan)

                    emit(ApiResult.Success(_data = loansList))
                } else {
                    val error = apiErrorConverter.convertHttpError(
                        errorStatusCode = response.code(),
                        errorMessage = response.message()
                    )
                    emit(ApiResult.Error(apiError = error))
                }
            }catch (exception: NoNetworkException) {
                val error = apiErrorConverter.convertConnectionError()
                emit(ApiResult.Error(apiError = error))
            }catch (exception: SocketTimeoutException) {
                val error = apiErrorConverter.convertTimeoutError()
                emit(ApiResult.Error(apiError = error))
            }catch (exception: IOException) {
                val error = apiErrorConverter.convertTimeoutError()
                emit(ApiResult.Error(apiError = error))
            }

        }
    }

    suspend fun getLoanById(bearerToken: String?, loanId: Long): Flow<ApiResult<Any>> {

        val stringLoanId = loanId.toString()

        return flow {

            try {

                val response = loanApi.getLoanById(
                    authorizationToken = bearerToken,
                    loanId = stringLoanId
                )
                val responseBody = response.body()

                if (response.isSuccessful && responseBody != null) {
                    val loan =
                        loanApiResponseConverter.getLoanFromLoanResponse(loanResponse = responseBody)
                    emit(ApiResult.Success(_data = loan))
                } else {
                    val error = apiErrorConverter.convertHttpError(
                        errorStatusCode = response.code(),
                        errorMessage = response.message()
                    )
                    emit(ApiResult.Error(apiError = error))
                }
            }catch (exception: NoNetworkException) {
                val error = apiErrorConverter.convertConnectionError()
                emit(ApiResult.Error(apiError = error))
            }catch (exception: SocketTimeoutException) {
                val error = apiErrorConverter.convertTimeoutError()
                emit(ApiResult.Error(apiError = error))
            }catch (exception: IOException) {
                val error = apiErrorConverter.convertTimeoutError()
                emit(ApiResult.Error(apiError = error))
            }
        }
    }
}
