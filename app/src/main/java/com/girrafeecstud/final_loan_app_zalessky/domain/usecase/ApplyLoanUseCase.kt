package com.girrafeecstud.final_loan_app_zalessky.domain.usecase

import com.girrafeecstud.final_loan_app_zalessky.data.network.login.ApiResult
import com.girrafeecstud.final_loan_app_zalessky.domain.repository.LoanRepository
import com.girrafeecstud.final_loan_app_zalessky.domain.entities.LoanRequest
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ApplyLoanUseCase @Inject constructor(
    private val repository: LoanRepository
) {
    suspend operator fun invoke(bearerToken: String?, loanRequest: LoanRequest): Flow<ApiResult<Any>> {
        return repository.applyLoan(
            bearerToken = bearerToken,
            loanRequest = loanRequest
        )
    }
}