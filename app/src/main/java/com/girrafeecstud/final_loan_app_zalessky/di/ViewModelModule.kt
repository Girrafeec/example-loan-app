package com.girrafeecstud.final_loan_app_zalessky.di

import androidx.lifecycle.ViewModel
import com.girrafeecstud.final_loan_app_zalessky.di.annotation.ViewModelKey
import com.girrafeecstud.final_loan_app_zalessky.presentation.LoginViewModel
import com.girrafeecstud.final_loan_app_zalessky.presentation.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    fun bindMainViewModel(impl: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    fun bindMLoginViewModel(impl: LoginViewModel): ViewModel

}