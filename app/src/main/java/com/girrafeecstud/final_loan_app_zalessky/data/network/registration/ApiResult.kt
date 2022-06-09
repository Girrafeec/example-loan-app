package com.girrafeecstud.final_loan_app_zalessky.data.network.registration

import com.girrafeecstud.final_loan_app_zalessky.data.network.ApiStatus

sealed class ApiResult <out T> (
    val status: ApiStatus,
    val data: T?,
    val message: String?
    ) {
    data class Success <out R> (val _data: R?): ApiResult<R> (
        status = ApiStatus.SUCCESS,
        data = _data,
        message = null
    )
    data class Error (val exception: String): ApiResult<Nothing> (
        status = ApiStatus.ERROR,
        data = null,
        message = exception
    )
}
