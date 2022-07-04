package com.girrafeecstud.final_loan_app_zalessky.data.repository

import com.girrafeecstud.final_loan_app_zalessky.data.datasource.LoginSharedPreferencesDataSourceImpl
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.junit.Assert.assertEquals

class LoginSharedPreferencesRepositoryUnitTest {

    companion object {
        private const val USER_AUTH_TOKEN = "Bearer eyJ0eXAiOiJKV1QiLCJhbGcbmcU2HhD"
        private const val USER_NAME = "string"
    }

    private lateinit var loginSharedPreferencesRepositoryImpl: LoginSharedPreferencesRepositoryImpl

    private val loginSharedPreferencesDataSourceImpl: LoginSharedPreferencesDataSourceImpl = mock()

    @Before
    fun setUp() {
        loginSharedPreferencesRepositoryImpl =
            LoginSharedPreferencesRepositoryImpl(loginSharedPreferencesDataSourceImpl = loginSharedPreferencesDataSourceImpl)
    }

    @Test
    fun `WHEN pass user authorized status EXPECT true result` () {
        runBlocking {
            val expectedResult = true

            whenever(loginSharedPreferencesDataSourceImpl.getUserAuthorizedStatus()).thenReturn(true)

            val actualResult = loginSharedPreferencesRepositoryImpl.getUserAuthorizedStatus()

            assertEquals(expectedResult, actualResult)
        }
    }

    @Test
    fun `WHEN pass user unauthorized status EXPECT false result` () {
        runBlocking {

            val expectedResult = false

            whenever(loginSharedPreferencesDataSourceImpl.getUserAuthorizedStatus()).thenReturn(false)

            val actualResult = loginSharedPreferencesRepositoryImpl.getUserAuthorizedStatus()

            assertEquals(expectedResult, actualResult)
        }
    }

    @Test
    fun `WHEN pass bearer token EXPECT the same token` () {
        runBlocking {

            val expectedResult = USER_AUTH_TOKEN

            whenever(loginSharedPreferencesDataSourceImpl.getUserBearerToken()).thenReturn(USER_AUTH_TOKEN)

            val actualResult = loginSharedPreferencesRepositoryImpl.getUserBearerToken()

            assertEquals(expectedResult, actualResult)
        }
    }

    @Test
    fun `WHEN clean bearer token EXPECT empty string` () {
        runBlocking {

            val expectedResult = ""

            whenever(loginSharedPreferencesDataSourceImpl.getUserBearerToken()).thenReturn("")

            val actualResult = loginSharedPreferencesRepositoryImpl.getUserBearerToken()

            assertEquals(expectedResult, actualResult)
        }
    }

    @Test
    fun `WHEN pass user name EXPECT the same user name` () {
        runBlocking {
            val expectedResult = USER_NAME

            whenever(loginSharedPreferencesDataSourceImpl.getUserName()).thenReturn(USER_NAME)

            val actualResult = loginSharedPreferencesDataSourceImpl.getUserName()

            assertEquals(expectedResult, actualResult)
        }
    }

    @Test
    fun `WHEN clear user name EXPECT empty string` () {
        runBlocking {
            val expectedResult = ""

            whenever(loginSharedPreferencesDataSourceImpl.getUserName()).thenReturn("")

            val actualResult = loginSharedPreferencesRepositoryImpl.getUserName()

            assertEquals(expectedResult, actualResult)
        }
    }

}