package com.girrafeecstud.final_loan_app_zalessky.data.repository

import android.util.Log
import com.girrafeecstud.final_loan_app_zalessky.data.network.registration.api.RegistrationApi
import com.girrafeecstud.final_loan_app_zalessky.data.network.registration.dto.RegistrationRequest
import javax.inject.Inject

class RegistrationRepositoryImpl @Inject constructor(
    private val registrationApi: RegistrationApi
) {

    suspend fun registration(registrationRequest: RegistrationRequest) {
        val response = registrationApi.registration(registrationRequest = registrationRequest)
        Log.i("tag", response.code().toString())
    }

}