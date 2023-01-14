package com.manager.labo.utils

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DateUtils private constructor() {
    companion object {
        private val DATE_FORMAT = DateTimeFormatter.ISO_LOCAL_DATE
        private val DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

        fun toDate(date: String): LocalDate = LocalDate.parse(date, DATE_FORMAT)
        fun toDateTime(dateTime: String): LocalDateTime = LocalDateTime.parse(dateTime, DATE_TIME_FORMAT)
        fun fromDate(date: LocalDate): String = date.format(DATE_FORMAT)
        fun fromDateTime(dateTime: LocalDateTime): String = dateTime.format(DATE_TIME_FORMAT)
    }
}
