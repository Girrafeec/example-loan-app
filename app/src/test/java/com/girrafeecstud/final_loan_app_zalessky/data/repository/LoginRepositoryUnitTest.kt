package com.girrafeecstud.final_loan_app_zalessky.data.repository

import com.girrafeecstud.final_loan_app_zalessky.data.datasource.LoginDataSourceImpl
import com.girrafeecstud.final_loan_app_zalessky.data.network.ApiError
import com.girrafeecstud.final_loan_app_zalessky.data.network.ApiErrorType
import com.girrafeecstud.final_loan_app_zalessky.data.network.login.ApiResult
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.junit.Assert.assertEquals

class LoginRepositoryUnitTest {

    companion object {
        private const val USER_NAME = "userName"
        private const val USER_PASSWORD = "password"
        private const val USER_AUTH_TOKEN = "Bearer eyJ0eXAiOiJKV1QiLCJhbGcbmcU2HhD"
        private const val BAD_REQUEST_MESSAGE = "BAD REQUEST"
    }

    private lateinit var loginRepositoryImpl: LoginRepositoryImpl

    private val loginDataSourceImpl: LoginDataSourceImpl = mock()

    @Before
    fun setUp() {
        loginRepositoryImpl = LoginRepositoryImpl(dataSource = loginDataSourceImpl)
    }

    @Test
    fun `EXPECT success result with roken` () {
        runBlocking {
            val expectedResult = USER_AUTH_TOKEN

            whenever(
                loginDataSourceImpl.login(
                    userName = USER_NAME,
                    userPassword = USER_PASSWORD
                )
            ).thenReturn(
                flow {
                    emit(ApiResult.Success(_data = USER_AUTH_TOKEN))
                }
            )

            var actualResult: String? = null

            loginRepositoryImpl.login(userName = USER_NAME, userPassword = USER_PASSWORD)
                .collect { result ->
                    when (result) {
                        is ApiResult.Success -> {
                            actualResult = result.data as String
                        }
                    }
                }

            assertEquals(expectedResult, actualResult)
        }
    }

    @Test
    fun `EXPECT error result with 400 error` () {
        runBlocking {
            val expectedResult = ApiErrorType.BAD_REQUEST_ERROR

            whenever(
                loginDataSourceImpl.login(
                    userName = USER_NAME,
                    userPassword = USER_PASSWORD
                )
            ).thenReturn(
                flow {
                    emit(
                        ApiResult.Error
                            (
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

            loginRepositoryImpl.login(userName = USER_NAME, userPassword = USER_PASSWORD)
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

}