package com.girrafeecstud.final_loan_app_zalessky.presentation.requestloan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.girrafeecstud.final_loan_app_zalessky.data.network.ApiError
import com.girrafeecstud.final_loan_app_zalessky.data.network.login.ApiResult
import com.girrafeecstud.final_loan_app_zalessky.data.repository.LoginSharedPreferencesRepository
import com.girrafeecstud.final_loan_app_zalessky.domain.entities.LoanConditions
import com.girrafeecstud.final_loan_app_zalessky.domain.usecase.GetLoanConditionsUseCase
import com.girrafeecstud.final_loan_app_zalessky.presentation.MainState
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoanConditionsViewModel @Inject constructor(
    private val getLoanConditionsUseCase: GetLoanConditionsUseCase,
    private val loginSharedPreferencesRepository: LoginSharedPreferencesRepository
) : ViewModel() {

    private val state = MutableLiveData<MainState>()

    fun loadLoanConditions() {
        viewModelScope.launch {
            val bearerToken =
                async {
                    loginSharedPreferencesRepository.getUserBearerToken()
                }
            getLoanConditionsUseCase(bearerToken = bearerToken.await())
                .onStart {
                    setLoading()
                }
                .collect { result ->
                    hideLoading()
                    when (result) {
                        is ApiResult.Success -> {
                            setSuccessStateValue(result.data as LoanConditions)
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

    private fun setSuccessStateValue(loanConditions: LoanConditions) {
        state.value = MainState.SuccessResult(data = loanConditions)
    }

    private fun setErrorResult(apiError: ApiError) {
        state.value = MainState.ErrorResult(apiError = apiError)
    }

    fun getState(): LiveData<MainState> {
        return state
    }

    override fun onCleared() {
        super.onCleared()
    }
}