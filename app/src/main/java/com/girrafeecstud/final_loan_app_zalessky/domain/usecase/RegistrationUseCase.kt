package com.girrafeecstud.final_loan_app_zalessky.domain.usecase

import com.girrafeecstud.final_loan_app_zalessky.data.network.login.ApiResult
import com.girrafeecstud.final_loan_app_zalessky.data.repository.RegistrationRepositoryImpl
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RegistrationUseCase @Inject constructor(
    private val registrationRepositoryImpl: RegistrationRepositoryImpl
) {

    suspend operator fun invoke(
        userName: String,
        userPassword: String
    ): Flow<ApiResult<Any>> {
        return registrationRepositoryImpl.registration(
            userName = userName,
            userPassword = userPassword
        )
    }
}