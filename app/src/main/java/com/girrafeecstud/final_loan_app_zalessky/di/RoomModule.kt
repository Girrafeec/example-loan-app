package com.girrafeecstud.final_loan_app_zalessky.di

import android.content.Context
import androidx.room.Room
import com.girrafeecstud.final_loan_app_zalessky.data.room.MainDatabase
import com.girrafeecstud.final_loan_app_zalessky.data.room.RoomConfig
import com.girrafeecstud.final_loan_app_zalessky.data.room.RoomLoanConverter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [AppModule::class])
class RoomModule {

    @Provides
    @Singleton
    fun provideMainDatabase(context: Context): MainDatabase {
        return Room.databaseBuilder(
            context,
            MainDatabase::class.java,
            RoomConfig.ROOM_DATABASE_NAME,
        ).allowMainThreadQueries().build()
    }

    @Provides
    @Singleton
    fun provideRoomLoanConverter(
    ): RoomLoanConverter {
        return RoomLoanConverter()
    }

}