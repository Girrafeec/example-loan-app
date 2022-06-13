package com.girrafeecstud.final_loan_app_zalessky.data.network.loan.dto

import com.google.gson.annotations.SerializedName

data class LoanConditionsResponse(
    @SerializedName("maxAmount")
    val maxLoanAmount: Double,
    @SerializedName("percent")
    val loanPercent: Double,
    @SerializedName("period")
    val loanPeriod: Int
)
