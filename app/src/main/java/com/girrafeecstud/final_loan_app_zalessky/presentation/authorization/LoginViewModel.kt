package com.girrafeecstud.final_loan_app_zalessky.presentation.authorization

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.girrafeecstud.final_loan_app_zalessky.data.network.ApiError
import com.girrafeecstud.final_loan_app_zalessky.data.network.login.ApiResult
import com.girrafeecstud.final_loan_app_zalessky.data.repository.LoginSharedPreferencesRepositoryImpl
import com.girrafeecstud.final_loan_app_zalessky.domain.entities.LoanConditions
import com.girrafeecstud.final_loan_app_zalessky.domain.usecase.LoginUseCase
import com.girrafeecstud.final_loan_app_zalessky.presentation.MainState
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val loginSharedPreferencesRepositoryImpl: LoginSharedPreferencesRepositoryImpl
): ViewModel() {

    private val state = MutableLiveData<MainState>()

    fun login(userName: String, userPassword: String) {
        viewModelScope.launch {
            loginUseCase(userName = userName, userPassword = userPassword)
                .onStart {
                    setLoading()
                }
                .collect { result ->
                    when (result) {
                        is ApiResult.Success -> {
                            setSuccessResult(token = result.data as String)
                        }
                        is ApiResult.Error -> {
                            hideLoading()
                            setError(apiError = result.data as ApiError)
                        }
                    }
                }
        }
    }

    fun saveLoginData(userBearerToken: String, userName: String) {
        viewModelScope.launch {
            async {
                loginSharedPreferencesRepositoryImpl.setUserAuthorized()
            }
            async {
                loginSharedPreferencesRepositoryImpl.setUserBearerToken(
                    userBearerToken = userBearerToken
                )
            }
            async {
                loginSharedPreferencesRepositoryImpl.setUserName(userName = userName)
            }
        }
    }

    fun getState(): LiveData<MainState> {
        return state
    }

    private fun hideLoading() {
        state.value =  MainState.IsLoading(isLoading = false)
    }

    private fun setLoading() {
        state.value = MainState.IsLoading(isLoading = true)
    }

    private fun setSuccessResult(token: String) {
        state.value = MainState.SuccessResult(data = token)
    }

    private fun setError(apiError: ApiError) {
        state.value = MainState.ErrorResult(apiError = apiError)
    }

}