package com.girrafeecstud.final_loan_app_zalessky.domain.entities

data class LoanRequest(
    val loanAmount: Double,
    val loanPeriod: Int,
    val loanPercent: Double,
    val borrowerFirstName: String,
    val borrowerLastName: String,
    val borrowerPhoneNumber: String
)
