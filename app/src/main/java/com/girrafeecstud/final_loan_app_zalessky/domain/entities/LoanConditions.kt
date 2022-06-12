package com.girrafeecstud.final_loan_app_zalessky.domain.entities

data class LoanConditions(
    val maxAmount: Int,
    val percent: Double,
    val period: Int
)
