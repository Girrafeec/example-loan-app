package com.girrafeecstud.final_loan_app_zalessky.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.girrafeecstud.final_loan_app_zalessky.data.repository.LoginSharedPreferencesRepositoryImpl
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

class UserProfileViewModel @Inject constructor(
    private val loginSharedPreferencesRepositoryImpl: LoginSharedPreferencesRepositoryImpl
) : ViewModel() {

    fun exitAccount() {
        viewModelScope.launch {
            async {
                loginSharedPreferencesRepositoryImpl.clearUserBearerToken()
            }
            async {
                loginSharedPreferencesRepositoryImpl.clearUserName()
            }
            async {
                loginSharedPreferencesRepositoryImpl.setUserUnauthorized()
            }
        }
    }

}