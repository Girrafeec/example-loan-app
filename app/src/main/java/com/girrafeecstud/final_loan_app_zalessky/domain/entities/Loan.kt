package com.girrafeecstud.final_loan_app_zalessky.domain.entities

import java.time.LocalDateTime

data class Loan(
    val amount: Int,
    val date: LocalDateTime,
    val firstName: String,
    val id: Int,
    val lastName: Int,
    val percent: Int,
    val period: Int,
    val phoneNumber: String,
    val state: LoanState
)
