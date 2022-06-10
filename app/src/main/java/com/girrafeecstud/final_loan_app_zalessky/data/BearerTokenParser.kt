package com.girrafeecstud.final_loan_app_zalessky.data

class BearerTokenParser {

    fun parseBearerToken(userBearerToken: String): String {
        return userBearerToken.replace("Bearer ", "")
    }

}