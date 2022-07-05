package com.girrafeecstud.final_loan_app_zalessky.data.convertion

import com.girrafeecstud.final_loan_app_zalessky.data.network.registration.RegistrationApiResponseConverter
import com.girrafeecstud.final_loan_app_zalessky.data.network.registration.dto.RegistrationResponse
import com.girrafeecstud.final_loan_app_zalessky.domain.entities.Auth
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertEquals

class RegistrationApiResponseConverterUnitTest {

    private lateinit var registrationApiResponseConverter: RegistrationApiResponseConverter

    @Before
    fun setUp() {
        registrationApiResponseConverter = RegistrationApiResponseConverter()
    }

    @Test
    fun `WHEN put RegistrationResponse expect Auth with same values` () {
        val expectedResult = Auth(
            userName = "userName",
            userRole = "USER"
        )

        val registrationResponse = RegistrationResponse(
            userName = "userName",
            userRole = "USER"
        )

        val actualResult: Auth = registrationApiResponseConverter.getAuthFromRegistrationResponse(
            registrationResponse =  registrationResponse
        )

        assertEquals(expectedResult, actualResult)
    }

}