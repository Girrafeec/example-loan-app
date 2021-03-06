package com.girrafeecstud.final_loan_app_zalessky.di

import androidx.lifecycle.ViewModel
import com.girrafeecstud.final_loan_app_zalessky.di.annotation.ViewModelKey
import com.girrafeecstud.final_loan_app_zalessky.presentation.LoanItemViewModel
import com.girrafeecstud.final_loan_app_zalessky.presentation.LoansViewModel
import com.girrafeecstud.final_loan_app_zalessky.presentation.UserProfileViewModel
import com.girrafeecstud.final_loan_app_zalessky.presentation.authorization.AuthorizationViewModel
import com.girrafeecstud.final_loan_app_zalessky.presentation.authorization.LoginViewModel
import com.girrafeecstud.final_loan_app_zalessky.presentation.authorization.RegistrationViewModel
import com.girrafeecstud.final_loan_app_zalessky.presentation.requestloan.LoanConditionsViewModel
import com.girrafeecstud.final_loan_app_zalessky.presentation.requestloan.LoanConfirmationViewModel
import com.girrafeecstud.final_loan_app_zalessky.presentation.requestloan.LoanPersonalDataViewModel
import com.girrafeecstud.final_loan_app_zalessky.presentation.requestloan.LoanRequestActivityViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

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

    @Binds
    @IntoMap
    @ViewModelKey(LoanConditionsViewModel::class)
    fun bindLoanConditionsViewModel(impl: LoanConditionsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LoanPersonalDataViewModel::class)
    fun bindLoanPersonalDataViewModel(impl: LoanPersonalDataViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LoanConfirmationViewModel::class)
    fun bindLoanConfirmationViewModel(impl: LoanConfirmationViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LoanRequestActivityViewModel::class)
    fun bindLoanRequestActivityViewModel(impl: LoanRequestActivityViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LoansViewModel::class)
    fun bindLoansViewModel(impl: LoansViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LoanItemViewModel::class)
    fun bindLoanItemViewModel(impl: LoanItemViewModel): ViewModel
}