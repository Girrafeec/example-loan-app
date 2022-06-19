package com.girrafeecstud.final_loan_app_zalessky.presentation.authorization

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.girrafeecstud.final_loan_app_zalessky.data.network.ApiError
import com.girrafeecstud.final_loan_app_zalessky.data.network.login.ApiResult
import com.girrafeecstud.final_loan_app_zalessky.data.repository.ValidatorsRepository
import com.girrafeecstud.final_loan_app_zalessky.domain.entities.Auth
import com.girrafeecstud.final_loan_app_zalessky.domain.usecase.RegistrationUseCase
import com.girrafeecstud.final_loan_app_zalessky.presentation.MainState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

class RegistrationViewModel @Inject constructor(
    private val registrationUseCase: RegistrationUseCase,
    private val validatorsRepository: ValidatorsRepository
): ViewModel() {

    private val state = MutableLiveData<MainState>()

    fun isUserNameValid(userName: String): Boolean {
        return validatorsRepository.isUserNameValid(userName = userName)
    }

    fun isLoginPasswordValid(password: String): Boolean {
        return validatorsRepository.isUserPasswordValid(password = password)
    }

    fun registration(userName: String, userPassword: String) {
        viewModelScope.launch {
            registrationUseCase(userName = userName, userPassword = userPassword)
                .onStart {
                    setLoading()
                }
                .collect { result ->
                    hideLoading()
                    when (result) {
                        is ApiResult.Success -> {
                            setSuccessStateValue(result.data as Auth)
                        }
                        is ApiResult.Error -> {
                            setErrorResult(apiError = result.data as ApiError)
                        }
                    }
                }
        }
    }

    private fun setLoading() {
        state.value = MainState.IsLoading(isLoading = true)
    }

    private fun hideLoading() {
        state.value = MainState.IsLoading(isLoading = false)
    }

    private fun setSuccessStateValue(auth: Auth) {
        state.value = MainState.SuccessResult(data = auth)
    }

    private fun setErrorResult(apiError: ApiError) {
        state.value = MainState.ErrorResult(apiError = apiError)
    }

    fun getState(): LiveData<MainState> {
        return state
    }

}