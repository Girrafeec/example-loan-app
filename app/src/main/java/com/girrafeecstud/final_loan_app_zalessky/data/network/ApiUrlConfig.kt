package com.girrafeecstud.final_loan_app_zalessky.data.network

class ApiUrlConfig {

    companion object {
        const val BASE_API_URL = "https://shiftlab.cft.ru:7777/"
        const val LOGIN_API_URL = "login"
        const val REGISTRATION_API_URL = "registration"
        const val MAIN_LOANS_API_URL = "loans"
        const val LOANS_CONDITIONS_API_URL = "$MAIN_LOANS_API_URL/conditions"
        const val LOANS_LIST_API_URL = "$MAIN_LOANS_API_URL/list"
    }

}