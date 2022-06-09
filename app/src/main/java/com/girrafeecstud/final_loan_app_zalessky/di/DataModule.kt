package com.girrafeecstud.final_loan_app_zalessky.di

import com.girrafeecstud.final_loan_app_zalessky.data.network.login.api.LoginApi
import com.girrafeecstud.final_loan_app_zalessky.data.repository.LoginRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {

    @Provides
    @Singleton
    fun provideLoginRepositoryImpl(loginApi: LoginApi): LoginRepositoryImpl {
        return LoginRepositoryImpl(loginApi = loginApi)
    }

}