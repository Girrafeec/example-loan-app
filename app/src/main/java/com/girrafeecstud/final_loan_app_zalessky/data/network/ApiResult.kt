package com.girrafeecstud.final_loan_app_zalessky.data.network.login

import com.girrafeecstud.final_loan_app_zalessky.data.network.ApiError
import com.girrafeecstud.final_loan_app_zalessky.data.network.ApiStatus

sealed class ApiResult <out T> (
    val status: ApiStatus,
    val data: T?,
    val message: String?
    ) {
    data class Success <out R> (val _data: R?): ApiResult<R>(
        status = ApiStatus.SUCCESS,
        data = _data,
        message = null
    )
    data class Error <out ApiError> (val apiError: ApiError): ApiResult<ApiError>(
        status = ApiStatus.ERROR,
        data = apiError,
        message = null
    )
}
