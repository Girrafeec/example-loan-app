package com.girrafeecstud.final_loan_app_zalessky.di

import com.girrafeecstud.final_loan_app_zalessky.data.convertion.LocalDateTimeConverter
import com.girrafeecstud.final_loan_app_zalessky.data.validation.InputValidators
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