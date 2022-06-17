package com.girrafeecstud.final_loan_app_zalessky.data.validation

import javax.inject.Inject

class InputValidators @Inject constructor(

) {

    fun userNameIsCorrect(userName: String): Boolean {
        return ValidatorsConfig.userNamePattern.matcher(userName).matches()
    }

    fun passwordIsCorrect(password: String): Boolean {
        return ValidatorsConfig.userPasswordPattern.matcher(password).matches()
    }

    fun firstNameIsCorrect(firstName: String): Boolean {
        return ValidatorsConfig.firstAndLastNamePattern.matcher(firstName).matches()
    }

    fun lastNameIsCorrect(lastName: String): Boolean {
        return ValidatorsConfig.firstAndLastNamePattern.matcher(lastName).matches()
    }

    fun phoneNumberIsCorrect(phoneNumber: String): Boolean {
        return ValidatorsConfig.phoneNumberPattern.matcher(phoneNumber).matches()
    }

}