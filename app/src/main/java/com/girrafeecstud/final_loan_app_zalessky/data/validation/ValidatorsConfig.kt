package com.girrafeecstud.final_loan_app_zalessky.data.validation

import java.util.regex.Pattern

class ValidatorsConfig {

    companion object {
        val userNamePattern = Pattern.compile(
            "^[-a-zA-Z0-9а-яА-Я,.:;!?*@_]{3,20}$"
        )
        val userPasswordPattern = Pattern.compile(
            "^[-a-zA-Z0-9а-яА-Я,.:;!?*@_]{8,20}\$"
        )
        val firstAndLastNamePattern = Pattern.compile(
            "^[a-zA-Zа-яА-Я]{1,40}$"
        )
        val phoneNumberPattern = Pattern.compile(
            "^[+]?[0-9]{10,13}$"
        )
    }

}