package com.girrafeecstud.final_loan_app_zalessky.data.repository

import com.girrafeecstud.final_loan_app_zalessky.data.repository.LoanRepositoryImpl
import com.girrafeecstud.final_loan_app_zalessky.data.datasource.RemoteLoanDataSourceImpl
import com.girrafeecstud.final_loan_app_zalessky.data.datasource.LocalLoanDataSourceImpl
import com.girrafeecstud.final_loan_app_zalessky.data.network.ApiError
import com.girrafeecstud.final_loan_app_zalessky.data.network.ApiErrorType
import com.girrafeecstud.final_loan_app_zalessky.data.network.login.ApiResult
import com.girrafeecstud.final_loan_app_zalessky.domain.entities.Loan
import com.girrafeecstud.final_loan_app_zalessky.domain.entities.LoanConditions
import com.girrafeecstud.final_loan_app_zalessky.domain.entities.LoanRequest
import com.girrafeecstud.final_loan_app_zalessky.domain.entities.LoanState
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.junit.Assert.assertEquals

class LoanRepositoryUnitTest {

    companion object {
        private const val USER_AUTH_TOKEN = "Bearer eyJ0eXAiOiJKV1QiLCJhbGcbmcU2HhD"
        private const val BAD_REQUEST_MESSAGE = "BAD REQUEST"
    }

    private lateinit var loanRepositoryImpl: LoanRepositoryImpl

    private val remoteLoanDataSourceImpl: RemoteLoanDataSourceImpl = mock()

    private val localLoanDataSourceImpl: LocalLoanDataSourceImpl = mock()

    @Before
    fun setUp () {
        loanRepositoryImpl = LoanRepositoryImpl(
            remoteDataSource = remoteLoanDataSourceImpl,
            localDataSource = localLoanDataSourceImpl
        )
    }

    @Test
    fun `EXPECT LoanConditions entity` () {
        runBlocking {

            val expectedResult = LoanConditions(
                percent = 6.51,
                period = 60,
                maxAmount = 20000.0
            )

            whenever(remoteLoanDataSourceImpl.getLoanConditions(bearerToken = USER_AUTH_TOKEN))
                .thenReturn(
                    flow {
                        emit(
                            ApiResult.Success(
                                _data = LoanConditions (
                                    percent = 6.51,
                                    period = 60,
                                    maxAmount = 20000.0
                                )
                            )
                        )
                    }
                )

            var actualResult: LoanConditions? = null

            loanRepositoryImpl.getLoanConditions(bearerToken = USER_AUTH_TOKEN)
                .collect { result ->
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
    fun `EXPECT 400 error when request for loan conditions` () {
        runBlocking {

            val expectedResult = ApiErrorType.BAD_REQUEST_ERROR

            whenever(remoteLoanDataSourceImpl.getLoanConditions(bearerToken = USER_AUTH_TOKEN))
                .thenReturn(
                    flow {
                        emit(
                            ApiResult.Error(
                                apiError = ApiError(
                                    errorType = ApiErrorType.BAD_REQUEST_ERROR,
                                    statusCode = 400,
                                    errorMessage = BAD_REQUEST_MESSAGE
                                )
                            )
                        )
                    }
                )

            var actualResult: ApiError? = null

            loanRepositoryImpl.getLoanConditions(bearerToken = USER_AUTH_TOKEN)
                .collect { result ->
                    when (result) {
                        is ApiResult.Error -> {
                            actualResult = result.data as ApiError
                        }
                    }
                }
            assertEquals(expectedResult, actualResult?.errorType)
        }
    }

    @Test
    fun `WHEN request loan EXPECT Loan entity` () {
        runBlocking {

            val loanRequest = LoanRequest(
                loanAmount = 10000.0,
                loanPeriod = 75,
                loanPercent = 12.72,
                borrowerFirstName = "string",
                borrowerLastName = "string",
                borrowerPhoneNumber = "string"
            )

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

            whenever(
                remoteLoanDataSourceImpl.applyLoan(
                    bearerToken = USER_AUTH_TOKEN,
                    loanRequest = loanRequest
                )
            ).thenReturn(
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

            loanRepositoryImpl.applyLoan(
                bearerToken = USER_AUTH_TOKEN,
                loanRequest = loanRequest
            )
                .collect { result ->
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
    fun `WHEN request loan EXPECT 400 error` () {
        runBlocking {

            val loanRequest = LoanRequest(
                loanAmount = 10000.0,
                loanPeriod = 75,
                loanPercent = 12.72,
                borrowerFirstName = "string",
                borrowerLastName = "string",
                borrowerPhoneNumber = "string"
            )

            val expectedResult = ApiErrorType.BAD_REQUEST_ERROR

            whenever(
                remoteLoanDataSourceImpl.applyLoan(
                    bearerToken = USER_AUTH_TOKEN,
                    loanRequest = loanRequest
                )
            ).thenReturn(
                flow {
                    emit(
                        ApiResult.Error(
                            apiError = ApiError(
                                errorType = ApiErrorType.BAD_REQUEST_ERROR,
                                statusCode = 400,
                                errorMessage = BAD_REQUEST_MESSAGE
                            )
                        )
                    )
                }
            )

            var actualResult: ApiError? = null

            loanRepositoryImpl.applyLoan(
                bearerToken = USER_AUTH_TOKEN,
                loanRequest = loanRequest
            )
                .collect { result ->
                    when (result) {
                        is ApiResult.Error -> {
                            actualResult = result.data as ApiError
                        }
                    }
                }
            assertEquals(expectedResult, actualResult?.errorType)
        }
    }

    @Test
    fun `EXPECT loans list` () {
        runBlocking {
            val localDataSourceExpectedResult = listOf<Loan>(
                Loan(
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

            val remoteDataSourceExpectedResult = listOf<Loan>(
                Loan(
                    loanAmount = 10000.0,
                    loanIssueDate = "2022-07-03T10:24:17.717+00:00",
                    borrowerFirstName = "string",
                    loanId = 975,
                    borrowerLastName = "string",
                    loanPercent = 12.72,
                    loanPeriod = 75,
                    borrowerPhoneNumber = "string",
                    loanState = LoanState.REGISTERED
                ),
                Loan(
                    loanAmount = 15000.0,
                    loanIssueDate = "2022-06-15T10:24:17.717+00:00",
                    borrowerFirstName = "string",
                    loanId = 986,
                    borrowerLastName = "string",
                    loanPercent = 16.72,
                    loanPeriod = 75,
                    borrowerPhoneNumber = "string",
                    loanState = LoanState.REGISTERED
                )
            )

            whenever(localLoanDataSourceImpl.getLoansList()).thenReturn(
                flow {
                    emit(
                        listOf(
                            Loan(
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

            whenever(remoteLoanDataSourceImpl.getLoansList(bearerToken = USER_AUTH_TOKEN)).thenReturn(
                flow {
                    emit(
                        ApiResult.Success(
                            _data = listOf(
                                Loan(
                                    loanAmount = 10000.0,
                                    loanIssueDate = "2022-07-03T10:24:17.717+00:00",
                                    borrowerFirstName = "string",
                                    loanId = 975,
                                    borrowerLastName = "string",
                                    loanPercent = 12.72,
                                    loanPeriod = 75,
                                    borrowerPhoneNumber = "string",
                                    loanState = LoanState.REGISTERED
                                ),
                                Loan(
                                    loanAmount = 15000.0,
                                    loanIssueDate = "2022-06-15T10:24:17.717+00:00",
                                    borrowerFirstName = "string",
                                    loanId = 986,
                                    borrowerLastName = "string",
                                    loanPercent = 16.72,
                                    loanPeriod = 75,
                                    borrowerPhoneNumber = "string",
                                    loanState = LoanState.REGISTERED
                                )
                            )
                        )
                    )
                }
            )

            var actualResult: List<Loan>? = null

            loanRepositoryImpl.getLoansList(
                bearerToken = USER_AUTH_TOKEN
            ).collectIndexed { index, result ->
                if (index < 1)
                    when (result) {
                        is ApiResult.Success -> {
                            actualResult = result.data as List<Loan>
                            assertEquals(localDataSourceExpectedResult, actualResult)
                        }
                    }
                else
                    when (result) {
                        is ApiResult.Success -> {
                            actualResult = result.data as List<Loan>
                            assertEquals(remoteDataSourceExpectedResult, actualResult)
                        }
                    }
            }
        }
    }

    @Test
    fun `WHEN ask for loans list EXPECT error 400` () {
        runBlocking {
            val localDataSourceExpectedResult = listOf<Loan>(
                Loan(
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

            val remoteDataSourceExpectedResult = ApiErrorType.BAD_REQUEST_ERROR

            whenever(localLoanDataSourceImpl.getLoansList()).thenReturn(
                flow {
                    emit(
                        listOf(
                            Loan(
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

            whenever(remoteLoanDataSourceImpl.getLoansList(bearerToken = USER_AUTH_TOKEN)).thenReturn(
                flow {
                    emit(
                        ApiResult.Error(
                            apiError = ApiError(
                                errorType = ApiErrorType.BAD_REQUEST_ERROR,
                                statusCode = 400,
                                errorMessage = BAD_REQUEST_MESSAGE
                            )
                        )
                    )
                }
            )

            var actualResult: Any? = null

            loanRepositoryImpl.getLoansList(
                bearerToken = USER_AUTH_TOKEN
            ).collectIndexed { index, result ->
                if (index < 1)
                    when (result) {
                        is ApiResult.Success -> {
                            actualResult = result.data as List<Loan>
                            assertEquals(localDataSourceExpectedResult, actualResult)
                        }
                    }
                else
                    when (result) {
                        is ApiResult.Error -> {
                            actualResult = result.data as ApiError
                            assertEquals(remoteDataSourceExpectedResult, (actualResult as ApiError).errorType)
                        }
                    }
            }
        }
    }

    @Test
    fun `EXPECT loan item` () {
        runBlocking {
            val localDataSourceExpectedResult = Loan(
                loanAmount = 15000.0,
                loanIssueDate = "2022-06-15T10:24:17.717+00:00",
                borrowerFirstName = "string",
                loanId = 986,
                borrowerLastName = "string",
                loanPercent = 16.72,
                loanPeriod = 75,
                borrowerPhoneNumber = "string",
                loanState = LoanState.REGISTERED
            )

            val remoteDataSourceExpectedResult = Loan(
                loanAmount = 15000.0,
                loanIssueDate = "2022-06-15T10:24:17.717+00:00",
                borrowerFirstName = "string",
                loanId = 986,
                borrowerLastName = "string",
                loanPercent = 16.72,
                loanPeriod = 75,
                borrowerPhoneNumber = "string",
                loanState = LoanState.REGISTERED
            )

            whenever(localLoanDataSourceImpl.getLoanById(loanId = 986)).thenReturn(
                flow {
                    emit(
                        Loan(
                            loanAmount = 15000.0,
                            loanIssueDate = "2022-06-15T10:24:17.717+00:00",
                            borrowerFirstName = "string",
                            loanId = 986,
                            borrowerLastName = "string",
                            loanPercent = 16.72,
                            loanPeriod = 75,
                            borrowerPhoneNumber = "string",
                            loanState = LoanState.REGISTERED
                        )
                    )
                }
            )

            whenever(remoteLoanDataSourceImpl.getLoanById(
                bearerToken = USER_AUTH_TOKEN,
                loanId = 986
            )).thenReturn(
                flow {
                    emit(
                        ApiResult.Success(
                            _data =
                            Loan(
                                loanAmount = 15000.0,
                                loanIssueDate = "2022-06-15T10:24:17.717+00:00",
                                borrowerFirstName = "string",
                                loanId = 986,
                                borrowerLastName = "string",
                                loanPercent = 16.72,
                                loanPeriod = 75,
                                borrowerPhoneNumber = "string",
                                loanState = LoanState.REGISTERED
                                )
                            )
                        )
                }
            )

            var actualResult: Loan? = null

            loanRepositoryImpl.getLoanById(
                bearerToken = USER_AUTH_TOKEN,
                loanId = 986
            ).collectIndexed { index, result ->
                if (index < 1)
                    when (result) {
                        is ApiResult.Success -> {
                            actualResult = result.data as Loan
                            assertEquals(localDataSourceExpectedResult, actualResult)
                        }
                    }
                else
                    when (result) {
                        is ApiResult.Success -> {
                            actualResult = result.data as Loan
                            assertEquals(remoteDataSourceExpectedResult, actualResult)
                        }
                    }
            }
        }
    }

    @Test
    fun `WHEN ask for loan item expect error 400` () {
        runBlocking {
            val localDataSourceExpectedResult = Loan(
                loanAmount = 15000.0,
                loanIssueDate = "2022-06-15T10:24:17.717+00:00",
                borrowerFirstName = "string",
                loanId = 986,
                borrowerLastName = "string",
                loanPercent = 16.72,
                loanPeriod = 75,
                borrowerPhoneNumber = "string",
                loanState = LoanState.REGISTERED
            )

            val remoteDataSourceExpectedResult = ApiErrorType.BAD_REQUEST_ERROR

            whenever(localLoanDataSourceImpl.getLoanById(loanId = 986)).thenReturn(
                flow {
                    emit(
                        Loan(
                            loanAmount = 15000.0,
                            loanIssueDate = "2022-06-15T10:24:17.717+00:00",
                            borrowerFirstName = "string",
                            loanId = 986,
                            borrowerLastName = "string",
                            loanPercent = 16.72,
                            loanPeriod = 75,
                            borrowerPhoneNumber = "string",
                            loanState = LoanState.REGISTERED
                        )
                    )
                }
            )

            whenever(remoteLoanDataSourceImpl.getLoanById(
                bearerToken = USER_AUTH_TOKEN,
                loanId = 986
            )).thenReturn(
                flow {
                    emit(
                        ApiResult.Error(
                            apiError = ApiError(
                                errorType = ApiErrorType.BAD_REQUEST_ERROR,
                                statusCode = 400,
                                errorMessage = BAD_REQUEST_MESSAGE
                            )
                        )
                    )
                }
            )

            var actualResult: Any? = null

            loanRepositoryImpl.getLoanById(
                bearerToken = USER_AUTH_TOKEN,
                loanId = 986
            ).collectIndexed { index, result ->
                if (index < 1)
                    when (result) {
                        is ApiResult.Success -> {
                            actualResult = result.data as Loan
                            assertEquals(localDataSourceExpectedResult, actualResult)
                        }
                    }
                else
                    when (result) {
                        is ApiResult.Error -> {
                            actualResult = result.data as ApiError
                            assertEquals(remoteDataSourceExpectedResult, (actualResult as ApiError).errorType)
                        }
                    }
            }
        }
    }

}