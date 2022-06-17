package com.girrafeecstud.final_loan_app_zalessky.data.repository

import com.girrafeecstud.final_loan_app_zalessky.data.convertion.LocalDateTimeConverter
import org.threeten.bp.LocalDateTime
import javax.inject.Inject

class LocalDateTimeConverterRepository @Inject constructor(
    private val localDateTimeConverter: LocalDateTimeConverter
) {

    fun getLocalDateTimeFromString(dateTimeString: String): LocalDateTime {
        return localDateTimeConverter.convertStringToLocalDateTime(dateTimeString = dateTimeString)
    }

    fun getDateStringFromLocalDateTime(localDateTime: LocalDateTime): String {
        return localDateTimeConverter.getDateStringFromLocalDateTime(localDateTime = localDateTime)
    }

    fun getTimeStringFromLocalDateTime(localDateTime: LocalDateTime): String {
        return localDateTimeConverter.getTimeStringFromLocalDateTime(localDateTime = localDateTime)
    }

}