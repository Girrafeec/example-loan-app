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

    private lateinit var loginSharedPreferencesDataSource: LoginSharedPreferencesDataSource

    @Before
    fun setUp() {
        sharedPreferencesSPMockBuilder = SPMockBuilder()
        loginSharedPreferencesDataSource  =
            LoginSharedPreferencesDataSource(
                sharedPreferencesSPMockBuilder.createContext()
            )
    }

    @Test
    fun `WHEN pass user authorized status EXPECT true result` () {
        runBlocking {

            val expectedResult = true

            loginSharedPreferencesDataSource.setUserAuthorized()

            val actualResult = loginSharedPreferencesDataSource.getUserAuthorizedStatus()

           assertEquals(expectedResult, actualResult)
        }
    }

    @Test
    fun `WHEN pass user unauthorized status EXPECT false result` () {
        runBlocking {

            val expectedResult = false

            loginSharedPreferencesDataSource.setUserUnauthorized()

            val actualResult = loginSharedPreferencesDataSource.getUserAuthorizedStatus()

            assertEquals(expectedResult, actualResult)
        }
    }

    @Test
    fun `WHEN pass bearer token EXPECT the same token` () {
        runBlocking {

            val expectedResult = USER_AUTH_TOKEN

            loginSharedPreferencesDataSource.setUserBearerToken(userBearerToken = USER_AUTH_TOKEN)

            val actualResult = loginSharedPreferencesDataSource.getUserBearerToken()

            assertEquals(expectedResult, actualResult)
        }
    }

    @Test
    fun `WHEN clean bearer token EXPECT empty string` () {
        runBlocking {

            val expectedResult = ""

            loginSharedPreferencesDataSource.clearUserBearerToken()

            val actualResult = loginSharedPreferencesDataSource.getUserBearerToken()

            assertEquals(expectedResult, actualResult)
        }
    }

    @Test
    fun `WHEN pass user name EXPECT the same user name` () {
        runBlocking {
            val expectedResult = USER_NAME

            loginSharedPreferencesDataSource.setUserName(userName = USER_NAME)

            val actualResult = loginSharedPreferencesDataSource.getUserName()

            assertEquals(expectedResult, actualResult)
        }
    }

    @Test
    fun `WHEN clear user name EXPECT empty string` () {
        runBlocking {
            val expectedResult = ""

            loginSharedPreferencesDataSource.clearUserName()

            val actualResult = loginSharedPreferencesDataSource.getUserName()

            assertEquals(expectedResult, actualResult)
        }
    }

}