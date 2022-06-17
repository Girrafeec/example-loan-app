package com.girrafeecstud.final_loan_app_zalessky.data.convertion

import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter
import javax.inject.Inject

class LocalDateTimeConverter @Inject constructor(

) {

    fun convertStringToLocalDateTime(dateTimeString: String): LocalDateTime {
        return LocalDateTime.parse(dateTimeString, DateTimeFormatter.ISO_ZONED_DATE_TIME)
    }

    fun getDateStringFromLocalDateTime(localDateTime: LocalDateTime): String {
        return localDateTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
    }

    fun getTimeStringFromLocalDateTime(localDateTime: LocalDateTime): String {
        return localDateTime.format(DateTimeFormatter.ofPattern("HH:mm"))
    }

}