package com.girrafeecstud.final_loan_app_zalessky.di

import com.girrafeecstud.final_loan_app_zalessky.data.convertion.LocalDateTimeConverter
import com.girrafeecstud.final_loan_app_zalessky.data.datasource.*
import com.girrafeecstud.final_loan_app_zalessky.data.repository.*
import com.girrafeecstud.final_loan_app_zalessky.data.validation.InputValidators
import com.girrafeecstud.final_loan_app_zalessky.domain.repository.LoanRepository
import com.girrafeecstud.final_loan_app_zalessky.domain.repository.LoginRepository
import com.girrafeecstud.final_loan_app_zalessky.domain.repository.RegistrationRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [RepositoryModule.RepositoryBindModule::class])
class RepositoryModule {

    @Provides
    @Singleton
    fun provideLoginSharedPreferencesRepositoryImpl(
        loginSharedPreferencesDataSource: LoginSharedPreferencesDataSource
    ): LoginSharedPreferencesRepository {
        return LoginSharedPreferencesRepository(
            loginSharedPreferencesDataSource = loginSharedPreferencesDataSource
        )
    }

    @Provides
    @Singleton
    fun provideValidatorsRepository(inputValidators: InputValidators): ValidatorsRepository {
        return ValidatorsRepository(inputValidators = inputValidators)
    }

    @Provides
    @Singleton
    fun provideLocalDateTimeConverterRepository(
        localDateTimeConverter: LocalDateTimeConverter
    ): LocalDateTimeConverterRepository {
        return LocalDateTimeConverterRepository(localDateTimeConverter = localDateTimeConverter)
    }

    @Module
    interface RepositoryBindModule {

        @Binds
        @Singleton
        fun bindLoanRepositoryImpl(impl: LoanRepositoryImpl): LoanRepository

        @Binds
        @Singleton
        fun bindRegistrationRepositoryImpl(impl: RegistrationRepositoryImpl): RegistrationRepository

        @Binds
        @Singleton
        fun bindLoginRepositoryImpl(impl: LoginRepositoryImpl): LoginRepository
    }

}