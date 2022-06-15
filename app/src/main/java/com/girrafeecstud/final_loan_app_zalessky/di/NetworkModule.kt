package com.girrafeecstud.final_loan_app_zalessky.di

import android.content.Context
import com.girrafeecstud.final_loan_app_zalessky.data.network.ApiErrorConverter
import com.girrafeecstud.final_loan_app_zalessky.data.network.ApiUrlConfig
import com.girrafeecstud.final_loan_app_zalessky.data.network.NetworkConnectionInterceptor
import com.girrafeecstud.final_loan_app_zalessky.data.network.loan.LoanApiResponseConverter
import com.girrafeecstud.final_loan_app_zalessky.data.network.registration.RegistrationApiResponseConverter
import com.girrafeecstud.final_loan_app_zalessky.di.annotation.BaseApiUrl
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
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
    fun provideNetworkConnectionInterceptor(context: Context): NetworkConnectionInterceptor {
        return NetworkConnectionInterceptor(context = context)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        networkConnectionInterceptor: NetworkConnectionInterceptor
    ): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(networkConnectionInterceptor)
            .build()

    @Provides
    @Singleton
    fun provideRetrofit(
        scalarsConverterFactory: ScalarsConverterFactory,
        gsonConverterFactory: GsonConverterFactory,
        okHttpClient: OkHttpClient,
        @BaseApiUrl baseApiUrl: String
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(baseApiUrl)
            .addConverterFactory(scalarsConverterFactory)
            .addConverterFactory(gsonConverterFactory)
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun provideApiErrorConverter(): ApiErrorConverter {
        return ApiErrorConverter()
    }

    @Provides
    @Singleton
    fun provideLoanApiResponseConverter(): LoanApiResponseConverter {
        return LoanApiResponseConverter()
    }

    @Provides
    @Singleton
    fun provideRegistrationApiResponseConverter(): RegistrationApiResponseConverter {
        return RegistrationApiResponseConverter()
    }

}