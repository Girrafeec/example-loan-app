package com.girrafeecstud.final_loan_app_zalessky.presentation.authorization

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.girrafeecstud.final_loan_app_zalessky.data.network.login.ApiResult
import com.girrafeecstud.final_loan_app_zalessky.domain.usecase.RegistrationUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

class RegistrationViewModel @Inject constructor(
    private val registrationUseCase: RegistrationUseCase
): ViewModel() {

    private val isConnecting = MutableLiveData<Boolean>()

    private val registrationResult = MutableLiveData<ApiResult<Any>>()

    init {
        //isConnecting.value = false
    }

    private fun makeConnectionStatusTrue() {
        isConnecting.value = true
    }

    private fun makeConnectionStatusFalse() {
        isConnecting.value = false
    }

    fun getConnectionStatus(): LiveData<Boolean> {
        return isConnecting
    }

    fun getRegistrationResult(): LiveData<ApiResult<Any>> {
        return registrationResult
    }

    fun registration(userName: String, userPassword: String) {
        viewModelScope.launch {
            registrationUseCase(userName = userName, userPassword = userPassword)
                .onStart {
                    makeConnectionStatusTrue()
                }
                .collect { result ->
                    registrationResult.value = result
                    if (result is ApiResult.Error)
                        makeConnectionStatusFalse()
                }
        }
    }

}