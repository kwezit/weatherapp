package com.test.wheatherapp.utils

import com.test.wheatherapp.MainApplication
import com.test.wheatherapp.R
import java.text.SimpleDateFormat
import java.util.Locale

object Utils {

    private const val DESIRED_FORMAT = "dd MMM yyyy, HH:mm"
    private const val RECEIVED_DATE_FORMAT = "yyyy-MM-dd\'T\'HH:mm:ssXXX"
    const val DEFAULT_PIC_DIMEN = 300

    fun convertTimestampToDate(timestamp: Long) : String  {
        val sdf = SimpleDateFormat(DESIRED_FORMAT, Locale.getDefault())
        return sdf.format(timestamp)
    }

    fun reformatDuration(duration: Long) : String {
        var value = duration.toInt()

        if (value < 1000) {
            return "1 ${MainApplication.getContext().getString(R.string.postfix_second)}"
        }

        value /=1000 //in seconds

        if (value < 60) {
            return "$value ${MainApplication.getContext().getString(R.string.postfix_seconds)}"
        }

        value /= 60 //in minutes
        var rest = (duration - value*60*1000).toInt()

        if (rest > 60*1000) {
            rest /=1000
        } else {
            rest = 0
        }

        if (value == 1) {

            return "1 ${MainApplication.getContext().getString(R.string.postfix_minute)} $rest ${MainApplication.getContext().getString(R.string.postfix_seconds)}"
        } else if (value < 60) {
            return "$value ${MainApplication.getContext().getString(R.string.postfix_minutes)} $rest ${MainApplication.getContext().getString(R.string.postfix_seconds)}"
        }

        value /= 60 //in hours
        rest = (duration - value*60*60*1000).toInt()

        if (rest > 1000*60) {
            rest /=1000*60
        } else {
            rest = 0
        }

        if (value == 1) {
            return "1 ${MainApplication.getContext().getString(R.string.postfix_hour)} $rest ${MainApplication.getContext().getString(R.string.postfix_minutes)}"
        } else if (value < 23) {
            return "$value ${MainApplication.getContext().getString(R.string.postfix_hours)} $rest ${MainApplication.getContext().getString(R.string.postfix_minutes)}"
        }

        value /= 24 //in days
        rest = (duration - value*24*60*60*1000).toInt()

        if (rest > 1000*60*60) {
            rest /=1000*60*60
        } else {
            rest = 0
        }

        return if (value == 1) {
            "1 ${MainApplication.getContext().getString(R.string.postfix_day)} $rest ${MainApplication.getContext().getString(R.string.postfix_hours)}"
        } else  {
            "$value ${MainApplication.getContext().getString(R.string.postfix_days)} $rest ${MainApplication.getContext().getString(R.string.postfix_hours)}"
        }



    }

    fun convertDateToTimestamp(date: String?): Long {
        if (date == null) {
            return System.currentTimeMillis()
        }
        val dateFormat = SimpleDateFormat(RECEIVED_DATE_FORMAT, Locale.getDefault())

        return dateFormat.parse(date)?.time ?: throw IllegalArgumentException("Invalid date format")
    }

    @Suppress("UNUSED")
    fun formatDateString(inputDateString: String?): String {

        if (inputDateString == null) {
            return ""
        }

        val inputFormat = SimpleDateFormat(RECEIVED_DATE_FORMAT, Locale.getDefault())
        val outputFormat = SimpleDateFormat(DESIRED_FORMAT, Locale.getDefault())

        val date = inputFormat.parse(inputDateString)
        return outputFormat.format(date!!)
    }

}