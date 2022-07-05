package com.girrafeecstud.final_loan_app_zalessky.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.girrafeecstud.final_loan_app_zalessky.data.repository.LoginSharedPreferencesRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

class UserProfileViewModel @Inject constructor(
    private val loginSharedPreferencesRepository: LoginSharedPreferencesRepository
) : ViewModel() {

    private val userName = MutableLiveData<String>()

    init {
        loadUserName()
    }

    private fun loadUserName() {
        viewModelScope.launch {
            val name = async {
                loginSharedPreferencesRepository.getUserName()
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
                loginSharedPreferencesRepository.clearUserBearerToken()
            }
            async {
                loginSharedPreferencesRepository.clearUserName()
            }
            async {
                loginSharedPreferencesRepository.setUserUnauthorized()
            }
        }
    }

}