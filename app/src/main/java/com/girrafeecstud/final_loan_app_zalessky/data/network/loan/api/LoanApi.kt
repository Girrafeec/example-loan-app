package com.girrafeecstud.final_loan_app_zalessky.data.network.loan.api

import com.girrafeecstud.final_loan_app_zalessky.data.network.ApiUrlConfig
import com.girrafeecstud.final_loan_app_zalessky.data.network.loan.dto.LoanApiRequest
import com.girrafeecstud.final_loan_app_zalessky.data.network.loan.dto.LoanConditionsResponse
import com.girrafeecstud.final_loan_app_zalessky.data.network.loan.dto.LoanResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface LoanApi {

    @GET(ApiUrlConfig.LOANS_CONDITIONS_API_URL)
    suspend fun getLoanConditions(
        @Header("Authorization") authorizationToken: String?
    ): Response<LoanConditionsResponse>

    @POST(ApiUrlConfig.MAIN_LOANS_API_URL)
    suspend fun applyLoan(
        @Header("Authorization") authorizationToken: String?,
        @Body loanApiRequest: LoanApiRequest
    ): Response<LoanResponse>
}