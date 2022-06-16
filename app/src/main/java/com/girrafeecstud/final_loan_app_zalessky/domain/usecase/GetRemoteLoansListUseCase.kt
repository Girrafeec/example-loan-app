package com.girrafeecstud.final_loan_app_zalessky.domain.usecase

import com.girrafeecstud.final_loan_app_zalessky.data.network.login.ApiResult
import com.girrafeecstud.final_loan_app_zalessky.data.repository.RemoteLoanRepositoryImpl
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRemoteLoansListUseCase @Inject constructor(
    private val repository: RemoteLoanRepositoryImpl
) {
    suspend operator fun invoke(bearerToken: String?): Flow<ApiResult<Any>> {
        return repository.getLoansList(bearerToken = bearerToken)
    }
}