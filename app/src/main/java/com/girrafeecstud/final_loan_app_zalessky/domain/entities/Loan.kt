package com.girrafeecstud.final_loan_app_zalessky.domain.entities

data class Loan(
    val loanAmount: Double,
    val loanIssueDate: String,
    val borrowerFirstName: String,
    val loanId: Long,
    val borrowerLastName: String,
    val loanPercent: Double,
    val loanPeriod: Int,
    val borrowerPhoneNumber: String,
    val loanState: LoanState
)
