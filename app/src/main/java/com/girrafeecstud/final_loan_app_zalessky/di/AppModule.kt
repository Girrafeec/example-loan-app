package com.girrafeecstud.final_loan_app_zalessky.di

import androidx.lifecycle.ViewModel
import com.girrafeecstud.final_loan_app_zalessky.di.annotation.ViewModelKey
import com.girrafeecstud.final_loan_app_zalessky.presentation.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface AppModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    fun bindMainViewModel(impl: MainViewModel): ViewModel

}