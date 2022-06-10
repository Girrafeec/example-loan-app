package com.girrafeecstud.final_loan_app_zalessky.data.datasource

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity

class LoginSharedPreferencesDataSourceImpl(context: Context) {

    private val context: Context

    companion object {
        private const val USER_AUTHORIZED = "USER_AUTHORIZED"
        private const val USER_BEARER_TOKEN = "USER_BEARER_TOKEN"
        private const val USER_NAME = "USER_NAME"
    }

    init {
        this.context = context
    }

    fun getUserAuthorizedStatus(): Boolean {
        return context
            .getSharedPreferences(
                SharedPreferencesConfig.SHARED_PREFS,
                AppCompatActivity.MODE_PRIVATE
            )
            .getBoolean(USER_AUTHORIZED, false)
    }

    fun setUserAuthorized() {
        context
            .getSharedPreferences(
                SharedPreferencesConfig.SHARED_PREFS,
                AppCompatActivity.MODE_PRIVATE
            )
            .edit()
            .putBoolean(USER_AUTHORIZED, true)
            .apply()
    }

    fun setUserUnauthorized() {
        context
            .getSharedPreferences(
                SharedPreferencesConfig.SHARED_PREFS,
                AppCompatActivity.MODE_PRIVATE
            )
            .edit()
            .putBoolean(USER_AUTHORIZED, false)
            .apply()
    }

    // TODO оставлять ли так знак вопроса?
    fun getUserBearerToken(): String? {
        return context
            .getSharedPreferences(
                SharedPreferencesConfig.SHARED_PREFS,
                AppCompatActivity.MODE_PRIVATE
            )
            .getString(USER_BEARER_TOKEN, "")
    }

    fun setUserBearerToken(userBearerToken: String) {
        context
            .getSharedPreferences(
                SharedPreferencesConfig.SHARED_PREFS,
                AppCompatActivity.MODE_PRIVATE
            )
            .edit()
            .putString(USER_BEARER_TOKEN, userBearerToken)
            .apply()
    }

    // TODO оставлять ли так знак вопроса?
    fun getUserName(): String? {
        return context
            .getSharedPreferences(
                SharedPreferencesConfig.SHARED_PREFS,
                AppCompatActivity.MODE_PRIVATE
            )
            .getString(USER_NAME, "")
    }

    fun setUserName(userName: String) {
        context
            .getSharedPreferences(
                SharedPreferencesConfig.SHARED_PREFS,
                AppCompatActivity.MODE_PRIVATE
            )
            .edit()
            .putString(USER_NAME, userName)
            .apply()
    }

}