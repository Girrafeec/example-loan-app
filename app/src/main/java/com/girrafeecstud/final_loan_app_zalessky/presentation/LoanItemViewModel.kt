package com.girrafeecstud.final_loan_app_zalessky.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.girrafeecstud.final_loan_app_zalessky.data.network.ApiError
import com.girrafeecstud.final_loan_app_zalessky.data.network.login.ApiResult
import com.girrafeecstud.final_loan_app_zalessky.data.repository.LocalDateTimeConverterRepository
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
    private val loginSharedPreferencesRepositoryImpl: LoginSharedPreferencesRepositoryImpl,
    private val localDateTimeConverterRepository: LocalDateTimeConverterRepository
): ViewModel() {

    private var loanId: Long = 0

    private val loan = MutableLiveData<Loan>()

    private val state = MutableLiveData<MainState>()

    fun getDateStringValue(
        dateTimeString: String)
    : String {

        val localDateTime =
            localDateTimeConverterRepository.getLocalDateTimeFromString(dateTimeString = dateTimeString)

        return localDateTimeConverterRepository
            .getDateStringFromLocalDateTime(localDateTime = localDateTime)
    }

    fun getTimeStringValue(
        dateTimeString: String
    ): String {

        val localDateTime =
            localDateTimeConverterRepository.getLocalDateTimeFromString(dateTimeString = dateTimeString)

        return localDateTimeConverterRepository
            .getTimeStringFromLocalDateTime(localDateTime = localDateTime)
    }

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
                    when (result) {
                        is ApiResult.Success -> {
                            loan.value = result.data as Loan
                            setSuccessResult(loan = result.data as Loan)
                        }
                        is ApiResult.Error -> {
                            setErrorResult(apiError = result.data as ApiError)
                        }
                    }
                }
        }
    }

    private fun setLoading() {
        state.value = MainState.IsLoading(true)
    }

    private fun hideLoading() {
        state.value = MainState.IsLoading(false)
    }

    private fun setSuccessResult(loan: Loan) {
        state.value = MainState.SuccessResult(data = loan)
    }

    private fun setErrorResult(apiError: ApiError) {
        state.value = MainState.ErrorResult(apiError = apiError)
    }

    fun getState(): LiveData<MainState> {
        return state
    }

    fun setLoanId(loanId: Long) {
        this.loanId = loanId
    }

    fun getLoan(): LiveData<Loan> {
        return loan
    }
}