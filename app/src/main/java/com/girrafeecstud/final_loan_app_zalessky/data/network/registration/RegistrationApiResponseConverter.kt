package com.girrafeecstud.final_loan_app_zalessky.data.network.registration

import com.girrafeecstud.final_loan_app_zalessky.data.network.registration.dto.RegistrationResponse
import com.girrafeecstud.final_loan_app_zalessky.domain.entities.Auth
import javax.inject.Inject

class RegistrationApiResponseConverter @Inject constructor(

) {

    fun getAuthFromRegistrationResponse(registrationResponse: RegistrationResponse): Auth {
        return Auth(
            userName = registrationResponse.userName,
            userRole = registrationResponse.userRole
        )
    }

}