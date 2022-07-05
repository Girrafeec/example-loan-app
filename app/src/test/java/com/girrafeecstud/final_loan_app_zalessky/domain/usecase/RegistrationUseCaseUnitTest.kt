package com.girrafeecstud.final_loan_app_zalessky.domain.usecase

import com.girrafeecstud.final_loan_app_zalessky.data.network.ApiError
import com.girrafeecstud.final_loan_app_zalessky.data.network.ApiErrorType
import com.girrafeecstud.final_loan_app_zalessky.data.network.login.ApiResult
import com.girrafeecstud.final_loan_app_zalessky.domain.entities.Auth
import com.girrafeecstud.final_loan_app_zalessky.domain.repository.RegistrationRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.junit.Assert.assertEquals

class RegistrationUseCaseUnitTest {

    companion object {
        private const val USER_NAME = "userName"
        private const val USER_PASSWORD = "password"
        private const val USER_ROLE = "USER"
    }

    private lateinit var registrationUseCase: RegistrationUseCase

    private val registrationRepository: RegistrationRepository = mock()

    @Before
    fun setUp() {
        registrationUseCase = RegistrationUseCase(
            repository = registrationRepository
        )
    }

    @Test
    fun `EXPECT success result with Auth entity` () {
        runBlocking {
            val expectedResult = Auth(
                userName = USER_NAME,
                userRole = USER_ROLE
            )

            whenever(registrationRepository.registration(
                userName = USER_NAME,
                userPassword = USER_PASSWORD
            )).thenReturn(
                flow {
                    emit(
                        ApiResult.Success(
                            _data = Auth(
                                userName = USER_NAME,
                                userRole = USER_ROLE
                            )
                        )
                    )
                }
            )

            var actualResult: Auth? = null

            registrationUseCase.invoke(
                userName = USER_NAME,
                userPassword = USER_PASSWORD
            ).collect { result ->
                when (result) {
                    is ApiResult.Success -> {
                        actualResult = result.data as Auth
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

            whenever(registrationRepository.registration(
                userName = USER_NAME,
                userPassword = USER_PASSWORD
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

            registrationUseCase.invoke(
                userName = USER_NAME,
                userPassword = USER_PASSWORD
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