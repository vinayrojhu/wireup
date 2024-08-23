package com.example.wireup.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateUtil {
    fun getDateTime(timeInMillis: Long) : String{
        val sdf = SimpleDateFormat("dd MMM, yy hh:mm a", Locale.getDefault())
        return sdf.format(Date(timeInMillis))
    }

    fun getDate(timeInMillis: Long) : String{
        val sdf = SimpleDateFormat("EEEE, MMM d", Locale.getDefault())
        return sdf.format(Date(timeInMillis))
    }

    fun getDate2(timeInMillis: Long) : String{
        val sdf = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
        return sdf.format(Date(timeInMillis))
    }
}