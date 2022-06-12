package com.girrafeecstud.final_loan_app_zalessky.data.network.login.dto

import com.google.gson.annotations.SerializedName

data class LoginRequest (
    @SerializedName("name")
    val userName: String,
    @SerializedName("password")
    val userPassword: String
)
