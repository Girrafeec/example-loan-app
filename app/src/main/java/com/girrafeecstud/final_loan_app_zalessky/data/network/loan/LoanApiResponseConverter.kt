package com.girrafeecstud.final_loan_app_zalessky.data.network.loan

import org.threeten.bp.LocalDateTime
import com.girrafeecstud.final_loan_app_zalessky.data.network.loan.dto.LoanResponse
import com.girrafeecstud.final_loan_app_zalessky.domain.entities.Loan
import com.girrafeecstud.final_loan_app_zalessky.domain.entities.LoanState
import javax.inject.Inject

class LoanApiResponseConverter @Inject constructor() {

    // TODO придумать что-то с датой
    fun getLoanFromLoanResponse(loanResponse: LoanResponse): Loan {

        val loanState: LoanState = when (loanResponse.loanState) {
            LoanState.APPROVED.name -> LoanState.APPROVED
            LoanState.REGISTERED.name -> LoanState.REGISTERED
            LoanState.REJECTED.name -> LoanState.REJECTED
            else -> LoanState.DEFAULT
        }

        val loanIssueDate = LocalDateTime.parse(loanResponse.loanIssueDate)

        return Loan(
            loanAmount = loanResponse.loanAmount,
            loanIssueDate = loanIssueDate,
            borrowerFirstName = loanResponse.borrowerFirstName,
            loanId = loanResponse.loanId,
            borrowerLastName = loanResponse.borrowerLastName,
            loanPercent = loanResponse.loanPercent,
            loanPeriod = loanResponse.loanPeriod,
            borrowerPhoneNumber = loanResponse.borrowerPhoneNumber,
            loanState = loanState
        )
    }

}