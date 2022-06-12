package com.girrafeecstud.final_loan_app_zalessky.di

import com.girrafeecstud.final_loan_app_zalessky.data.network.loan.api.LoanApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
class LoanModule {

    @Provides
    @Singleton
    fun provideLoginApi(retrofit: Retrofit): LoanApi {
        return retrofit.create(LoanApi::class.java)
    }

}