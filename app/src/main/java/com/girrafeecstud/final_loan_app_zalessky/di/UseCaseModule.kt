package com.girrafeecstud.final_loan_app_zalessky.di

import com.girrafeecstud.final_loan_app_zalessky.data.repository.LoanRepositoryImpl
import com.girrafeecstud.final_loan_app_zalessky.data.repository.LoginRepositoryImpl
import com.girrafeecstud.final_loan_app_zalessky.data.repository.RegistrationRepositoryImpl
import com.girrafeecstud.final_loan_app_zalessky.domain.usecase.GetLoanConditionsUseCase
import com.girrafeecstud.final_loan_app_zalessky.domain.usecase.LoginUseCase
import com.girrafeecstud.final_loan_app_zalessky.domain.usecase.RegistrationUseCase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class UseCaseModule {

    @Provides
    @Singleton
    fun provideLoginUseCase(repository: LoginRepositoryImpl): LoginUseCase {
        return LoginUseCase(repository = repository)
    }

    @Provides
    @Singleton
    fun provideRegistrationUseCase(repository: RegistrationRepositoryImpl): RegistrationUseCase {
        return RegistrationUseCase(repository = repository)
    }

    @Provides
    @Singleton
    fun provideGetLoanConditionsUseCase(repository: LoanRepositoryImpl): GetLoanConditionsUseCase {
        return GetLoanConditionsUseCase(repository = repository)
    }

}