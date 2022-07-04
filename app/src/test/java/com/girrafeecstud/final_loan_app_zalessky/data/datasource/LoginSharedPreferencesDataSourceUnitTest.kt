package com.girrafeecstud.final_loan_app_zalessky.data.datasource

import com.github.ivanshafran.sharedpreferencesmock.SPMockBuilder
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertEquals

class LoginSharedPreferencesDataSourceUnitTest {

    companion object {
        private const val USER_AUTH_TOKEN = "Bearer eyJ0eXAiOiJKV1QiLCJhbGcbmcU2HhD"
        private const val USER_NAME = "string"
    }

    private lateinit var sharedPreferencesSPMockBuilder: SPMockBuilder

    private lateinit var loginSharedPreferencesDataSourceImpl: LoginSharedPreferencesDataSourceImpl

    @Before
    fun setUp() {
        sharedPreferencesSPMockBuilder = SPMockBuilder()
        loginSharedPreferencesDataSourceImpl  =
            LoginSharedPreferencesDataSourceImpl(
                sharedPreferencesSPMockBuilder.createContext()
            )
    }

    @Test
    fun `WHEN pass user authorized status EXPECT true result` () {
        runBlocking {

            val expectedResult = true

            loginSharedPreferencesDataSourceImpl.setUserAuthorized()

            val actualResult = loginSharedPreferencesDataSourceImpl.getUserAuthorizedStatus()

           assertEquals(expectedResult, actualResult)
        }
    }

    @Test
    fun `WHEN pass user unauthorized status EXPECT false result` () {
        runBlocking {

            val expectedResult = false

            loginSharedPreferencesDataSourceImpl.setUserUnauthorized()

            val actualResult = loginSharedPreferencesDataSourceImpl.getUserAuthorizedStatus()

            assertEquals(expectedResult, actualResult)
        }
    }

    @Test
    fun `WHEN pass bearer token EXPECT the same token` () {
        runBlocking {

            val expectedResult = USER_AUTH_TOKEN

            loginSharedPreferencesDataSourceImpl.setUserBearerToken(userBearerToken = USER_AUTH_TOKEN)

            val actualResult = loginSharedPreferencesDataSourceImpl.getUserBearerToken()

            assertEquals(expectedResult, actualResult)
        }
    }

    @Test
    fun `WHEN clean bearer token EXPECT empty string` () {
        runBlocking {

            val expectedResult = ""

            loginSharedPreferencesDataSourceImpl.clearUserBearerToken()

            val actualResult = loginSharedPreferencesDataSourceImpl.getUserBearerToken()

            assertEquals(expectedResult, actualResult)
        }
    }

    @Test
    fun `WHEN pass user name EXPECT the same user name` () {
        runBlocking {
            val expectedResult = USER_NAME

            loginSharedPreferencesDataSourceImpl.setUserName(userName = USER_NAME)

            val actualResult = loginSharedPreferencesDataSourceImpl.getUserName()

            assertEquals(expectedResult, actualResult)
        }
    }

    @Test
    fun `WHEN clear user name EXPECT empty string` () {
        runBlocking {
            val expectedResult = ""

            loginSharedPreferencesDataSourceImpl.clearUserName()

            val actualResult = loginSharedPreferencesDataSourceImpl.getUserName()

            assertEquals(expectedResult, actualResult)
        }
    }

}