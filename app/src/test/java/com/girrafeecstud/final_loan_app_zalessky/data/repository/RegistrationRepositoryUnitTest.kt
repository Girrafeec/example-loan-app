package com.girrafeecstud.final_loan_app_zalessky.data.repository

import com.girrafeecstud.final_loan_app_zalessky.data.datasource.RegistrationDataSourceImpl
import com.girrafeecstud.final_loan_app_zalessky.data.network.ApiError
import com.girrafeecstud.final_loan_app_zalessky.data.network.ApiErrorType
import com.girrafeecstud.final_loan_app_zalessky.data.network.login.ApiResult
import com.girrafeecstud.final_loan_app_zalessky.domain.entities.Auth
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.junit.Assert.assertEquals

class RegistrationRepositoryUnitTest {

    companion object {
        private const val USER_NAME = "userName"
        private const val USER_PASSWORD = "password"
        private const val USER_ROLE = "USER"
        private const val BAD_REQUEST_MESSAGE = "BAD REQUEST"
    }

    private lateinit var registrationRepositoryImpl: RegistrationRepositoryImpl

    private val registrationDataSourceImpl: RegistrationDataSourceImpl = mock()

    @Before
    fun setUp() {
        registrationRepositoryImpl = RegistrationRepositoryImpl(dataSource = registrationDataSourceImpl)
    }

    @Test
    fun `EXPECT Auth entuty with same fields values` () {
        runBlocking {
            val expectedResult = Auth(
                userName = USER_NAME,
                userRole = USER_ROLE
            )

            whenever(
                registrationDataSourceImpl.registration(
                userName = USER_NAME,
                userPassword = USER_PASSWORD
                )
            ).thenReturn(
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

            registrationRepositoryImpl.registration(
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
    fun `EXPECT error result with 400 error` () {
        runBlocking {
            val expectedResult = ApiErrorType.BAD_REQUEST_ERROR

            whenever(
                registrationDataSourceImpl.registration(
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

            registrationRepositoryImpl.registration(
                userName = USER_NAME,
                userPassword = USER_PASSWORD
            ).collect { result ->
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