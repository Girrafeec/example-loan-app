package com.girrafeecstud.final_loan_app_zalessky.data.network.loan.dto

import com.google.gson.annotations.SerializedName

data class LoanConditionsResponse(
    @SerializedName("maxAmount")
    var maxLoanAmount: Int? = null,
    @SerializedName("percent")
    var loanPerCent: Double? = null,
    @SerializedName("period")
    var loanPeriod: Int? = null
)
