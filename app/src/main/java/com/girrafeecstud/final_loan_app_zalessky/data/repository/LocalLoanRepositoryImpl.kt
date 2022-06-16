package com.girrafeecstud.final_loan_app_zalessky.data.repository

import com.girrafeecstud.final_loan_app_zalessky.data.datasource.LocalLoanDataSourceImpl
import com.girrafeecstud.final_loan_app_zalessky.domain.entities.Loan
import javax.inject.Inject

class LocalLoanRepositoryImpl @Inject constructor(
    private val dataSource: LocalLoanDataSourceImpl
) {

    suspend fun fillLoans(loans: List<Loan>) {
        dataSource.fillLoansList(loans = loans)
    }

    suspend fun resetLoans() {
        dataSource.clearLoans()
    }

}