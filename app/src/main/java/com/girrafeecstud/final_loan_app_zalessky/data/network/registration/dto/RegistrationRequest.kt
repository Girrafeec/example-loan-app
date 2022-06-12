package com.girrafeecstud.final_loan_app_zalessky.data.network.registration.dto

import com.google.gson.annotations.SerializedName

data class RegistrationRequest(
    @SerializedName("name")
    val userName: String,
    @SerializedName("password")
    val userPassword: String
)
