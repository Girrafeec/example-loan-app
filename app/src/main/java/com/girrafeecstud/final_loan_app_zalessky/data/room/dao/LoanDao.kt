package com.girrafeecstud.final_loan_app_zalessky.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.girrafeecstud.final_loan_app_zalessky.data.room.RoomConfig
import com.girrafeecstud.final_loan_app_zalessky.data.room.model.RoomLoan

@Dao
interface LoanDao {
    @Insert
    fun insertLoans(loans: List<RoomLoan>)

    @Query("SELECT * FROM ${RoomConfig.LOANS_TABLE_NAME} ORDER BY id DESC")
    fun getLoans(): List<RoomLoan>

    @Query("SELECT * FROM ${RoomConfig.LOANS_TABLE_NAME} WHERE id = :loanId")
    fun getLoanById(loanId: Long): RoomLoan

    @Query("DELETE FROM ${RoomConfig.LOANS_TABLE_NAME}")
    fun resetLoans()
}