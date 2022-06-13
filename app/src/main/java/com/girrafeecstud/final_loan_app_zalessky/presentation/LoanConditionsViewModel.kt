package com.girrafeecstud.final_loan_app_zalessky.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.girrafeecstud.final_loan_app_zalessky.data.network.login.ApiResult
import com.girrafeecstud.final_loan_app_zalessky.data.repository.LoginSharedPreferencesRepositoryImpl
import com.girrafeecstud.final_loan_app_zalessky.domain.entities.LoanConditions
import com.girrafeecstud.final_loan_app_zalessky.domain.usecase.GetLoanConditionsUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoanConditionsViewModel @Inject constructor(
    private val getLoanConditionsUseCase: GetLoanConditionsUseCase,
    private val loginSharedPreferencesRepositoryImpl: LoginSharedPreferencesRepositoryImpl
) : ViewModel() {

    private val state = MutableLiveData<LoanConditionsFragmentState>()

    private val requestResult = MutableLiveData<ApiResult<Any>>()

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
                    requestResult.value = result
                    if (result is ApiResult.Success)
                        setSuccessStateValue(result.data as LoanConditions)
                }
        }
    }

    private fun setLoading() {
        state.value = LoanConditionsFragmentState.IsLoading(isLoading = true)
    }

    private fun hideLoading() {
        state.value = LoanConditionsFragmentState.IsLoading(isLoading = false)
    }

    private fun setSuccessStateValue(loanConditions: LoanConditions) {
        state.value = LoanConditionsFragmentState.SuccessResult(loanConditions = loanConditions)
    }

    fun getState(): LiveData<LoanConditionsFragmentState> {
        return state
    }

    fun getLoanConditionsRequestResult(): LiveData<ApiResult<Any>> {
        return requestResult
    }

    fun setChosenAmountValue(amountValue: Double) {
        chosenAmountValue.value = amountValue
    }

    fun getChosenAmountValue(): LiveData<Double> {
        return chosenAmountValue
    }

    sealed class LoanConditionsFragmentState {
        data class IsLoading(val isLoading: Boolean): LoanConditionsFragmentState()
        data class SuccessResult(val loanConditions: LoanConditions): LoanConditionsFragmentState()
        // TODO Провайдить тип ошибки через специальный класс
        data class ErrorResult(val errorMessage: String): LoanConditionsFragmentState()
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("tag", "succcer")
    }
}