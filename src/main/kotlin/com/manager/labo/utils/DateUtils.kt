package com.manager.labo.utils

import java.time.format.DateTimeFormatter

public final class DateUtils private constructor() {

    private val DATE_FORMAT = DateTimeFormatter.ISO_LOCAL_DATE
    private val DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

    public val instance: DateUtils = DateUtils()

}
