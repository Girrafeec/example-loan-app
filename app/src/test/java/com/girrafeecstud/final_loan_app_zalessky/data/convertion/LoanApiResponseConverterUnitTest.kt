package com.girrafeecstud.final_loan_app_zalessky.data.convertion

import com.girrafeecstud.final_loan_app_zalessky.data.network.loan.LoanApiResponseConverter
import com.girrafeecstud.final_loan_app_zalessky.data.network.loan.dto.LoanResponse
import com.girrafeecstud.final_loan_app_zalessky.domain.entities.Loan
import com.girrafeecstud.final_loan_app_zalessky.domain.entities.LoanState
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertEquals

class LoanApiResponseConverterUnitTest {

    private lateinit var loanApiResponseConverter: LoanApiResponseConverter

    @Before
    fun setUp() {
        loanApiResponseConverter = LoanApiResponseConverter()
    }

    @Test
    fun `WHEN put LoanResponse EXPECT loan with same values` () {

        val loanResponse = LoanResponse(
            loanAmount = 10000.0,
            loanIssueDate = "2022-07-03T10:24:17.717+00:00",
            borrowerFirstName = "string",
            loanId = 975,
            borrowerLastName = "string",
            loanPercent = 12.72,
            loanPeriod = 75,
            borrowerPhoneNumber = "string",
            loanState = LoanState.REGISTERED.name
        )

        val expectedResult = Loan(
            loanAmount = 10000.0,
            loanIssueDate = "2022-07-03T10:24:17.717+00:00",
            borrowerFirstName = "string",
            loanId = 975,
            borrowerLastName = "string",
            loanPercent = 12.72,
            loanPeriod = 75,
            borrowerPhoneNumber = "string",
            loanState = LoanState.REGISTERED
        )

        val actualResult: Loan = loanApiResponseConverter.getLoanFromLoanResponse(loanResponse = loanResponse)

        assertEquals(expectedResult, actualResult)
    }

}