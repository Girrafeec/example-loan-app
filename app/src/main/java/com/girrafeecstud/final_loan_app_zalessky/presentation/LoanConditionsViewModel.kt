package com.girrafeecstud.final_loan_app_zalessky.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.girrafeecstud.final_loan_app_zalessky.data.network.login.ApiResult
import com.girrafeecstud.final_loan_app_zalessky.data.repository.LoginSharedPreferencesRepositoryImpl
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

    private val requestResult = MutableLiveData<ApiResult<Any>>()

    init {
        getLoanConditions()
    }

    private fun getLoanConditions() {
        viewModelScope.launch {
            val bearerToken =
                async {
                    loginSharedPreferencesRepositoryImpl.getUserBearerToken()
                }
            Log.i("tag l c vm", bearerToken.await().toString())
            getLoanConditionsUseCase(bearerToken = bearerToken.await())
                .collect { result ->
                    requestResult.value = result
                }
        }
    }

    fun getLoanConditionsRequestResult(): LiveData<ApiResult<Any>> {
        return requestResult
    }

}