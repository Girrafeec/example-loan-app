package com.girrafeecstud.final_loan_app_zalessky.data.network

data class ApiError (
    val statusCode: Int?,
    val errorMessage: String?,
    val errorType: ApiErrorType
)