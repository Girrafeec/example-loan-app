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

    // TODO перенести datasource в отдельный модуль, а репозитории - в отдельный

    @Provides
    @Singleton
    fun provideLoginDataSourceImpl(
        loginApi: LoginApi,
        apiErrorConverter: ApiErrorConverter
    ): LoginDataSourceImpl {
        return LoginDataSourceImpl(
            loginApi = loginApi,
            apiErrorConverter = apiErrorConverter
        )
    }

    @Provides
    @Singleton
    fun provideRegistrationDataSourceImpl(
        registrationApi: RegistrationApi,
        registrationApiResponseConverter: RegistrationApiResponseConverter,
        apiErrorConverter: ApiErrorConverter
    ): RegistrationDataSourceImpl {
        return RegistrationDataSourceImpl(
            registrationApi = registrationApi,
            registrationApiResponseConverter = registrationApiResponseConverter,
            apiErrorConverter = apiErrorConverter
        )
    }

    @Provides
    @Singleton
    fun provideRemoteLoanDataSourceImpl(
        loanApi: LoanApi,
        loanApiResponseConverter: LoanApiResponseConverter,
        apiErrorConverter: ApiErrorConverter
    ): RemoteLoanDataSourceImpl {
        return RemoteLoanDataSourceImpl(
            loanApi = loanApi,
            loanApiResponseConverter = loanApiResponseConverter,
            apiErrorConverter = apiErrorConverter
        )
    }

    @Provides
    @Singleton
    fun provideLocalLoanDataSourceImpl(
        database: MainDatabase,
        roomLoanConverter: RoomLoanConverter
    ): LocalLoanDataSourceImpl {
        return LocalLoanDataSourceImpl(
            dataBase = database,
            roomLoanConverter = roomLoanConverter
        )
    }

    @Provides
    @Singleton
    fun provideLoginSharedPreferencesDataSourceImpl(
        context: Context
    ): LoginSharedPreferencesDataSourceImpl {
        return LoginSharedPreferencesDataSourceImpl(context = context)
    }

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
    fun provideRemoteLoanRepositoryImpl(
        remoteLoanDataSourceImpl: RemoteLoanDataSourceImpl,
        localLoanDataSourceImpl: LocalLoanDataSourceImpl
    ): RemoteLoanRepositoryImpl {
        return RemoteLoanRepositoryImpl(
            remoteDataSource = remoteLoanDataSourceImpl,
            localDataSource = localLoanDataSourceImpl
        )
    }

    @Provides
    @Singleton
    fun provideLocalLoanRepositoryImpl(localLoanDataSourceImpl: LocalLoanDataSourceImpl): LocalLoanRepositoryImpl {
        return LocalLoanRepositoryImpl(dataSource = localLoanDataSourceImpl)
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