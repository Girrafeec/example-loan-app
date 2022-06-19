package com.girrafeecstud.final_loan_app_zalessky.di

import com.girrafeecstud.final_loan_app_zalessky.presentation.MainViewModelFactory
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    NetworkModule::class,
    LoginModule::class,
    RegistrationModule::class,
    LoanModule::class,
    RoomModule::class,
    DataModule::class,
    RepositoryModule::class,
    DataSourceModule::class,
    UseCaseModule::class,
    ViewModelModule::class,
    AppModule::class
])
interface AppComponent {

    fun mainViewModelFactory(): MainViewModelFactory

}