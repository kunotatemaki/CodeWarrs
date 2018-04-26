package com.rukiasoft.codewars.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

object DateUtils{

    private const val DATE_FORMAT = "dd/MM/yyyy HH:mm"


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
}