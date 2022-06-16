package com.girrafeecstud.final_loan_app_zalessky.di

import com.girrafeecstud.final_loan_app_zalessky.data.repository.RemoteLoanRepositoryImpl
import com.girrafeecstud.final_loan_app_zalessky.data.repository.LoginRepositoryImpl
import com.girrafeecstud.final_loan_app_zalessky.data.repository.RegistrationRepositoryImpl
import com.girrafeecstud.final_loan_app_zalessky.domain.usecase.*
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
    fun provideGetLoanConditionsUseCase(repository: RemoteLoanRepositoryImpl): GetLoanConditionsUseCase {
        return GetLoanConditionsUseCase(repository = repository)
    }

    @Provides
    @Singleton
    fun provideApplyLoanUseCase(repository: RemoteLoanRepositoryImpl): ApplyLoanUseCase {
        return ApplyLoanUseCase(repository = repository)
    }

    @Provides
    @Singleton
    fun provideGetLoansListUseCase(repository: RemoteLoanRepositoryImpl): GetLoansListUseCase {
        return GetLoansListUseCase(repository = repository)
    }

    @Provides
    @Singleton
    fun provideGetLoanByIdUseCase(repository: RemoteLoanRepositoryImpl): GetLoanByIdUseCase {
        return GetLoanByIdUseCase(repository = repository)
    }

}