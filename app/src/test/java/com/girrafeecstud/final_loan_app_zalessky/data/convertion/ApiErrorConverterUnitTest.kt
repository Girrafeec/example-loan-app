package com.girrafeecstud.final_loan_app_zalessky.data.convertion

import com.girrafeecstud.final_loan_app_zalessky.data.network.ApiError
import com.girrafeecstud.final_loan_app_zalessky.data.network.ApiErrorConverter
import com.girrafeecstud.final_loan_app_zalessky.data.network.ApiErrorType
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertEquals

class ApiErrorConverterUnitTest {

    private lateinit var apiErrorConverter: ApiErrorConverter

    @Before
    fun setUp() {
        apiErrorConverter = ApiErrorConverter()
    }

    @Test
    fun `EXPECT ApiError with TIMEOUT_EXCEEDED_ERROR error type` () {
        val expectedResult = ApiError(
            statusCode = null,
            errorMessage = null,
            errorType = ApiErrorType.TIMEOUT_EXCEEDED_ERROR
        )

        val actualResult = apiErrorConverter.convertTimeoutError()

        assertEquals(expectedResult, actualResult)
    }

    @Test
    fun `EXPECT ApiError with NO_CONNECTION_ERROR error type` () {
        val expectedResult = ApiError(
            statusCode = null,
            errorMessage = null,
            errorType = ApiErrorType.NO_CONNECTION_ERROR
        )

        val actualResult = apiErrorConverter.convertConnectionError()

        assertEquals(expectedResult, actualResult)
    }

    @Test
    fun `EXPECT ApiError with BAD_REQUEST_ERROR error type` () {
        val expectedResult = ApiError(
            statusCode = 400,
            errorMessage = "",
            errorType = ApiErrorType.BAD_REQUEST_ERROR
        )

        val actualResult = apiErrorConverter.convertHttpError(
            errorStatusCode = 400,
            errorMessage = ""
        )
        assertEquals(expectedResult, actualResult)
    }

    @Test
    fun `EXPECT ApiError with UNAUTHORIZED_ERROR error type` () {
        val expectedResult = ApiError(
            statusCode = 401,
            errorMessage = "",
            errorType = ApiErrorType.UNAUTHORIZED_ERROR
        )

        val actualResult = apiErrorConverter.convertHttpError(
            errorStatusCode = 401,
            errorMessage = ""
        )
        assertEquals(expectedResult, actualResult)
    }

    @Test
    fun `EXPECT ApiError with RESOURCE_FORBIDDEN_ERROR error type` () {
        val expectedResult = ApiError(
            statusCode = 403,
            errorMessage = "",
            errorType = ApiErrorType.RESOURCE_FORBIDDEN_ERROR
        )

        val actualResult = apiErrorConverter.convertHttpError(
            errorStatusCode = 403,
            errorMessage = ""
        )
        assertEquals(expectedResult, actualResult)
    }

    @Test
    fun `EXPECT ApiError with NOT_FOUND_ERROR error type` () {
        val expectedResult = ApiError(
            statusCode = 404,
            errorMessage = "",
            errorType = ApiErrorType.NOT_FOUND_ERROR
        )

        val actualResult = apiErrorConverter.convertHttpError(
            errorStatusCode = 404,
            errorMessage = ""
        )
        assertEquals(expectedResult, actualResult)
    }

    @Test
    fun `EXPECT ApiError with UNKNOWN_ERROR error type` () {
        val expectedResult = ApiError(
            statusCode = 410,
            errorMessage = "",
            errorType = ApiErrorType.UNKNOWN_ERROR
        )

        val actualResult = apiErrorConverter.convertHttpError(
            errorStatusCode = 410,
            errorMessage = ""
        )
        assertEquals(expectedResult, actualResult)
    }

}