package com.girrafeecstud.final_loan_app_zalessky.data.convertion

import com.girrafeecstud.final_loan_app_zalessky.data.room.RoomLoanConverter
import com.girrafeecstud.final_loan_app_zalessky.data.room.model.RoomLoan
import com.girrafeecstud.final_loan_app_zalessky.domain.entities.Loan
import com.girrafeecstud.final_loan_app_zalessky.domain.entities.LoanState
import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.Before

class RoomLoanConverterUnitTest {

    private lateinit var roomLoanConverter: RoomLoanConverter

    @Before
    fun setUp() {
        roomLoanConverter = RoomLoanConverter()
    }

    @Test
    fun `WHEN put Loan EXPECT RoomLoan with same values` () {
        val loan = Loan(
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

        val expectedResult = RoomLoan(
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

        val actualResult: RoomLoan = roomLoanConverter.getRoomLoanFromLoan(loan = loan)

        assertEquals(expectedResult, actualResult)
    }

    @Test
    fun `WHEN put RoomLoan EXPECT Loan with same values` () {
        val roomLoan = RoomLoan(
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

        val actualResult: Loan = roomLoanConverter.getLoanFromRoomLoan(roomLoan = roomLoan)

        assertEquals(expectedResult, actualResult)
    }

}