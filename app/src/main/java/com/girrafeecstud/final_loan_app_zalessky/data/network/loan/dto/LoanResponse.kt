package com.girrafeecstud.final_loan_app_zalessky.data.network.loan.dto

import com.google.gson.annotations.SerializedName

data class LoanResponse(
    @SerializedName("amount")
    val loanAmount: Double,
    @SerializedName("date")
    val loanIssueDate: String,
    @SerializedName("firstName")
    val borrowerFirstName: String,
    @SerializedName("id")
    val loanId: Long,
    @SerializedName("lastName")
    val borrowerLastName: String,
    @SerializedName("percent")
    val loanPercent: Double,
    @SerializedName("period")
    val loanPeriod: Int,
    @SerializedName("phoneNumber")
    val borrowerPhoneNumber: String,
    @SerializedName("state")
    val loanState: String
)