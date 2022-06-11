package com.girrafeecstud.final_loan_app_zalessky.di

import androidx.lifecycle.ViewModel
import com.girrafeecstud.final_loan_app_zalessky.di.annotation.ViewModelKey
import com.girrafeecstud.final_loan_app_zalessky.presentation.*
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
    fun bindLoginViewModel(impl: LoginViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RegistrationViewModel::class)
    fun bindRegistrationViewModel(impl: RegistrationViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(UserProfileViewModel::class)
    fun bindUserProfileViewModel(impl: UserProfileViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AuthorizationViewModel::class)
    fun bindAuthorizationViewModel(impl: AuthorizationViewModel): ViewModel

}