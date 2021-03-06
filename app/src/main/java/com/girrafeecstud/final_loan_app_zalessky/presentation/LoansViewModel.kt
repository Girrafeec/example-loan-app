package com.girrafeecstud.final_loan_app_zalessky.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.girrafeecstud.final_loan_app_zalessky.data.network.ApiError
import com.girrafeecstud.final_loan_app_zalessky.data.network.login.ApiResult
import com.girrafeecstud.final_loan_app_zalessky.data.repository.LoginSharedPreferencesRepository
import com.girrafeecstud.final_loan_app_zalessky.domain.entities.Loan
import com.girrafeecstud.final_loan_app_zalessky.domain.usecase.GetLoansListUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoansViewModel @Inject constructor(
    private val getLoansListUseCase: GetLoansListUseCase,
    private val loginSharedPreferencesRepository: LoginSharedPreferencesRepository
) : ViewModel() {

    private val loans = MutableLiveData<List<Loan>>()

    private val state = MutableLiveData<MainState>()

    init {
        getRemoteLoansList()
    }

    fun getRemoteLoansList() {
        viewModelScope.launch {
            val bearerToken = async {
                loginSharedPreferencesRepository.getUserBearerToken()
            }
            getLoansListUseCase(bearerToken = bearerToken.await())
                .onStart {
                    setLoading()
                }
                .collect { result ->
                    hideLoading()
                    when (result) {
                        is ApiResult.Success -> {
                            loans.value = result.data as List<Loan>
                            setSuccessResult(loans = result.data as List<Loan>)
                        }
                        is ApiResult.Error -> {
                            setError(apiError = result.data as ApiError)
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

    private fun setSuccessResult(loans: List<Loan>) {
        state.value = MainState.SuccessResult(data = loans)
    }

    private fun setError(apiError: ApiError) {
        state.value = MainState.ErrorResult(apiError = apiError)
    }

    fun getState(): LiveData<MainState> {
        return state
    }

    fun getLoans(): LiveData<List<Loan>> {
        return loans
    }

}