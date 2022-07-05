package com.girrafeecstud.final_loan_app_zalessky.data.repository

import com.girrafeecstud.final_loan_app_zalessky.data.datasource.LoginSharedPreferencesDataSource
import javax.inject.Inject

class LoginSharedPreferencesRepository @Inject constructor(
    private val loginSharedPreferencesDataSource: LoginSharedPreferencesDataSource
) {

    suspend fun getUserAuthorizedStatus(): Boolean {
        return loginSharedPreferencesDataSource.getUserAuthorizedStatus()
    }

    suspend fun setUserAuthorized() {
        loginSharedPreferencesDataSource.setUserAuthorized()
    }

    suspend fun setUserUnauthorized() {
        loginSharedPreferencesDataSource.setUserUnauthorized()
    }

    suspend fun getUserBearerToken(): String? {
        return loginSharedPreferencesDataSource.getUserBearerToken()
    }

    suspend fun setUserBearerToken(userBearerToken: String) {
        loginSharedPreferencesDataSource.setUserBearerToken(userBearerToken = userBearerToken)
    }

    suspend fun clearUserBearerToken() {
        loginSharedPreferencesDataSource.clearUserBearerToken()
    }

    suspend fun getUserName(): String? {
        return loginSharedPreferencesDataSource.getUserName()
    }

    suspend fun setUserName(userName: String) {
        loginSharedPreferencesDataSource.setUserName(userName = userName)
    }

    suspend fun clearUserName() {
        loginSharedPreferencesDataSource.clearUserName()
    }

}