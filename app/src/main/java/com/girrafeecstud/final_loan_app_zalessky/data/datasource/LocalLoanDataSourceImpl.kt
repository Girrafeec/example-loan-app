package com.girrafeecstud.final_loan_app_zalessky.data.datasource

import com.girrafeecstud.final_loan_app_zalessky.data.room.MainDatabase
import com.girrafeecstud.final_loan_app_zalessky.data.room.RoomLoanConverter
import com.girrafeecstud.final_loan_app_zalessky.data.room.model.RoomLoan
import com.girrafeecstud.final_loan_app_zalessky.domain.entities.Loan
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
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

    suspend fun clearLoans() {
        dataBase.getLoanDao().resetLoans()
    }

    suspend fun getLoansList(): Flow<List<Loan>> {
        return flow {
            val loans = mutableListOf<Loan>()
            dataBase.getLoanDao().getLoans().forEach { roomLoan ->
                loans += roomLoanConverter.getLoanFromRoomLoan(roomLoan = roomLoan)
            }
            emit(loans)
        }
    }

    suspend fun getLoanById(loanId: Long): Flow<Loan> {
        return flow {
            emit(roomLoanConverter
                .getLoanFromRoomLoan(
                roomLoan = dataBase.getLoanDao().getLoanById(loanId = loanId)
                )
            )
        }
    }

}