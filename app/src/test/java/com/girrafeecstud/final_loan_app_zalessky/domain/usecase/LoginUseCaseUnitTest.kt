package com.girrafeecstud.final_loan_app_zalessky.domain.usecase

import com.girrafeecstud.final_loan_app_zalessky.data.network.ApiError
import com.girrafeecstud.final_loan_app_zalessky.data.network.ApiErrorType
import com.girrafeecstud.final_loan_app_zalessky.data.network.login.ApiResult
import com.girrafeecstud.final_loan_app_zalessky.domain.repository.LoginRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.junit.Assert.assertEquals

class LoginUseCaseUnitTest {

    companion object {
        private const val USER_AUTH_TOKEN = "Bearer gtgegbrg8berg"
    }

    private lateinit var loginUseCase: LoginUseCase

    private val loginRepository: LoginRepository = mock()

    @Before
    fun setUp() {
        loginUseCase = LoginUseCase(
            repository = loginRepository
        )
    }

    @Test
    fun `EXPECT success result with bearer token` () {
        runBlocking {
            val expectedResult = USER_AUTH_TOKEN

            whenever(loginRepository.login(
                userName = "userName",
                userPassword = "password"
            )).thenReturn(
                flow {
                    emit(
                        ApiResult.Success(
                            _data = USER_AUTH_TOKEN
                        )
                    )
                }
            )

            var actualResult: String? = null

            loginUseCase(
                userName = "userName",
                userPassword = "password"
            ).collect { result ->
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
    fun `EXPECT error result with BAD_REQUEST_ERROR error type` () {
        runBlocking {
            val expectedResult = ApiError(
                statusCode = 400,
                errorMessage = "",
                errorType = ApiErrorType.BAD_REQUEST_ERROR
            )

            whenever(loginRepository.login(
                userName = "userName",
                userPassword = "password"
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

            loginUseCase(
                userName = "userName",
                userPassword = "password"
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