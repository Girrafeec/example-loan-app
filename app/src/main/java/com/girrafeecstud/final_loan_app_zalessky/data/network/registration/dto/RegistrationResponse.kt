package com.girrafeecstud.final_loan_app_zalessky.data.network.registration.dto

import com.google.gson.annotations.SerializedName

data class RegistrationResponse(
    @SerializedName("name")
    var userName: String? = null,
    @SerializedName("role")
    var userRole: String? = null
)