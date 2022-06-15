package com.girrafeecstud.final_loan_app_zalessky.presentation.requestloan

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.girrafeecstud.final_loan_app_zalessky.data.network.ApiError
import com.girrafeecstud.final_loan_app_zalessky.data.network.login.ApiResult
import com.girrafeecstud.final_loan_app_zalessky.data.repository.LoginSharedPreferencesRepositoryImpl
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
    private val loginSharedPreferencesRepositoryImpl: LoginSharedPreferencesRepositoryImpl
) : ViewModel() {

    private val state = MutableLiveData<MainState>()

    private val chosenAmountValue = MutableLiveData<Double>()

    init {
        getLoanConditions()
    }

    private fun getLoanConditions() {
        viewModelScope.launch {
            val bearerToken =
                async {
                    loginSharedPreferencesRepositoryImpl.getUserBearerToken()
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

    fun setChosenAmountValue(amountValue: Double) {
        chosenAmountValue.value = amountValue
    }

    fun getChosenAmountValue(): LiveData<Double> {
        return chosenAmountValue
    }

    override fun onCleared() {
        super.onCleared()
    }
}