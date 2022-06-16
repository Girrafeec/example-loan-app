package com.girrafeecstud.final_loan_app_zalessky.data.room.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.girrafeecstud.final_loan_app_zalessky.data.room.RoomConfig

@Entity(tableName = RoomConfig.LOANS_TABLE_NAME)
data class RoomLoan(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val loanId: Long,
    @ColumnInfo(name="date")
    val loanIssueDate: String,
    @ColumnInfo(name = "amount")
    val loanAmount: Double,
    @ColumnInfo(name = "firstName")
    val borrowerFirstName: String,
    @ColumnInfo(name = "lastName")
    val borrowerLastName: String,
    @ColumnInfo(name = "percent")
    val loanPercent: Double,
    @ColumnInfo(name = "period")
    val loanPeriod: Int,
    @ColumnInfo(name = "phoneNumber")
    val borrowerPhoneNumber: String,
    @ColumnInfo(name = "state")
    val loanState: String
)
