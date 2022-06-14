package com.girrafeecstud.final_loan_app_zalessky.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.girrafeecstud.final_loan_app_zalessky.data.network.login.ApiResult
import com.girrafeecstud.final_loan_app_zalessky.data.repository.LoginSharedPreferencesRepositoryImpl
import com.girrafeecstud.final_loan_app_zalessky.domain.entities.Loan
import com.girrafeecstud.final_loan_app_zalessky.domain.usecase.GetLoanByIdUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoanItemViewModel @Inject constructor(
    private val getLoanByIdUseCase: GetLoanByIdUseCase,
    private val loginSharedPreferencesRepositoryImpl: LoginSharedPreferencesRepositoryImpl
): ViewModel() {

    private var loanId: Long = 0

    private val state = MutableLiveData<LoanActivityState>()

    fun loadLoanData() {
        viewModelScope.launch {
            val bearerToken = async {
                loginSharedPreferencesRepositoryImpl.getUserBearerToken()
            }
            getLoanByIdUseCase(bearerToken = bearerToken.await(), loanId = loanId)
                .onStart {
                    setLoading()
                }
                .collect { result ->
                    hideLoading()
                    if (result is ApiResult.Success)
                        setSuccessResult(loan = result.data as Loan)
                    //if (result is ApiResult.Error)
                        //Log.i("tag", result.exception)
                }
        }
    }

    private fun setLoading() {
        state.value = LoanActivityState.IsLoading(true)
    }

    private fun hideLoading() {
        state.value = LoanActivityState.IsLoading(false)
    }

    private fun setSuccessResult(loan: Loan) {
        state.value = LoanActivityState.SuccessResult(loan = loan)
    }

    fun getState(): LiveData<LoanActivityState> {
        return state
    }

    fun setLoanId(loanId: Long) {
        this.loanId = loanId
    }

    sealed class LoanActivityState {
        data class IsLoading(val isLoading: Boolean): LoanActivityState()
        data class SuccessResult(val loan: Loan): LoanActivityState()
        // TODO провайдить нормальную ошибку
        data class ErrorResult(val errorMessage: String): LoanActivityState()
    }
}