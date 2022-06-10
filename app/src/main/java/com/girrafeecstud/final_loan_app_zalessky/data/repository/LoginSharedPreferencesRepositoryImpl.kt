package com.girrafeecstud.final_loan_app_zalessky.data.repository

import com.girrafeecstud.final_loan_app_zalessky.data.datasource.LoginSharedPreferencesDataSourceImpl
import javax.inject.Inject

class LoginSharedPreferencesRepositoryImpl @Inject constructor(
    private val loginSharedPreferencesDataSourceImpl: LoginSharedPreferencesDataSourceImpl
) {

    suspend fun getUserAuthorizedStatus(): Boolean {
        return loginSharedPreferencesDataSourceImpl.getUserAuthorizedStatus()
    }

    suspend fun setUserAuthorized() {
        loginSharedPreferencesDataSourceImpl.setUserAuthorized()
    }

    suspend fun setUserUnauthorized() {
        loginSharedPreferencesDataSourceImpl.setUserUnauthorized()
    }

    suspend fun getUserBearerToken(): String? {
        return loginSharedPreferencesDataSourceImpl.getUserBearerToken()
    }

    suspend fun setUserBearerToken(userBearerToken: String) {
        loginSharedPreferencesDataSourceImpl.setUserBearerToken(userBearerToken = userBearerToken)
    }

    suspend fun getUserName(): String? {
        return loginSharedPreferencesDataSourceImpl.getUserName()
    }

    suspend fun setUserName(userName: String) {
        loginSharedPreferencesDataSourceImpl.setUserName(userName = userName)
    }

}