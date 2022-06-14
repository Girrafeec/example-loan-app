package com.girrafeecstud.final_loan_app_zalessky.presentation

import com.girrafeecstud.final_loan_app_zalessky.data.network.ApiError

sealed class MainState {
    data class IsLoading(val isLoading: Boolean): MainState()
    data class SuccessResult(val data: Any): MainState()
    data class ErrorResult(val apiError: ApiError): MainState()
}
