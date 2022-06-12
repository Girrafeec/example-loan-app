package com.girrafeecstud.final_loan_app_zalessky.data.network.loan.api

import com.girrafeecstud.final_loan_app_zalessky.data.network.ApiUrlConfig
import com.girrafeecstud.final_loan_app_zalessky.data.network.loan.dto.LoanConditionsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface LoanApi {

    @GET(ApiUrlConfig.LOANS_CONDITIONS_API_URL)
    suspend fun getLoanConditions(@Header("Authorization") authorizationToken: String?): Response<LoanConditionsResponse>
}