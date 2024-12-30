package com.obrio.test.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Long.convertMillisToDate() : String {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    val date = Date(this)
    return dateFormat.format(date)
}