package com.girrafeecstud.final_loan_app_zalessky.data.network.login.dto

data class LoginResponse(
    var bearer: String? = null,
    var userToken: String? = null
)
