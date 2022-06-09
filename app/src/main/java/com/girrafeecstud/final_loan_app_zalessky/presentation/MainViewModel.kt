package com.girrafeecstud.final_loan_app_zalessky.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.girrafeecstud.final_loan_app_zalessky.data.network.login.dto.LoginRequest
import com.girrafeecstud.final_loan_app_zalessky.data.network.registration.dto.RegistrationRequest
import com.girrafeecstud.final_loan_app_zalessky.data.repository.LoginRepositoryImpl
import com.girrafeecstud.final_loan_app_zalessky.data.repository.RegistrationRepositoryImpl
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val loginRepositoryImpl: LoginRepositoryImpl,
    private val registrationRepositoryImpl: RegistrationRepositoryImpl
): ViewModel() {

    private val result = MutableLiveData<String>()

    init {
        result.value = ""
        Log.i("tag", "vm init")
        request()
    }

    private fun request() {

        viewModelScope.launch {
            val loginRequest = LoginRequest(userName = "ivan_zal", userPassword = "qwertyuiop")
            loginRepositoryImpl.login(loginRequest = loginRequest)

            //val registrationRequest = RegistrationRequest(userName = "ivan_zal", userPassword = "qwertyuiop")
            //registrationRepositoryImpl.registration(registrationRequest = registrationRequest)
        }
    }

    fun getResult(): LiveData<String> {
        return result
    }

}