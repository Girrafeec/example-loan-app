package com.girrafeecstud.final_loan_app_zalessky.di

import android.content.Context
import com.girrafeecstud.final_loan_app_zalessky.data.convertion.LocalDateTimeConverter
import com.girrafeecstud.final_loan_app_zalessky.data.validation.InputValidators
import com.girrafeecstud.final_loan_app_zalessky.data.datasource.*
import com.girrafeecstud.final_loan_app_zalessky.data.network.ApiErrorConverter
import com.girrafeecstud.final_loan_app_zalessky.data.network.loan.LoanApiResponseConverter
import com.girrafeecstud.final_loan_app_zalessky.data.network.loan.api.LoanApi
import com.girrafeecstud.final_loan_app_zalessky.data.network.login.api.LoginApi
import com.girrafeecstud.final_loan_app_zalessky.data.network.registration.RegistrationApiResponseConverter
import com.girrafeecstud.final_loan_app_zalessky.data.network.registration.api.RegistrationApi
import com.girrafeecstud.final_loan_app_zalessky.data.repository.*
import com.girrafeecstud.final_loan_app_zalessky.data.room.MainDatabase
import com.girrafeecstud.final_loan_app_zalessky.data.room.RoomLoanConverter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {

    @Provides
    @Singleton
    fun provideInputValidators(): InputValidators {
        return InputValidators()
    }

    @Provides
    @Singleton
    fun provideLocalDateTimeConverter(): LocalDateTimeConverter {
        return LocalDateTimeConverter()
    }

}