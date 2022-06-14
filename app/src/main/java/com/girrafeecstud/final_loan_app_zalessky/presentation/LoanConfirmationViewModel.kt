package com.girrafeecstud.final_loan_app_zalessky.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.girrafeecstud.final_loan_app_zalessky.data.network.login.ApiResult
import com.girrafeecstud.final_loan_app_zalessky.data.repository.LoginSharedPreferencesRepositoryImpl
import com.girrafeecstud.final_loan_app_zalessky.domain.entities.Loan
import com.girrafeecstud.final_loan_app_zalessky.domain.entities.LoanRequest
import com.girrafeecstud.final_loan_app_zalessky.domain.entities.LoanState
import com.girrafeecstud.final_loan_app_zalessky.domain.usecase.ApplyLoanUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoanConfirmationViewModel @Inject constructor(
    private val applyLoanUseCase: ApplyLoanUseCase,
    private val loginSharedPreferencesRepositoryImpl: LoginSharedPreferencesRepositoryImpl
) : ViewModel() {

    private val state = MutableLiveData<LoanConfirmationFragmentState>()

    fun applyLoan(loanRequest: LoanRequest) {
        viewModelScope.launch {
            val bearerToken = async {
                loginSharedPreferencesRepositoryImpl.getUserBearerToken()
            }
            applyLoanUseCase(bearerToken = bearerToken.await(), loanRequest = loanRequest)
                .onStart {
                    setLoading()
                }
                .collect { result ->
                    hideLoading()
                    if (result is ApiResult.Success)
                        setSuccessStateValue(result.data as Loan)
                }
        }
    }

    private fun setLoading() {
        state.value = LoanConfirmationFragmentState.IsLoading(isLoading = true)
    }

    private fun hideLoading() {
        state.value = LoanConfirmationFragmentState.IsLoading(isLoading = false)
    }

    private fun setSuccessStateValue(loan: Loan) {
        state.value = LoanConfirmationFragmentState.SuccessResult(loan = loan)
    }

    fun getState(): LiveData<LoanConfirmationFragmentState> {
        return state
    }

    sealed class LoanConfirmationFragmentState {
        data class IsLoading(val isLoading: Boolean): LoanConfirmationFragmentState()
        data class SuccessResult(val loan: Loan): LoanConfirmationFragmentState()
        // TODO Провайдить тип ошибки через специальный класс
        data class ErrorResult(val errorMessage: String): LoanConfirmationFragmentState()
    }

    override fun onCleared() {
        super.onCleared()
    }

}