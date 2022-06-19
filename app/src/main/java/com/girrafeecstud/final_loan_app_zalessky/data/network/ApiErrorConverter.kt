package com.girrafeecstud.final_loan_app_zalessky.data.network

class ApiErrorConverter {

    fun convertHttpError(
        errorStatusCode: Int,
        errorMessage: String
    ): ApiError {

        var errorType = ApiErrorType.UNKNOWN_ERROR

        when (errorStatusCode) {
            400 -> {
                errorType = ApiErrorType.BAD_REQUEST_ERROR
            }
            401 -> {
                errorType = ApiErrorType.UNAUTHORIZED_ERROR
            }
            403 -> {
                errorType = ApiErrorType.RESOURCE_FORBIDDEN_ERROR
            }
            404 -> {
                errorType = ApiErrorType.NOT_FOUND_ERROR
            }
        }
        return ApiError(
            statusCode = errorStatusCode,
            errorMessage = errorMessage,
            errorType = errorType
        )
    }

    fun convertConnectionError(): ApiError {
        return ApiError(
            statusCode = null,
            errorMessage = null,
            errorType = ApiErrorType.NO_CONNECTION_ERROR
        )
    }

    fun convertTimeoutError(): ApiError {
        return ApiError(
            statusCode = null,
            errorMessage = null,
            errorType = ApiErrorType.TIMEOUT_EXCEEDED_ERROR
        )
    }

}