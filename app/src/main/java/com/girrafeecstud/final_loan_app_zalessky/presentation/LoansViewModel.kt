package com.girrafeecstud.final_loan_app_zalessky.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.girrafeecstud.final_loan_app_zalessky.data.network.login.ApiResult
import com.girrafeecstud.final_loan_app_zalessky.data.repository.LoginSharedPreferencesRepositoryImpl
import com.girrafeecstud.final_loan_app_zalessky.domain.entities.Loan
import com.girrafeecstud.final_loan_app_zalessky.domain.entities.LoanConditions
import com.girrafeecstud.final_loan_app_zalessky.domain.usecase.GetLoansListUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoansViewModel @Inject constructor(
    private val getLoansListUseCase: GetLoansListUseCase,
    private val loginSharedPreferencesRepositoryImpl: LoginSharedPreferencesRepositoryImpl
) : ViewModel() {

    private val loans = MutableLiveData<Loan>()

    private val state = MutableLiveData<LoansFragmentState>()

    init {
        getLoansList()
    }

    fun getLoansList() {
        viewModelScope.launch {
            val bearerToken = async {
                loginSharedPreferencesRepositoryImpl.getUserBearerToken()
            }
            getLoansListUseCase(bearerToken = bearerToken.await())
                .onStart {
                    setLoading()
                }
                .collect { result ->
                    hideLoading()
                    if (result is ApiResult.Success)
                        setSuccessStateValue(result.data as List<Loan>)
                }
        }
    }

    private fun setLoading() {
        state.value = LoansFragmentState.IsLoading(isLoading = true)
    }

    private fun hideLoading() {
        state.value = LoansFragmentState.IsLoading(isLoading = false)
    }

    private fun setSuccessStateValue(loans: List<Loan>) {
        state.value = LoansFragmentState.SuccessResult(loans = loans)
    }

    fun getState(): LiveData<LoansFragmentState> {
        return state
    }

    sealed class LoansFragmentState {
        data class IsLoading(val isLoading: Boolean): LoansFragmentState()
        data class SuccessResult(val loans: List<Loan>): LoansFragmentState()
        // TODO Провайдить тип ошибки через специальный класс
        data class ErrorResult(val errorMessage: String): LoansFragmentState()
    }

}