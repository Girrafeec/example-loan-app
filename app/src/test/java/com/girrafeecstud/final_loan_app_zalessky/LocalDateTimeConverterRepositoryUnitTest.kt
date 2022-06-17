package com.girrafeecstud.final_loan_app_zalessky;

import com.girrafeecstud.final_loan_app_zalessky.data.convertion.LocalDateTimeConverter;
import com.girrafeecstud.final_loan_app_zalessky.data.repository.LocalDateTimeConverterRepository
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert.assertEquals
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter

class LocalDateTimeConverterRepositoryUnitTest {

    private lateinit var localDateTimeConverter: LocalDateTimeConverter

    private lateinit var localDateTimeConverterRepository: LocalDateTimeConverterRepository

    @Before
    fun setUp() {
        localDateTimeConverter = LocalDateTimeConverter()
        localDateTimeConverterRepository = LocalDateTimeConverterRepository(localDateTimeConverter = localDateTimeConverter)
    }

    @Test
    fun `WHEN put string with data like in server EXPECT LocalDateTime with same value`() {

        val dateTime = "2022-06-14T14:51:29.506+00:00"

        val actualResult = localDateTimeConverterRepository.getLocalDateTimeFromString(dateTimeString = dateTime)

        val expectedResult = LocalDateTime.parse(dateTime, DateTimeFormatter.ISO_ZONED_DATE_TIME)

        assertEquals(expectedResult.equals(actualResult), true)
    }

    @Test
    fun `WHEN put LocalDateTime with data like in server EXPECT String with same value`() {

        val dateTime = "2022-06-14T14:51:29.506+00:00"

        val actualResult = localDateTimeConverterRepository
            .getStringFromLocalDateTime(localDateTime = LocalDateTime.parse(dateTime, DateTimeFormatter.ISO_ZONED_DATE_TIME))

        val expectedResult = LocalDateTime.parse(dateTime, DateTimeFormatter.ISO_ZONED_DATE_TIME).toString()

        assertEquals(expectedResult, actualResult)
    }

    @Test
    fun `WHEN PUT LocalDateTime EXPECT string value with the same date of format dd-MM-yyyy`() {

        val dateTime = "2022-06-14T14:51:29.506+00:00"

        val actualResult = localDateTimeConverterRepository
            .getDateStringFromLocalDateTime(localDateTime = LocalDateTime.parse(dateTime, DateTimeFormatter.ISO_ZONED_DATE_TIME))

        val expectedResult = "14-06-2022"

        assertEquals(expectedResult, actualResult)
    }

    @Test
    fun `WHEN PUT LocalDateTime EXPECT string value with the same time of format hh mm`() {

        val dateTime = "2022-06-14T14:51:29.506+00:00"

        val actualResult = localDateTimeConverterRepository
            .getTimeStringFromLocalDateTime(localDateTime = LocalDateTime.parse(dateTime, DateTimeFormatter.ISO_ZONED_DATE_TIME))

        val expectedResult = "14:51"

        assertEquals(expectedResult, actualResult)
    }

}
