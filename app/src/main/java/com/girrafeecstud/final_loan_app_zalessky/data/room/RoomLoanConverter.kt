package com.girrafeecstud.final_loan_app_zalessky.data.room

import com.girrafeecstud.final_loan_app_zalessky.data.room.model.RoomLoan
import com.girrafeecstud.final_loan_app_zalessky.domain.entities.Loan
import com.girrafeecstud.final_loan_app_zalessky.domain.entities.LoanState
import javax.inject.Inject

class RoomLoanConverter @Inject constructor(
) {

    fun getRoomLoanFromLoan(loan: Loan): RoomLoan {

        return RoomLoan(
            loanId = loan.loanId,
            loanIssueDate = loan.loanIssueDate,
            loanAmount = loan.loanAmount,
            borrowerFirstName = loan.borrowerFirstName,
            borrowerLastName = loan.borrowerLastName,
            loanPercent = loan.loanPercent,
            loanPeriod = loan.loanPeriod,
            borrowerPhoneNumber = loan.borrowerPhoneNumber,
            loanState = loan.loanState.name
        )
    }

    fun getLoanFromRoomLoan(roomLoan: RoomLoan): Loan {

        val loanState: LoanState = when (roomLoan.loanState) {
            LoanState.APPROVED.name -> LoanState.APPROVED
            LoanState.REGISTERED.name -> LoanState.REGISTERED
            LoanState.REJECTED.name -> LoanState.REJECTED
            else -> LoanState.DEFAULT
        }

        return Loan(
            loanId = roomLoan.loanId,
            loanIssueDate = roomLoan.loanIssueDate,
            loanAmount = roomLoan.loanAmount,
            borrowerFirstName = roomLoan.borrowerFirstName,
            borrowerLastName = roomLoan.borrowerLastName,
            loanPercent = roomLoan.loanPercent,
            loanPeriod = roomLoan.loanPeriod,
            borrowerPhoneNumber = roomLoan.borrowerPhoneNumber,
            loanState = loanState
        )
    }

}