package com.girrafeecstud.final_loan_app_zalessky.di

import com.girrafeecstud.final_loan_app_zalessky.data.convertion.LocalDateTimeConverter
import com.girrafeecstud.final_loan_app_zalessky.data.datasource.*
import com.girrafeecstud.final_loan_app_zalessky.data.repository.*
import com.girrafeecstud.final_loan_app_zalessky.data.validation.InputValidators
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideLoginRepositoryImpl(dataSource: LoginDataSourceImpl): LoginRepositoryImpl {
        return LoginRepositoryImpl(dataSource = dataSource)
    }

    @Provides
    @Singleton
    fun provideRegistrationRepositoryImpl(dataSource: RegistrationDataSourceImpl): RegistrationRepositoryImpl {
        return RegistrationRepositoryImpl(dataSource = dataSource)
    }

    @Provides
    @Singleton
    fun provideLoanRepositoryImpl(
        remoteLoanDataSourceImpl: RemoteLoanDataSourceImpl,
        localLoanDataSourceImpl: LocalLoanDataSourceImpl
    ): LoanRepositoryImpl {
        return LoanRepositoryImpl(
            remoteDataSource = remoteLoanDataSourceImpl,
            localDataSource = localLoanDataSourceImpl
        )
    }

    @Provides
    @Singleton
    fun provideLoginSharedPreferencesRepositoryImpl(
        loginSharedPreferencesDataSourceImpl: LoginSharedPreferencesDataSourceImpl
    ): LoginSharedPreferencesRepositoryImpl {
        return LoginSharedPreferencesRepositoryImpl(
            loginSharedPreferencesDataSourceImpl = loginSharedPreferencesDataSourceImpl
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

}