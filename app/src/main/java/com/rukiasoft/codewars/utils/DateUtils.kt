package com.rukiasoft.codewars.utils

import android.annotation.SuppressLint
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object DateUtils{

    private const val DATE_FORMAT = "dd/MM/yyyy HH:mm"
    private const val ISO_DATE_FORMAT_ZULU = "yyyy-MM-dd'T'HH:mm:ss'Z'"
    private const val ISO_DATE_FORMAT_WITH_DECIMALS_ZULU = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSS'Z'"
    private const val ISO_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss"
    private const val ISO_DATE_FORMAT_WITH_DECIMALS = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSS"


    /**
     * Get Current Time
     *
     * @return time in milliseconds
     */
    val currentTime: Date
        get() {
            val c = Calendar.getInstance()
            return c.time
        }

    @SuppressLint("SimpleDateFormat")
    fun getDateFormatted(date: Date?): String {
        if (date == null) {
            return ""
        }
        val dateFormat = SimpleDateFormat(DATE_FORMAT)
        return try {
            dateFormat.format(date)
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }

    /**
     * Parses a date from ISO format
     *
     * @param date Date string in ISO format, e.g. "2016-04-14T09:45:55Z"
     * @return Parsed date or null
     */
    fun parseIsoDate(date: String?): Date? {
        if (date == null) {
            return null
        }

        return if (date.endsWith("Z")) {
            zuluTimeStringToZuluDate(date)
        } else {
            timeZonedTimeStringToZuluDate(date)
        }

    }

    @SuppressLint("SimpleDateFormat")
    private fun zuluTimeStringToZuluDate(date: String): Date? {

        val dateFormat = if (!date.contains(".")) {
            SimpleDateFormat(ISO_DATE_FORMAT_ZULU)
        } else {
            SimpleDateFormat(ISO_DATE_FORMAT_WITH_DECIMALS_ZULU)
        }
        return try {
            //Force timezone to GMT to store the time as Zulu Time
            dateFormat.timeZone = TimeZone.getTimeZone("GMT")
            dateFormat.parse(date)
        } catch (e: ParseException) {
            e.printStackTrace()
            null
        }

    }

    @SuppressLint("SimpleDateFormat")
    private fun timeZonedTimeStringToZuluDate(date: String): Date? {

        val dateFormat = if (!date.contains(".")) {
            SimpleDateFormat(ISO_DATE_FORMAT)
        } else {
            SimpleDateFormat(ISO_DATE_FORMAT_WITH_DECIMALS)
        }

        return try {
            dateFormat.parse(date)
        } catch (e: ParseException) {
            e.printStackTrace()
            null
        }

    }

}