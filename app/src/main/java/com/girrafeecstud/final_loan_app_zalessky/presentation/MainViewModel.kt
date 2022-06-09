package com.girrafeecstud.final_loan_app_zalessky.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.girrafeecstud.final_loan_app_zalessky.data.network.login.dto.LoginRequest
import com.girrafeecstud.final_loan_app_zalessky.data.repository.LoginRepositoryImpl
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val loginRepositoryImpl: LoginRepositoryImpl
): ViewModel() {

    init {
        Log.i("tag", "vm init")
        request()
    }

    private fun request() {

        viewModelScope.launch {
            val loginRequest = LoginRequest(name = "ivan_zal", "qwertyuiop")

            loginRepositoryImpl.login(loginRequest = loginRequest)
        }
    }

}