package com.girrafeecstud.final_loan_app_zalessky.domain.usecase

import com.girrafeecstud.final_loan_app_zalessky.data.network.ApiError
import com.girrafeecstud.final_loan_app_zalessky.data.network.ApiErrorType
import com.girrafeecstud.final_loan_app_zalessky.data.network.login.ApiResult
import com.girrafeecstud.final_loan_app_zalessky.domain.entities.LoanConditions
import com.girrafeecstud.final_loan_app_zalessky.domain.repository.LoanRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.junit.Assert.assertEquals

class GetLoanConditionsUseCaseUnitTest {

    companion object {
        private const val USER_AUTH_TOKEN = "Bearer ruhg3894h84hg"
    }

    private lateinit var getLoanConditionsUseCase: GetLoanConditionsUseCase

    private val loanRepository: LoanRepository = mock()

    @Before
    fun setUp() {
        getLoanConditionsUseCase = GetLoanConditionsUseCase(
            repository = loanRepository
        )
    }

    @Test
    fun `EXPECT success result with LoanConditions entity` () {
        runBlocking {
            val expectedResult = LoanConditions(
                percent = 6.51,
                period = 60,
                maxAmount = 20000.0
            )

            whenever(loanRepository.getLoanConditions(
                bearerToken = USER_AUTH_TOKEN
            )).thenReturn(
                flow {
                    emit(
                        ApiResult.Success(
                            _data = LoanConditions(
                                percent = 6.51,
                                period = 60,
                                maxAmount = 20000.0
                            )
                        )
                    )
                }
            )

            var actualResult: LoanConditions? = null

            getLoanConditionsUseCase(bearerToken = USER_AUTH_TOKEN).collect { result ->
                when (result) {
                    is ApiResult.Success -> {
                        actualResult = result.data as LoanConditions
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

            whenever(loanRepository.getLoanConditions(
                bearerToken = USER_AUTH_TOKEN
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

            getLoanConditionsUseCase(bearerToken = USER_AUTH_TOKEN).collect { result ->
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