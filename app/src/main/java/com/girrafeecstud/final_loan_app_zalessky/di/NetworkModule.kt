package com.girrafeecstud.final_loan_app_zalessky.di

import com.girrafeecstud.final_loan_app_zalessky.data.network.ApiUrlConfig
import com.girrafeecstud.final_loan_app_zalessky.di.annotation.BaseApiUrl
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @BaseApiUrl
    fun provideBaseApiUrl() = ApiUrlConfig.BASE_API_URL

    @Provides
    fun provideGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Provides
    fun provideScalarConverterFactory(): ScalarsConverterFactory {
        return ScalarsConverterFactory.create()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        scalarsConverterFactory: ScalarsConverterFactory,
        gsonConverterFactory: GsonConverterFactory,
        @BaseApiUrl baseApiUrl: String
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(baseApiUrl)
            .addConverterFactory(scalarsConverterFactory)
            .addConverterFactory(gsonConverterFactory)
            .build()

}