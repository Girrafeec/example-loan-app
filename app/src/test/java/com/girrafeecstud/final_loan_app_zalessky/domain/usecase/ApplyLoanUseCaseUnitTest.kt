package com.girrafeecstud.final_loan_app_zalessky.domain.usecase

import com.girrafeecstud.final_loan_app_zalessky.data.network.ApiError
import com.girrafeecstud.final_loan_app_zalessky.data.network.ApiErrorType
import com.girrafeecstud.final_loan_app_zalessky.data.network.login.ApiResult
import com.girrafeecstud.final_loan_app_zalessky.domain.entities.Loan
import com.girrafeecstud.final_loan_app_zalessky.domain.entities.LoanRequest
import com.girrafeecstud.final_loan_app_zalessky.domain.entities.LoanState
import com.girrafeecstud.final_loan_app_zalessky.domain.repository.LoanRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.junit.Assert.assertEquals

class ApplyLoanUseCaseUnitTest {

    companion object {
        private const val USER_AUTH_TOKEN = "Bearer ruhg3894h84hg"
    }

    private lateinit var applyLoanUseCase: ApplyLoanUseCase

    private val loanRepository: LoanRepository = mock()

    @Before
    fun setUp() {
        applyLoanUseCase = ApplyLoanUseCase(
            repository = loanRepository
        )
    }

    @Test
    fun `EXPECT success result with loan entity` () {
        runBlocking {
            val expectedResult = Loan(
                loanAmount = 10000.0,
                loanIssueDate = "2022-07-03T10:24:17.717+00:00",
                borrowerFirstName = "string",
                loanId = 975,
                borrowerLastName = "string",
                loanPercent = 12.72,
                loanPeriod = 75,
                borrowerPhoneNumber = "string",
                loanState = LoanState.REGISTERED
            )

            val loanRequest = LoanRequest(
                loanAmount = 10000.0,
                loanPeriod = 75,
                loanPercent = 12.72,
                borrowerFirstName = "string",
                borrowerLastName = "string",
                borrowerPhoneNumber = "string"
            )

            whenever(loanRepository.applyLoan(
                bearerToken = USER_AUTH_TOKEN,
                loanRequest = loanRequest
            )).thenReturn(
                flow {
                    emit(
                        ApiResult.Success(
                            _data = Loan(
                                loanAmount = 10000.0,
                                loanIssueDate = "2022-07-03T10:24:17.717+00:00",
                                borrowerFirstName = "string",
                                loanId = 975,
                                borrowerLastName = "string",
                                loanPercent = 12.72,
                                loanPeriod = 75,
                                borrowerPhoneNumber = "string",
                                loanState = LoanState.REGISTERED
                            )
                        )
                    )
                }
            )

            var actualResult: Loan? = null

            applyLoanUseCase(
                bearerToken = USER_AUTH_TOKEN,
                loanRequest = loanRequest
            ).collect { result ->
                when (result) {
                    is ApiResult.Success -> {
                        actualResult = result.data as Loan
                    }
                }
            }

            assertEquals(expectedResult, actualResult)
        }
    }

    @Test
    fun `EXPECT error result with BAD_REQUEST_ERROR error type` () {
        runBlocking {
            val expectedResult = ApiError(
                statusCode = 400,
                errorMessage = "",
                errorType = ApiErrorType.BAD_REQUEST_ERROR
            )

            val loanRequest = LoanRequest(
                loanAmount = 10000.0,
                loanPeriod = 75,
                loanPercent = 12.72,
                borrowerFirstName = "string",
                borrowerLastName = "string",
                borrowerPhoneNumber = "string"
            )

            whenever(loanRepository.applyLoan(
                bearerToken = USER_AUTH_TOKEN,
                loanRequest = loanRequest
            )).thenReturn(
                flow {
                    emit(
                        ApiResult.Error(
                            apiError = ApiError(
                                statusCode = 400,
                                errorMessage = "",
                                errorType = ApiErrorType.BAD_REQUEST_ERROR
                            )
                        )
                    )
                }
            )

            var actualResult: ApiError? = null

            applyLoanUseCase(
                bearerToken = USER_AUTH_TOKEN,
                loanRequest = loanRequest
            ).collect { result ->
                when (result) {
                    is ApiResult.Error -> {
                        actualResult = result.data as ApiError
                    }
                }
            }

            assertEquals(expectedResult, actualResult)
        }
    }
}