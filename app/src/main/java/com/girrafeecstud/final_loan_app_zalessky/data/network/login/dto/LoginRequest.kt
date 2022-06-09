package com.girrafeecstud.final_loan_app_zalessky.data.network.login.dto

import com.google.gson.annotations.SerializedName

data class LoginRequest (
    @SerializedName("name") var name: String? = null,
    @SerializedName("password") var password: String? = null
)
