package com.girrafeecstud.final_loan_app_zalessky.data.repository

import com.girrafeecstud.final_loan_app_zalessky.data.validation.InputValidators
import javax.inject.Inject

class ValidatorsRepository @Inject constructor(
    private val inputValidators: InputValidators
) {

    fun isUserNameValid(userName: String): Boolean {
        return inputValidators.userNameIsCorrect(userName = userName)
    }

    fun isUserPasswordValid(password: String): Boolean {
        return inputValidators.passwordIsCorrect(password = password)
    }

    fun isFirstNameValid(firstName: String): Boolean {
        return inputValidators.firstNameIsCorrect(firstName = firstName)
    }

    fun isLastNameValid(lastName: String): Boolean {
        return inputValidators.lastNameIsCorrect(lastName = lastName)
    }

    fun isPhoneNumberValid(phoneNumber: String): Boolean {
        return inputValidators.phoneNumberIsCorrect(phoneNumber = phoneNumber)
    }

}