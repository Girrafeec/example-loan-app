package com.girrafeecstud.final_loan_app_zalessky.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.girrafeecstud.final_loan_app_zalessky.data.network.ApiStatus
import com.girrafeecstud.final_loan_app_zalessky.domain.usecase.LoginUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
): ViewModel() {

    private val isConnecting = MutableLiveData<Boolean>()

    private val token = MutableLiveData<String>()

    init {
        isConnecting.value = false
        token.value = ""
    }

    private fun makeConnectionStatusTrue() {
        isConnecting.value = true
    }

    private fun makeConnectionStatusFalse() {
        isConnecting.value = false
    }

    fun getConnectiongStatus(): LiveData<Boolean> {
        return isConnecting
    }

    fun getToken(): LiveData<String> {
        return token
    }

    fun login(userName: String, userPassword: String) {
        viewModelScope.launch {
            loginUseCase(userName = userName, userPassword = userPassword)
                .onStart {
                    makeConnectionStatusTrue()
                }
                .collect { result ->
                    makeConnectionStatusFalse()
                    when (result.status) {
                        ApiStatus.SUCCESS -> {token.value = result.data.toString()}
                        ApiStatus.ERROR -> {
                            Log.i("tag vm", result.message.toString())
                        }
                    }
                }
        }
    }

}