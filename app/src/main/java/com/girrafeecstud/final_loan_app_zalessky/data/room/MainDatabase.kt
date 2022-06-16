package com.girrafeecstud.final_loan_app_zalessky.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.girrafeecstud.final_loan_app_zalessky.data.room.dao.LoanDao
import com.girrafeecstud.final_loan_app_zalessky.data.room.model.RoomLoan

@Database(entities = [RoomLoan::class], version = RoomConfig.ROOM_DATABASE_VERSION)
abstract class MainDatabase: RoomDatabase() {

    abstract fun getLoanDao(): LoanDao

}