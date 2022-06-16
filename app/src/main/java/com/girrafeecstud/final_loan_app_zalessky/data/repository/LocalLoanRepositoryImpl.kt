package com.girrafeecstud.final_loan_app_zalessky.data.repository

import com.girrafeecstud.final_loan_app_zalessky.data.datasource.LocalLoanDataSourceImpl
import com.girrafeecstud.final_loan_app_zalessky.domain.entities.Loan
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalLoanRepositoryImpl @Inject constructor(
    private val dataSource: LocalLoanDataSourceImpl
) {

    suspend fun getLoansList(): Flow<List<Loan>> {
        return dataSource.getLoansList()
    }

    suspend fun fillLoans(loans: List<Loan>) {
        dataSource.fillLoansList(loans = loans)
    }

    suspend fun resetLoans() {
        dataSource.clearLoans()
    }

    suspend fun getLoanById(loanId: Long): Flow<Loan> {
        return dataSource.getLoanById(loanId = loanId)
    }

}