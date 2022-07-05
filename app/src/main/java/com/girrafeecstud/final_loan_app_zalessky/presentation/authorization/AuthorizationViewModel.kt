package com.girrafeecstud.final_loan_app_zalessky.presentation.authorization

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.girrafeecstud.final_loan_app_zalessky.data.repository.LoginSharedPreferencesRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

class AuthorizationViewModel @Inject constructor(
    private val loginSharedPreferencesRepository: LoginSharedPreferencesRepository
): ViewModel() {

    private val userAuthorized = MutableLiveData<Boolean>()

    init {
        getUserAuthorizationStatus()
    }

    private fun getUserAuthorizationStatus() {
        viewModelScope.launch {

            val authStatus = async {
                loginSharedPreferencesRepository.getUserAuthorizedStatus()
            }
            userAuthorized.value = authStatus.await()
        }
    }

    fun isUserAuthorized(): LiveData<Boolean> {
        return userAuthorized
    }
}