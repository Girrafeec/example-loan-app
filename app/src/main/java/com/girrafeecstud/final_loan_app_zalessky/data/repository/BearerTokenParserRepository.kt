package com.girrafeecstud.final_loan_app_zalessky.data.repository

import com.girrafeecstud.final_loan_app_zalessky.data.BearerTokenParser
import javax.inject.Inject

class BearerTokenParserRepository @Inject constructor(
    private val bearerTokenParser: BearerTokenParser
) {

    suspend fun parseBearerToken(userBearerToken: String): String {
        return bearerTokenParser.parseBearerToken(userBearerToken = userBearerToken)
    }

}