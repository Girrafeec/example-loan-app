package com.girrafeecstud.final_loan_app_zalessky.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.girrafeecstud.final_loan_app_zalessky.data.repository.LoginSharedPreferencesRepositoryImpl
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

class UserProfileViewModel @Inject constructor(
    private val loginSharedPreferencesRepositoryImpl: LoginSharedPreferencesRepositoryImpl
) : ViewModel() {

    private val userName = MutableLiveData<String>()

    init {
        loadUserName()
    }

    private fun loadUserName() {
        viewModelScope.launch {
            val name = async {
                loginSharedPreferencesRepositoryImpl.getUserName()
            }
            userName.value = name.await()
        }
    }

    fun getUserName(): LiveData<String> {
        return userName
    }

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