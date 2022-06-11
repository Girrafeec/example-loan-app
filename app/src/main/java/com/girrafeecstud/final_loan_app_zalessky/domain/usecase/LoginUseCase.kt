package com.girrafeecstud.final_loan_app_zalessky.domain.usecase

import com.girrafeecstud.final_loan_app_zalessky.data.network.login.ApiResult
import com.girrafeecstud.final_loan_app_zalessky.data.repository.LoginRepositoryImpl
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: LoginRepositoryImpl
) {

    suspend operator fun invoke(userName: String, userPassword: String): Flow<ApiResult<Any>> {
        return repository.login(userName = userName, userPassword = userPassword)
    }

}