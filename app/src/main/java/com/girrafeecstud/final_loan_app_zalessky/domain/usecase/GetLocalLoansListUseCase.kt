package com.girrafeecstud.final_loan_app_zalessky.domain.usecase

import com.girrafeecstud.final_loan_app_zalessky.data.repository.LocalLoanRepositoryImpl
import com.girrafeecstud.final_loan_app_zalessky.domain.entities.Loan
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLocalLoansListUseCase @Inject constructor(
    private val repository: LocalLoanRepositoryImpl
) {

    suspend operator fun invoke(): Flow<List<Loan>> {
        return repository.getLoansList()
    }

}