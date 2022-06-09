package com.girrafeecstud.final_loan_app_zalessky.data.network.registration.api

import com.girrafeecstud.final_loan_app_zalessky.data.network.ApiUrlConfig
import com.girrafeecstud.final_loan_app_zalessky.data.network.registration.dto.RegistrationRequest
import com.girrafeecstud.final_loan_app_zalessky.data.network.registration.dto.RegistrationResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RegistrationApi {

    @POST(ApiUrlConfig.REGISTRATION_API_URL)
    suspend fun registration(@Body registrationRequest: RegistrationRequest): Response<RegistrationResponse>

}