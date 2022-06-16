package com.girrafeecstud.final_loan_app_zalessky.domain.usecase

import com.girrafeecstud.final_loan_app_zalessky.data.network.login.ApiResult
import com.girrafeecstud.final_loan_app_zalessky.data.repository.RemoteLoanRepositoryImpl
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRemoteLoanByIdUseCase @Inject constructor(
    private val repository: RemoteLoanRepositoryImpl
) {
    suspend operator fun invoke(bearerToken: String?, loanId: Long): Flow<ApiResult<Any>> {
        return repository.getLoanById(
            bearerToken = bearerToken,
            loanId = loanId
        )
    }
}