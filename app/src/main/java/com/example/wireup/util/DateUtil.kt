package com.example.wireup.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateUtil {
    fun getDateTime(timeInMillis: Long) : String{
        val sdf = SimpleDateFormat("dd MMM, yy hh:mm a", Locale.getDefault())
        return sdf.format(Date(timeInMillis))
    }
}