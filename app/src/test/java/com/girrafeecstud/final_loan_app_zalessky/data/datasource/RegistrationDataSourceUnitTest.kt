package com.girrafeecstud.final_loan_app_zalessky.data.datasource

import com.girrafeecstud.final_loan_app_zalessky.data.network.ApiError
import com.girrafeecstud.final_loan_app_zalessky.data.network.ApiErrorConverter
import com.girrafeecstud.final_loan_app_zalessky.data.network.ApiErrorType
import com.girrafeecstud.final_loan_app_zalessky.data.network.login.ApiResult
import com.girrafeecstud.final_loan_app_zalessky.data.network.registration.api.RegistrationApi
import com.girrafeecstud.final_loan_app_zalessky.data.network.registration.RegistrationApiResponseConverter
import com.girrafeecstud.final_loan_app_zalessky.domain.entities.Auth
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.SocketPolicy
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class RegistrationDataSourceUnitTest {

    companion object {
        private const val USER_NAME = "userName"
        private const val USER_PASSWORD = "password"
        private const val USER_ROLE = "USER"
    }

    private lateinit var registrationDataSourceImpl: RegistrationDataSourceImpl

    private lateinit var registrationApi: RegistrationApi

    private lateinit var registrationApiResponseConverter: RegistrationApiResponseConverter

    private lateinit var apiErrorConverter: ApiErrorConverter

    private lateinit var mockWebServer: MockWebServer

    private lateinit var okHttpClient: OkHttpClient

    @Before
    fun setUp() {
        okHttpClient = OkHttpClient.Builder().build()
        mockWebServer = MockWebServer()
        registrationApi = Retrofit
            .Builder()
            .baseUrl(mockWebServer.url(""))
            .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(ScalarsConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(RegistrationApi::class.java)

        registrationApiResponseConverter = RegistrationApiResponseConverter()
        apiErrorConverter = ApiErrorConverter()
        registrationDataSourceImpl =
            RegistrationDataSourceImpl(
                registrationApi = registrationApi,
                registrationApiResponseConverter = registrationApiResponseConverter,
                apiErrorConverter = apiErrorConverter
            )
    }

    private fun enqueueSuccessResponse(fileName: String) {
        val inputStream = javaClass?.classLoader.getResourceAsStream(fileName)
        val source = inputStream.source().buffer()
        val mockResponse = MockResponse()
        mockResponse.setBody(source.readString(Charsets.UTF_8))
        mockResponse.setResponseCode(200)
        mockWebServer.enqueue(response = mockResponse)
    }

    private fun enqueueErrorResponse(fileName: String, errorCode: Int) {
        val inputStream = javaClass?.classLoader.getResourceAsStream(fileName)
        val source = inputStream.source().buffer()
        val mockResponse = MockResponse()
        mockResponse.setBody(source.readString(Charsets.UTF_8))
        mockResponse.setResponseCode(errorCode)
        mockWebServer.enqueue(response = mockResponse)
    }

    private fun enqueueConnectionTimeoutErrorResponse() {
        val mockResponse = MockResponse()
        mockResponse.setSocketPolicy(SocketPolicy.DISCONNECT_AT_START)
        mockWebServer.enqueue(response = mockResponse)
    }

    @Test
    fun `WHEN put legal and unique username and password EXPECT success result with auth entity`()  =
        runBlocking {

            enqueueSuccessResponse(fileName = "SuccessRegistrationResponse.json")

            val expectedResult = Auth(userName = USER_NAME, userRole = USER_ROLE)

            var actualResult: Auth? = null

            //Send Request to the MockServer
            val response =
                registrationDataSourceImpl.registration(
                    userName = USER_NAME,
                    userPassword = USER_PASSWORD
                ).collectLatest { result ->
                    when (result) {
                        is ApiResult.Success -> {
                            actualResult = result.data as Auth
                        }
                    }
                }

            //Request received by the mock server
            val request = mockWebServer.takeRequest()

            assertEquals(expectedResult, actualResult)
        }

    @Test
    fun `WHEN get error 400 EXPECT BAD_REQUEST error`() {
        runBlocking {

            val expectedErrorType = ApiErrorType.BAD_REQUEST_ERROR

            enqueueErrorResponse(fileName = "BadRequestRegistrationResponse", errorCode = 400)

            var responseBody: ApiError? = null

            //Send Request to the MockServer
            val response =
                registrationDataSourceImpl.registration(
                    userName = USER_NAME,
                    userPassword = USER_PASSWORD
                ).collectLatest { result ->
                    when (result) {
                        is ApiResult.Error -> {
                            responseBody = result.data as ApiError
                        }
                    }
                }

            //Request received by the mock server
            val request = mockWebServer.takeRequest()

            assertEquals(expectedErrorType, responseBody?.errorType)
        }
    }

    @Test
    fun `WHEN get error 401 EXPECT UNAUTHORIZED_ERROR error`() {
        runBlocking {

            val expectedErrorType = ApiErrorType.UNAUTHORIZED_ERROR

            enqueueErrorResponse(fileName = "", errorCode = 401)

            var responseBody: ApiError? = null

            //Send Request to the MockServer
            val response =
                registrationDataSourceImpl.registration(
                    userName = USER_NAME,
                    userPassword = USER_PASSWORD
                ).collectLatest { result ->
                    when (result) {
                        is ApiResult.Error -> {
                            responseBody = result.data as ApiError
                        }
                    }
                }

            //Request received by the mock server
            val request = mockWebServer.takeRequest()

            assertEquals(expectedErrorType, responseBody?.errorType)
        }
    }

    @Test
    fun `WHEN get error 403 EXPECT RESOURCE_FORBIDDEN_ERROR error`() {
        runBlocking {

            val expectedErrorType = ApiErrorType.RESOURCE_FORBIDDEN_ERROR

            enqueueErrorResponse(fileName = "", errorCode = 403)

            var responseBody: ApiError? = null

            //Send Request to the MockServer
            val response =
                registrationDataSourceImpl.registration(
                    userName = USER_NAME,
                    userPassword = USER_PASSWORD
                ).collectLatest { result ->
                    when (result) {
                        is ApiResult.Error -> {
                            responseBody = result.data as ApiError
                        }
                    }
                }

            //Request received by the mock server
            val request = mockWebServer.takeRequest()

            assertEquals(expectedErrorType, responseBody?.errorType)
        }
    }

    @Test
    fun `WHEN get error 404 EXPECT NOT_FOUND_ERROR error`() {
        runBlocking {

            val expectedErrorType = ApiErrorType.NOT_FOUND_ERROR

            enqueueErrorResponse(fileName = "", errorCode = 404)

            var responseBody: ApiError? = null

            //Send Request to the MockServer
            val response =
                registrationDataSourceImpl.registration(
                    userName = USER_NAME,
                    userPassword = USER_PASSWORD
                ).collectLatest { result ->
                    when (result) {
                        is ApiResult.Error -> {
                            responseBody = result.data as ApiError
                        }
                    }
                }

            //Request received by the mock server
            val request = mockWebServer.takeRequest()

            assertEquals(expectedErrorType, responseBody?.errorType)
        }
    }

    @Test
    fun `WHEN get unknown error EXPECT UNKNOWN_ERROR error`() {
        runBlocking {

            val expectedErrorType = ApiErrorType.UNKNOWN_ERROR

            enqueueErrorResponse(fileName = "", errorCode = 500)

            var responseBody: ApiError? = null

            //Send Request to the MockServer
            val response =
                registrationDataSourceImpl.registration(
                    userName = USER_NAME,
                    userPassword = USER_PASSWORD
                ).collectLatest { result ->
                    when (result) {
                        is ApiResult.Error -> {
                            responseBody = result.data as ApiError
                        }
                    }
                }

            //Request received by the mock server
            val request = mockWebServer.takeRequest()

            assertEquals(expectedErrorType, responseBody?.errorType)
        }
    }

    @Test
    fun `WHEN timeout exceed EXPECT TIMEOUT_EXCEEDED_ERROR` () {

        runBlocking {

            val expectedErrorType = ApiErrorType.TIMEOUT_EXCEEDED_ERROR

            enqueueConnectionTimeoutErrorResponse()

            var responseBody: ApiError? = null

            //Send Request to the MockServer
            val response =
                registrationDataSourceImpl.registration(
                    userName = USER_NAME,
                    userPassword = USER_PASSWORD
                ).collectLatest { result ->
                    when (result) {
                        is ApiResult.Error -> {
                            responseBody = result.data as ApiError
                        }
                    }
                }

            //Request received by the mock server
            val request = mockWebServer.takeRequest()

            assertEquals(expectedErrorType, responseBody?.errorType)
        }
    }

    @After
    fun shutdownMockWebServer() {
        mockWebServer.shutdown()
    }

}