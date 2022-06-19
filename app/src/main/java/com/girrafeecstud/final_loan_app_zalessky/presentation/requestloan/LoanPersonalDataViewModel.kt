package com.girrafeecstud.final_loan_app_zalessky.presentation.requestloan

import androidx.lifecycle.ViewModel
import com.girrafeecstud.final_loan_app_zalessky.data.repository.ValidatorsRepository
import javax.inject.Inject

class LoanPersonalDataViewModel @Inject constructor(
    private val validatorsRepository: ValidatorsRepository
) : ViewModel() {

    fun isFirstNameValid(firstName: String): Boolean {
        return validatorsRepository.isFirstNameValid(firstName = firstName)
    }

    fun isLastNameValid(lastName: String): Boolean {
        return validatorsRepository.isLastNameValid(lastName = lastName)
    }

    fun isPhoneNumberValid(phoneNumber: String): Boolean {
        return validatorsRepository.isPhoneNumberValid(phoneNumber = phoneNumber)
    }

}