package com.girrafeecstud.final_loan_app_zalessky.di

import android.content.Context
import com.girrafeecstud.final_loan_app_zalessky.data.datasource.*
import com.girrafeecstud.final_loan_app_zalessky.data.network.ApiErrorConverter
import com.girrafeecstud.final_loan_app_zalessky.data.network.loan.LoanApiResponseConverter
import com.girrafeecstud.final_loan_app_zalessky.data.network.loan.api.LoanApi
import com.girrafeecstud.final_loan_app_zalessky.data.network.login.api.LoginApi
import com.girrafeecstud.final_loan_app_zalessky.data.network.registration.RegistrationApiResponseConverter
import com.girrafeecstud.final_loan_app_zalessky.data.network.registration.api.RegistrationApi
import com.girrafeecstud.final_loan_app_zalessky.data.room.MainDatabase
import com.girrafeecstud.final_loan_app_zalessky.data.room.RoomLoanConverter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataSourceModule {

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

}