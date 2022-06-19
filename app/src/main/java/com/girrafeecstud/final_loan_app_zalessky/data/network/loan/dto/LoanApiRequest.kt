package com.girrafeecstud.final_loan_app_zalessky.data.network.loan.dto

import com.google.gson.annotations.SerializedName

data class LoanApiRequest(
    @SerializedName("amount")
    val loanAmount: Double,
    @SerializedName("firstName")
    val borrowerFirstName: String,
    @SerializedName("lastName")
    val borrowerLastName: String,
    @SerializedName("percent")
    val loanPercent: Double,
    @SerializedName("period")
    val loanPeriod: Int,
    @SerializedName("phoneNumber")
    val borrowerPhoneNumber: String
)