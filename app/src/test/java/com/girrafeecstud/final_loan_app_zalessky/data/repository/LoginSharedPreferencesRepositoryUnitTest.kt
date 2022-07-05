package com.girrafeecstud.final_loan_app_zalessky.data.repository

import com.girrafeecstud.final_loan_app_zalessky.data.datasource.LoginSharedPreferencesDataSource
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

    private lateinit var loginSharedPreferencesRepository: LoginSharedPreferencesRepository

    private val loginSharedPreferencesDataSource: LoginSharedPreferencesDataSource = mock()

    @Before
    fun setUp() {
        loginSharedPreferencesRepository =
            LoginSharedPreferencesRepository(loginSharedPreferencesDataSourceImpl = loginSharedPreferencesDataSource)
    }

    @Test
    fun `WHEN pass user authorized status EXPECT true result` () {
        runBlocking {
            val expectedResult = true

            whenever(loginSharedPreferencesDataSource.getUserAuthorizedStatus()).thenReturn(true)

            val actualResult = loginSharedPreferencesRepository.getUserAuthorizedStatus()

            assertEquals(expectedResult, actualResult)
        }
    }

    @Test
    fun `WHEN pass user unauthorized status EXPECT false result` () {
        runBlocking {

            val expectedResult = false

            whenever(loginSharedPreferencesDataSource.getUserAuthorizedStatus()).thenReturn(false)

            val actualResult = loginSharedPreferencesRepository.getUserAuthorizedStatus()

            assertEquals(expectedResult, actualResult)
        }
    }

    @Test
    fun `WHEN pass bearer token EXPECT the same token` () {
        runBlocking {

            val expectedResult = USER_AUTH_TOKEN

            whenever(loginSharedPreferencesDataSource.getUserBearerToken()).thenReturn(USER_AUTH_TOKEN)

            val actualResult = loginSharedPreferencesRepository.getUserBearerToken()

            assertEquals(expectedResult, actualResult)
        }
    }

    @Test
    fun `WHEN clean bearer token EXPECT empty string` () {
        runBlocking {

            val expectedResult = ""

            whenever(loginSharedPreferencesDataSource.getUserBearerToken()).thenReturn("")

            val actualResult = loginSharedPreferencesRepository.getUserBearerToken()

            assertEquals(expectedResult, actualResult)
        }
    }

    @Test
    fun `WHEN pass user name EXPECT the same user name` () {
        runBlocking {
            val expectedResult = USER_NAME

            whenever(loginSharedPreferencesDataSource.getUserName()).thenReturn(USER_NAME)

            val actualResult = loginSharedPreferencesDataSource.getUserName()

            assertEquals(expectedResult, actualResult)
        }
    }

    @Test
    fun `WHEN clear user name EXPECT empty string` () {
        runBlocking {
            val expectedResult = ""

            whenever(loginSharedPreferencesDataSource.getUserName()).thenReturn("")

            val actualResult = loginSharedPreferencesRepository.getUserName()

            assertEquals(expectedResult, actualResult)
        }
    }

}