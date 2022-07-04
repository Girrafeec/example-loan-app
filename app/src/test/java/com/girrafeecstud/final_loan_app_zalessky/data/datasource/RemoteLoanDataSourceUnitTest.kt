package com.girrafeecstud.final_loan_app_zalessky.data.datasource

import com.girrafeecstud.final_loan_app_zalessky.data.network.ApiError
import com.girrafeecstud.final_loan_app_zalessky.data.network.ApiErrorConverter
import com.girrafeecstud.final_loan_app_zalessky.data.network.ApiErrorType
import com.girrafeecstud.final_loan_app_zalessky.data.network.loan.LoanApiResponseConverter
import com.girrafeecstud.final_loan_app_zalessky.data.network.loan.api.LoanApi
import com.girrafeecstud.final_loan_app_zalessky.data.network.login.ApiResult
import com.girrafeecstud.final_loan_app_zalessky.domain.entities.Loan
import com.girrafeecstud.final_loan_app_zalessky.domain.entities.LoanConditions
import com.girrafeecstud.final_loan_app_zalessky.domain.entities.LoanRequest
import com.girrafeecstud.final_loan_app_zalessky.domain.entities.LoanState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.SocketPolicy
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class RemoteLoanDataSourceUnitTest {

    companion object {
        private const val USER_AUTH_TOKEN = "Bearer eyJ0eXAiOiJKV1QiLCJhbGcbmcU2HhD"
    }

    private lateinit var loanApiResponseConverter: LoanApiResponseConverter

    private lateinit var loanDataSourceImpl: RemoteLoanDataSourceImpl

    private lateinit var loanApi: LoanApi

    private lateinit var apiErrorConverter: ApiErrorConverter

    private lateinit var mockWebServer: MockWebServer

    private lateinit var okHttpClient: OkHttpClient

    @Before
    fun setUp() {
        okHttpClient = OkHttpClient.Builder().build()
        mockWebServer = MockWebServer()
        loanApi = Retrofit
            .Builder()
            .baseUrl(mockWebServer.url(""))
            .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(ScalarsConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(LoanApi::class.java)

        loanApiResponseConverter = LoanApiResponseConverter()
        apiErrorConverter = ApiErrorConverter()
        loanDataSourceImpl =
            RemoteLoanDataSourceImpl(
                loanApi = loanApi,
                loanApiResponseConverter = loanApiResponseConverter,
                apiErrorConverter = apiErrorConverter
            )
    }

    private fun enqueueSuccessResponse(fileName: String) {
        val inputStream = javaClass?.classLoader.getResourceAsStream(fileName)
        val source = inputStream.source().buffer()
        val mockResponse = MockResponse()
        mockResponse.setResponseCode(200)
        mockResponse.setBody(source.readString(Charsets.UTF_8))
        mockWebServer.enqueue(response = mockResponse)
    }

    private fun enqueueErrorResponse(fileName: String, errorCode: Int) {
        val inputStream = javaClass?.classLoader.getResourceAsStream(fileName)
        val source = inputStream.source().buffer()
        val mockResponse = MockResponse()
        mockResponse.setBody(source.readString(Charsets.UTF_8))
        mockResponse.setResponseCode(errorCode)
        print(mockResponse.toString())
        mockWebServer.enqueue(response = mockResponse)
    }

    private fun enqueueConnectionTimeoutErrorResponse() {
        val mockResponse = MockResponse()
        mockResponse.setSocketPolicy(SocketPolicy.DISCONNECT_AT_START)
        mockWebServer.enqueue(response = mockResponse)
    }

    @Test
    fun `WHEN loan conditiond success response EXPECT LoanConditions entity` () {
        runBlocking {

            enqueueSuccessResponse(fileName = "SuccessLoanConditionsResponse.json")

            val expectedResult = LoanConditions(
                percent = 6.51,
                period = 60,
                maxAmount = 20000.0
            )

            var actualResult: LoanConditions? = null

            //Send Request to the MockServer
            val response = loanDataSourceImpl.getLoanConditions(
                bearerToken = USER_AUTH_TOKEN
            ).collect { result ->
                when (result) {
                    is ApiResult.Success -> {
                        actualResult = result.data as LoanConditions
                    }
                }
            }

            //Request received by the mock server
            val request = mockWebServer.takeRequest()

            Assert.assertEquals(expectedResult, actualResult)
        }
    }

    @Test
    fun `EXPECT Loan conditions 400 error` () {
        runBlocking {

            val expectedErrorType = ApiErrorType.BAD_REQUEST_ERROR

            enqueueErrorResponse(fileName = "", errorCode = 400)

            var responseBody: ApiError? = null

            //Send Request to the MockServer
            val response = loanDataSourceImpl.getLoanConditions(
                bearerToken = USER_AUTH_TOKEN
                ).collectLatest { result ->
                    when (result) {
                        is ApiResult.Error -> {
                            responseBody = result.data as ApiError
                        }
                    }
                }

            //Request received by the mock server
            val request = mockWebServer.takeRequest()

            Assert.assertEquals(expectedErrorType, responseBody?.errorType)
        }
    }

    @Test
    fun `EXPECT Loan conditions 401 error` () {
        runBlocking {

            val expectedErrorType = ApiErrorType.UNAUTHORIZED_ERROR

            enqueueErrorResponse(fileName = "", errorCode = 401)

            var responseBody: ApiError? = null

            //Send Request to the MockServer
            val response = loanDataSourceImpl.getLoanConditions(
                bearerToken = USER_AUTH_TOKEN
                ).collectLatest { result ->
                    when (result) {
                        is ApiResult.Error -> {
                            responseBody = result.data as ApiError
                        }
                    }
                }

            //Request received by the mock server
            val request = mockWebServer.takeRequest()

            Assert.assertEquals(expectedErrorType, responseBody?.errorType)
        }
    }

    @Test
    fun `EXPECT Loan conditions 403 error` () {
        runBlocking {

            val expectedErrorType = ApiErrorType.RESOURCE_FORBIDDEN_ERROR

            enqueueErrorResponse(fileName = "", errorCode = 403)

            var responseBody: ApiError? = null

            //Send Request to the MockServer
            val response = loanDataSourceImpl.getLoanConditions(
                bearerToken = USER_AUTH_TOKEN
                ).collectLatest { result ->
                    when (result) {
                        is ApiResult.Error -> {
                            responseBody = result.data as ApiError
                        }
                    }
                }

            //Request received by the mock server
            val request = mockWebServer.takeRequest()

            Assert.assertEquals(expectedErrorType, responseBody?.errorType)
        }
    }

    @Test
    fun `EXPECT Loan conditions 404 error` () {
        runBlocking {

            val expectedErrorType = ApiErrorType.NOT_FOUND_ERROR

            enqueueErrorResponse(fileName = "", errorCode = 404)

            var responseBody: ApiError? = null

            //Send Request to the MockServer
            val response = loanDataSourceImpl.getLoanConditions(
                bearerToken = USER_AUTH_TOKEN
                ).collectLatest { result ->
                    when (result) {
                        is ApiResult.Error -> {
                            responseBody = result.data as ApiError
                        }
                    }
                }

            //Request received by the mock server
            val request = mockWebServer.takeRequest()

            Assert.assertEquals(expectedErrorType, responseBody?.errorType)
        }
    }

    @Test
    fun `EXPECT Loan conditions unknown error` () {
        runBlocking {

            val expectedErrorType = ApiErrorType.UNKNOWN_ERROR

            enqueueErrorResponse(fileName = "", errorCode = 500)

            var responseBody: ApiError? = null

            //Send Request to the MockServer
            val response = loanDataSourceImpl.getLoanConditions(
                bearerToken = USER_AUTH_TOKEN
                ).collectLatest { result ->
                    when (result) {
                        is ApiResult.Error -> {
                            responseBody = result.data as ApiError
                        }
                    }
                }

            //Request received by the mock server
            val request = mockWebServer.takeRequest()

            Assert.assertEquals(expectedErrorType, responseBody?.errorType)
        }
    }

    @Test
    fun `EXPECT Loan conditions timeout error` () {
        runBlocking {

            val expectedErrorType = ApiErrorType.TIMEOUT_EXCEEDED_ERROR

            enqueueConnectionTimeoutErrorResponse()

            var responseBody: ApiError? = null

            //Send Request to the MockServer
            val response = loanDataSourceImpl.getLoanConditions(
                bearerToken = USER_AUTH_TOKEN
                ).collectLatest { result ->
                    when (result) {
                        is ApiResult.Error -> {
                            responseBody = result.data as ApiError
                        }
                    }
                }

            //Request received by the mock server
            val request = mockWebServer.takeRequest()

            Assert.assertEquals(expectedErrorType, responseBody?.errorType)
        }
    }

    @Test
    fun `WHEN loan request success response EXPECT Loan entity` () {
        runBlocking {

            enqueueSuccessResponse(fileName = "SuccessLoanRequestResponse.json")

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

            var actualResult: Loan? = null

            //Send Request to the MockServer
            val response = loanDataSourceImpl.applyLoan(
                bearerToken = USER_AUTH_TOKEN,
                loanRequest = LoanRequest(
                    loanAmount = 10000.0,
                    loanPeriod = 75,
                    loanPercent = 12.72,
                    borrowerFirstName = "string",
                    borrowerLastName = "string",
                    borrowerPhoneNumber = "string"
                )
            ).collect { result ->
                when (result) {
                    is ApiResult.Success -> {
                        actualResult = result.data as Loan
                    }
                }
            }

            //Request received by the mock server
            val request = mockWebServer.takeRequest()

            Assert.assertEquals(expectedResult, actualResult)
        }
    }

    @Test
    fun `EXPECT Loan request 400 error` () {
        runBlocking {

            val expectedErrorType = ApiErrorType.BAD_REQUEST_ERROR

            enqueueErrorResponse(fileName = "", errorCode = 400)

            var responseBody: ApiError? = null

            //Send Request to the MockServer
            val response = loanDataSourceImpl.getLoanConditions(
                bearerToken = USER_AUTH_TOKEN
            ).collectLatest { result ->
                when (result) {
                    is ApiResult.Error -> {
                        responseBody = result.data as ApiError
                    }
                }
            }

            //Request received by the mock server
            val request = mockWebServer.takeRequest()

            Assert.assertEquals(expectedErrorType, responseBody?.errorType)
        }
    }

    @Test
    fun `EXPECT Loan request 401 error` () {
        runBlocking {

            val expectedErrorType = ApiErrorType.UNAUTHORIZED_ERROR

            enqueueErrorResponse(fileName = "", errorCode = 401)

            var responseBody: ApiError? = null

            //Send Request to the MockServer
            val response = loanDataSourceImpl.getLoanConditions(
                bearerToken = USER_AUTH_TOKEN
            ).collectLatest { result ->
                when (result) {
                    is ApiResult.Error -> {
                        responseBody = result.data as ApiError
                    }
                }
            }

            //Request received by the mock server
            val request = mockWebServer.takeRequest()

            Assert.assertEquals(expectedErrorType, responseBody?.errorType)
        }
    }

    @Test
    fun `EXPECT Loan request 403 error` () {
        runBlocking {

            val expectedErrorType = ApiErrorType.RESOURCE_FORBIDDEN_ERROR

            enqueueErrorResponse(fileName = "", errorCode = 403)

            var responseBody: ApiError? = null

            //Send Request to the MockServer
            val response = loanDataSourceImpl.getLoanConditions(
                bearerToken = USER_AUTH_TOKEN
            ).collectLatest { result ->
                when (result) {
                    is ApiResult.Error -> {
                        responseBody = result.data as ApiError
                    }
                }
            }

            //Request received by the mock server
            val request = mockWebServer.takeRequest()

            Assert.assertEquals(expectedErrorType, responseBody?.errorType)
        }
    }

    @Test
    fun `EXPECT Loan request 404 error` () {
        runBlocking {

            val expectedErrorType = ApiErrorType.NOT_FOUND_ERROR

            enqueueErrorResponse(fileName = "", errorCode = 404)

            var responseBody: ApiError? = null

            //Send Request to the MockServer
            val response = loanDataSourceImpl.getLoanConditions(
                bearerToken = USER_AUTH_TOKEN
            ).collectLatest { result ->
                when (result) {
                    is ApiResult.Error -> {
                        responseBody = result.data as ApiError
                    }
                }
            }

            //Request received by the mock server
            val request = mockWebServer.takeRequest()

            Assert.assertEquals(expectedErrorType, responseBody?.errorType)
        }
    }

    @Test
    fun `EXPECT Loan request unknown error` () {
        runBlocking {

            val expectedErrorType = ApiErrorType.UNKNOWN_ERROR

            enqueueErrorResponse(fileName = "", errorCode = 500)

            var responseBody: ApiError? = null

            //Send Request to the MockServer
            val response = loanDataSourceImpl.getLoanConditions(
                bearerToken = USER_AUTH_TOKEN
            ).collectLatest { result ->
                when (result) {
                    is ApiResult.Error -> {
                        responseBody = result.data as ApiError
                    }
                }
            }

            //Request received by the mock server
            val request = mockWebServer.takeRequest()

            Assert.assertEquals(expectedErrorType, responseBody?.errorType)
        }
    }

    @Test
    fun `EXPECT Loan request timeout error` () {
        runBlocking {

            val expectedErrorType = ApiErrorType.TIMEOUT_EXCEEDED_ERROR

            enqueueConnectionTimeoutErrorResponse()

            var responseBody: ApiError? = null

            //Send Request to the MockServer
            val response = loanDataSourceImpl.getLoanConditions(
                bearerToken = USER_AUTH_TOKEN
            ).collectLatest { result ->
                when (result) {
                    is ApiResult.Error -> {
                        responseBody = result.data as ApiError
                    }
                }
            }

            //Request received by the mock server
            val request = mockWebServer.takeRequest()

            Assert.assertEquals(expectedErrorType, responseBody?.errorType)
        }
    }

    @Test
    fun `WHEN loans list request success response EXPECT Loans List entity` () {
        runBlocking {

            enqueueSuccessResponse(fileName = "SuccessLoansListResponse.json")

            val expectedResult = listOf<Loan>(
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

            var actualResult: List<Loan>? = null

            //Send Request to the MockServer
            val response = loanDataSourceImpl.getLoansList(
                bearerToken = USER_AUTH_TOKEN
            ).collect { result ->
                when (result) {
                    is ApiResult.Success -> {
                        actualResult = result.data as List<Loan>
                    }
                }
            }

            //Request received by the mock server
            val request = mockWebServer.takeRequest()

            Assert.assertEquals(expectedResult, actualResult)
        }
    }

    @Test
    fun `EXPECT Loans list 400 error` () {
        runBlocking {

            val expectedErrorType = ApiErrorType.BAD_REQUEST_ERROR

            enqueueErrorResponse(fileName = "", errorCode = 400)

            var responseBody: ApiError? = null

            //Send Request to the MockServer
            val response = loanDataSourceImpl.getLoanConditions(
                bearerToken = USER_AUTH_TOKEN
            ).collectLatest { result ->
                when (result) {
                    is ApiResult.Error -> {
                        responseBody = result.data as ApiError
                    }
                }
            }

            //Request received by the mock server
            val request = mockWebServer.takeRequest()

            Assert.assertEquals(expectedErrorType, responseBody?.errorType)
        }
    }

    @Test
    fun `EXPECT Loans list 401 error` () {
        runBlocking {

            val expectedErrorType = ApiErrorType.UNAUTHORIZED_ERROR

            enqueueErrorResponse(fileName = "", errorCode = 401)

            var responseBody: ApiError? = null

            //Send Request to the MockServer
            val response = loanDataSourceImpl.getLoanConditions(
                bearerToken = USER_AUTH_TOKEN
            ).collectLatest { result ->
                when (result) {
                    is ApiResult.Error -> {
                        responseBody = result.data as ApiError
                    }
                }
            }

            //Request received by the mock server
            val request = mockWebServer.takeRequest()

            Assert.assertEquals(expectedErrorType, responseBody?.errorType)
        }
    }

    @Test
    fun `EXPECT Loans list 403 error` () {
        runBlocking {

            val expectedErrorType = ApiErrorType.RESOURCE_FORBIDDEN_ERROR

            enqueueErrorResponse(fileName = "", errorCode = 403)

            var responseBody: ApiError? = null

            //Send Request to the MockServer
            val response = loanDataSourceImpl.getLoanConditions(
                bearerToken = USER_AUTH_TOKEN
            ).collectLatest { result ->
                when (result) {
                    is ApiResult.Error -> {
                        responseBody = result.data as ApiError
                    }
                }
            }

            //Request received by the mock server
            val request = mockWebServer.takeRequest()

            Assert.assertEquals(expectedErrorType, responseBody?.errorType)
        }
    }

    @Test
    fun `EXPECT Loans list 404 error` () {
        runBlocking {

            val expectedErrorType = ApiErrorType.NOT_FOUND_ERROR

            enqueueErrorResponse(fileName = "", errorCode = 404)

            var responseBody: ApiError? = null

            //Send Request to the MockServer
            val response = loanDataSourceImpl.getLoanConditions(
                bearerToken = USER_AUTH_TOKEN
            ).collectLatest { result ->
                when (result) {
                    is ApiResult.Error -> {
                        responseBody = result.data as ApiError
                    }
                }
            }

            //Request received by the mock server
            val request = mockWebServer.takeRequest()

            Assert.assertEquals(expectedErrorType, responseBody?.errorType)
        }
    }

    @Test
    fun `EXPECT Loans list unknown error` () {
        runBlocking {

            val expectedErrorType = ApiErrorType.UNKNOWN_ERROR

            enqueueErrorResponse(fileName = "", errorCode = 500)

            var responseBody: ApiError? = null

            //Send Request to the MockServer
            val response = loanDataSourceImpl.getLoanConditions(
                bearerToken = USER_AUTH_TOKEN
            ).collectLatest { result ->
                when (result) {
                    is ApiResult.Error -> {
                        responseBody = result.data as ApiError
                    }
                }
            }

            //Request received by the mock server
            val request = mockWebServer.takeRequest()

            Assert.assertEquals(expectedErrorType, responseBody?.errorType)
        }
    }

    @Test
    fun `EXPECT Loans list timeout error` () {
        runBlocking {

            val expectedErrorType = ApiErrorType.TIMEOUT_EXCEEDED_ERROR

            enqueueConnectionTimeoutErrorResponse()

            var responseBody: ApiError? = null

            //Send Request to the MockServer
            val response = loanDataSourceImpl.getLoanConditions(
                bearerToken = USER_AUTH_TOKEN
            ).collectLatest { result ->
                when (result) {
                    is ApiResult.Error -> {
                        responseBody = result.data as ApiError
                    }
                }
            }

            //Request received by the mock server
            val request = mockWebServer.takeRequest()

            Assert.assertEquals(expectedErrorType, responseBody?.errorType)
        }
    }

    @Test
    fun `WHEN loan request by id success response EXPECT Loan entity` () {
        runBlocking {

            enqueueSuccessResponse(fileName = "SussessLoanItemResponse.json")

            val expectedResult = Loan(
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

            var actualResult: Loan? = null

            //Send Request to the MockServer
            val response = loanDataSourceImpl.getLoanById(
                bearerToken = USER_AUTH_TOKEN,
                loanId = 986
            ).collect { result ->
                when (result) {
                    is ApiResult.Success -> {
                        actualResult = result.data as Loan
                    }
                }
            }

            //Request received by the mock server
            val request = mockWebServer.takeRequest()

            Assert.assertEquals(expectedResult, actualResult)
        }
    }

    @Test
    fun `EXPECT Loan item error` () {
        runBlocking {

            val expectedErrorType = ApiErrorType.BAD_REQUEST_ERROR

            enqueueErrorResponse(fileName = "", errorCode = 400)

            var responseBody: ApiError? = null

            //Send Request to the MockServer
            val response = loanDataSourceImpl.getLoanConditions(
                bearerToken = USER_AUTH_TOKEN
            ).collectLatest { result ->
                when (result) {
                    is ApiResult.Error -> {
                        responseBody = result.data as ApiError
                    }
                }
            }

            //Request received by the mock server
            val request = mockWebServer.takeRequest()

            Assert.assertEquals(expectedErrorType, responseBody?.errorType)
        }
    }

    @Test
    fun `EXPECT Loan item 401 error` () {
        runBlocking {

            val expectedErrorType = ApiErrorType.UNAUTHORIZED_ERROR

            enqueueErrorResponse(fileName = "", errorCode = 401)

            var responseBody: ApiError? = null

            //Send Request to the MockServer
            val response = loanDataSourceImpl.getLoanConditions(
                bearerToken = USER_AUTH_TOKEN
            ).collectLatest { result ->
                when (result) {
                    is ApiResult.Error -> {
                        responseBody = result.data as ApiError
                    }
                }
            }

            //Request received by the mock server
            val request = mockWebServer.takeRequest()

            Assert.assertEquals(expectedErrorType, responseBody?.errorType)
        }
    }

    @Test
    fun `EXPECT Loan item 403 error` () {
        runBlocking {

            val expectedErrorType = ApiErrorType.RESOURCE_FORBIDDEN_ERROR

            enqueueErrorResponse(fileName = "", errorCode = 403)

            var responseBody: ApiError? = null

            //Send Request to the MockServer
            val response = loanDataSourceImpl.getLoanConditions(
                bearerToken = USER_AUTH_TOKEN
            ).collectLatest { result ->
                when (result) {
                    is ApiResult.Error -> {
                        responseBody = result.data as ApiError
                    }
                }
            }

            //Request received by the mock server
            val request = mockWebServer.takeRequest()

            Assert.assertEquals(expectedErrorType, responseBody?.errorType)
        }
    }

    @Test
    fun `EXPECT Loan item 404 error` () {
        runBlocking {

            val expectedErrorType = ApiErrorType.NOT_FOUND_ERROR

            enqueueErrorResponse(fileName = "", errorCode = 404)

            var responseBody: ApiError? = null

            //Send Request to the MockServer
            val response = loanDataSourceImpl.getLoanConditions(
                bearerToken = USER_AUTH_TOKEN
            ).collectLatest { result ->
                when (result) {
                    is ApiResult.Error -> {
                        responseBody = result.data as ApiError
                    }
                }
            }

            //Request received by the mock server
            val request = mockWebServer.takeRequest()

            Assert.assertEquals(expectedErrorType, responseBody?.errorType)
        }
    }

    @Test
    fun `EXPECT Loan item unknown error` () {
        runBlocking {

            val expectedErrorType = ApiErrorType.UNKNOWN_ERROR

            enqueueErrorResponse(fileName = "", errorCode = 500)

            var responseBody: ApiError? = null

            //Send Request to the MockServer
            val response = loanDataSourceImpl.getLoanConditions(
                bearerToken = USER_AUTH_TOKEN
            ).collectLatest { result ->
                when (result) {
                    is ApiResult.Error -> {
                        responseBody = result.data as ApiError
                    }
                }
            }

            //Request received by the mock server
            val request = mockWebServer.takeRequest()

            Assert.assertEquals(expectedErrorType, responseBody?.errorType)
        }
    }

    @Test
    fun `EXPECT Loan item timeout error` () {
        runBlocking {

            val expectedErrorType = ApiErrorType.TIMEOUT_EXCEEDED_ERROR

            enqueueConnectionTimeoutErrorResponse()

            var responseBody: ApiError? = null

            //Send Request to the MockServer
            val response = loanDataSourceImpl.getLoanConditions(
                bearerToken = USER_AUTH_TOKEN
            ).collectLatest { result ->
                when (result) {
                    is ApiResult.Error -> {
                        responseBody = result.data as ApiError
                    }
                }
            }

            //Request received by the mock server
            val request = mockWebServer.takeRequest()

            Assert.assertEquals(expectedErrorType, responseBody?.errorType)
        }
    }

    @After
    fun shutdownMockWebServer() {
        mockWebServer.shutdown()
    }

}