package com.girrafeecstud.final_loan_app_zalessky.di

import android.content.Context
import com.girrafeecstud.final_loan_app_zalessky.data.datasource.LoanDataSourceImpl
import com.girrafeecstud.final_loan_app_zalessky.data.datasource.LoginDataSourceImpl
import com.girrafeecstud.final_loan_app_zalessky.data.datasource.LoginSharedPreferencesDataSourceImpl
import com.girrafeecstud.final_loan_app_zalessky.data.datasource.RegistrationDataSourceImpl
import com.girrafeecstud.final_loan_app_zalessky.data.network.loan.api.LoanApi
import com.girrafeecstud.final_loan_app_zalessky.data.network.login.api.LoginApi
import com.girrafeecstud.final_loan_app_zalessky.data.network.registration.api.RegistrationApi
import com.girrafeecstud.final_loan_app_zalessky.data.repository.*
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {

    // TODO перенести datasource в отдельный модуль, а репозитории - в отдельный

    @Provides
    @Singleton
    fun provideLoginDataSourceImpl(loginApi: LoginApi): LoginDataSourceImpl {
        return LoginDataSourceImpl(loginApi = loginApi)
    }

    @Provides
    @Singleton
    fun provideRegistrationDataSourceImpl(
        registrationApi: RegistrationApi
    ): RegistrationDataSourceImpl {
        return RegistrationDataSourceImpl(registrationApi = registrationApi)
    }

    @Provides
    @Singleton
    fun provideLoanDataSourceImpl(
        loanApi: LoanApi
    ): LoanDataSourceImpl {
        return LoanDataSourceImpl(loanApi = loanApi)
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
    fun provideLoanRepositoryImpl(loanDataSourceImpl: LoanDataSourceImpl): LoanRepositoryImpl {
        return LoanRepositoryImpl(dataSource = loanDataSourceImpl)
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

}