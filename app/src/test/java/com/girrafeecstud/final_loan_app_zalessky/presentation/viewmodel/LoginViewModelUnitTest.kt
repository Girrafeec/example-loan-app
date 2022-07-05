package com.girrafeecstud.final_loan_app_zalessky.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.girrafeecstud.final_loan_app_zalessky.MainDispatcherRule
import com.girrafeecstud.final_loan_app_zalessky.data.network.login.ApiResult
import com.girrafeecstud.final_loan_app_zalessky.domain.usecase.LoginUseCase
import com.girrafeecstud.final_loan_app_zalessky.presentation.MainState
import com.girrafeecstud.final_loan_app_zalessky.presentation.authorization.LoginViewModel
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class LoginViewModelUnitTest {

    companion object {
        const val USER_AUTH_TOKEN = "Bearer rgrtgrgrtgtg"
    }

    @get:Rule
    val mainTestDispatcherRule = MainDispatcherRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var loginViewModel: LoginViewModel

    private val loginUseCase: LoginUseCase = mock()

    @Before
    fun setUp() {
        loginViewModel = LoginViewModel(
            loginUseCase = loginUseCase,
            loginSharedPreferencesRepository = mock(),
            validatorsRepository = mock()
        )
    }

    @Test
    fun `EXPECT  success result with Bearer token` () {
        runBlocking {

            val expectedResult = MainState.SuccessResult(data = "Bearer rgrtgrgrtgtg")

            whenever(
                loginUseCase(
                    userName = any(),
                    userPassword = any()
                )
            ).thenReturn(
                flow {
                    emit(
                        ApiResult.Success(
                        _data = USER_AUTH_TOKEN
                        )
                    )
                }
            )

            loginViewModel.login(
                userName = "userName",
                userPassword = "password"
            )

            var actualResult = loginViewModel.getState().value

            assertEquals(expectedResult, actualResult)
        }
    }

}