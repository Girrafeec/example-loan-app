package com.girrafeecstud.final_loan_app_zalessky.domain.entities

import java.time.LocalDateTime

data class Loan(
    val loanAmount: Double,
    //val loanIssueDate: LocalDateTime,
    val loanIssueDate: String,
    val borrowerFirstName: String,
    val loanId: Long,
    val borrowerLastName: String,
    val loanPercent: Double,
    val loanPeriod: Int,
    val borrowerPhoneNumber: String,
    val loanState: LoanState
)
