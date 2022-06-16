package com.girrafeecstud.final_loan_app_zalessky.data.datasource

import com.girrafeecstud.final_loan_app_zalessky.data.room.MainDatabase
import com.girrafeecstud.final_loan_app_zalessky.data.room.RoomLoanConverter
import com.girrafeecstud.final_loan_app_zalessky.data.room.model.RoomLoan
import com.girrafeecstud.final_loan_app_zalessky.domain.entities.Loan
import javax.inject.Inject

class LocalLoanDataSourceImpl @Inject constructor(
    private val dataBase: MainDatabase,
    private val roomLoanConverter: RoomLoanConverter
) {

    suspend fun fillLoansList(loans: List<Loan>) {

        val dataBaseLoans = mutableListOf<RoomLoan>()
        loans.forEach { loan ->
            dataBaseLoans += roomLoanConverter.getRoomLoanFromLoan(loan = loan)
        }

        dataBase.getLoanDao().insertLoans(loans = dataBaseLoans)
    }

    suspend fun getLoansList(): List<Loan> {
        val loans = mutableListOf<Loan>()
        dataBase.getLoanDao().getLoans().forEach { roomLoan ->
            loans += roomLoanConverter.getLoanFromRoomLoan(roomLoan = roomLoan)
        }
        return loans
    }

    suspend fun clearLoans() {
        dataBase.getLoanDao().resetLoans()
    }

}