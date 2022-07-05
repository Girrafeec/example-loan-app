package com.girrafeecstud.final_loan_app_zalessky.domain.usecase

import com.girrafeecstud.final_loan_app_zalessky.data.network.login.ApiResult
import com.girrafeecstud.final_loan_app_zalessky.domain.repository.LoanRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLoanByIdUseCase @Inject constructor(
    private val repository: LoanRepository
) {
    suspend operator fun invoke(bearerToken: String?, loanId: Long): Flow<ApiResult<Any>> {
        return repository.getLoanById(
            bearerToken = bearerToken,
            loanId = loanId
        )
    }
}