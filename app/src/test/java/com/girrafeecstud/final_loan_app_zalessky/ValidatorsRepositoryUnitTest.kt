package com.girrafeecstud.final_loan_app_zalessky

import com.girrafeecstud.final_loan_app_zalessky.data.validation.InputValidators
import com.girrafeecstud.final_loan_app_zalessky.data.repository.ValidatorsRepository
import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.Before

class ValidatorsRepositoryUnitTest {

    private lateinit var inputValidators: InputValidators

    private lateinit var validatorsRepository: ValidatorsRepository

    @Before
    fun setUp() {
        inputValidators = InputValidators()
        validatorsRepository = ValidatorsRepository(inputValidators = inputValidators)
    }

    @Test
    fun `WHEN put phoneNumber with extra signs EXPECT false result`() {

        val actualResult = validatorsRepository.isPhoneNumberValid(phoneNumber = "+7931;23445a")

        val expectedResult = false

        assertEquals(expectedResult, actualResult)

    }

    @Test
    fun `WHEN put correct phoneNumber EXPECT true result`() {

        val actualResult = validatorsRepository.isPhoneNumberValid(phoneNumber = "+79219459678")

        val expectedResult = true

        assertEquals(expectedResult, actualResult)

    }

    @Test
    fun `WHEN put phoneNumber with brackets EXPECT false result`() {

        val actualResult = validatorsRepository.isPhoneNumberValid(phoneNumber = "+7(921)9459678")

        val expectedResult = false

        assertEquals(expectedResult, actualResult)

    }

    @Test
    fun `WHEN put phoneNumber without plus sign EXPECT true result`() {

        val actualResult = validatorsRepository.isPhoneNumberValid(phoneNumber = "89219459678")

        val expectedResult = true

        assertEquals(expectedResult, actualResult)

    }

    @Test
    fun `WHEN put phoneNumber from Belarus EXPECT true result`() {

        val actualResult = validatorsRepository.isPhoneNumberValid(phoneNumber = "+3751234567890")

        val expectedResult = true

        assertEquals(expectedResult, actualResult)

    }

    @Test
    fun `WHEN put space userName with space EXPECT false result`() {

        val actualResult = validatorsRepository.isUserNameValid(userName = "f gth")

        val expectedResult = false

        assertEquals(expectedResult, actualResult)

    }

    @Test
    fun `WHEN put userName with russian words EXPECT true result`() {

        val actualResult = validatorsRepository.isUserNameValid(userName = "гюго")

        val expectedResult = true

        assertEquals(expectedResult, actualResult)

    }

    @Test
    fun `WHEN put userName with special signs EXPECT true result`() {

        val actualResult = validatorsRepository.isUserNameValid(userName = "!*@?-_.,:;")

        val expectedResult = true

        assertEquals(expectedResult, actualResult)

    }

    @Test
    fun `WHEN put userName with tab EXPECT false result`() {

        val actualResult = validatorsRepository.isUserNameValid(userName = "\t")

        val expectedResult = false

        assertEquals(expectedResult, actualResult)

    }

    @Test
    fun `WHEN put userName with new line EXPECT false result`() {

        val actualResult = validatorsRepository.isUserNameValid(userName = "\n")

        val expectedResult = false

        assertEquals(expectedResult, actualResult)

    }

    @Test
    fun `WHEN put empty userName EXPECT false result`() {

        val actualResult = validatorsRepository.isUserNameValid(userName = "")

        val expectedResult = false

        assertEquals(expectedResult, actualResult)

    }

    @Test
    fun `WHEN put empty firstName EXPECT false result`() {

        val actualResult = validatorsRepository.isFirstNameValid(fistName = "")

        val expectedResult = false

        assertEquals(expectedResult, actualResult)

    }

    @Test
    fun `WHEN put firstName with any sign EXPECT false result`() {

        val actualResult = validatorsRepository.isFirstNameValid(fistName = "Peter;")

        val expectedResult = false

        assertEquals(expectedResult, actualResult)

    }

    @Test
    fun `WHEN put firstName with space EXPECT false result`() {

        val actualResult = validatorsRepository.isFirstNameValid(fistName = "Peter ")

        val expectedResult = false

        assertEquals(expectedResult, actualResult)

    }

    @Test
    fun `WHEN put russian firstName EXPECT true result`() {

        val actualResult = validatorsRepository.isFirstNameValid(fistName = "Иван")

        val expectedResult = true

        assertEquals(expectedResult, actualResult)

    }

    @Test
    fun `WHEN put russian lastName EXPECT true result`() {

        val actualResult = validatorsRepository.isLastNameValid(lastName = "Залесский")

        val expectedResult = true

        assertEquals(expectedResult, actualResult)

    }

    @Test
    fun `WHEN put password with space EXPECT false result`() {

        val actualResult = validatorsRepository.isUserPasswordValid(password = "")

        val expectedResult = false

        assertEquals(expectedResult, actualResult)

    }

    @Test
    fun `WHEN put password with words and extra signs EXPECT true result`() {

        val actualResult = validatorsRepository.isUserPasswordValid(password = "qwert!?yuio_")

        val expectedResult = true

        assertEquals(expectedResult, actualResult)

    }

    @Test
    fun `WHEN put password of less then 8 characters signs EXPECT false result`() {

        val actualResult = validatorsRepository.isUserPasswordValid(password = "qwerty")

        val expectedResult = false

        assertEquals(expectedResult, actualResult)

    }

}