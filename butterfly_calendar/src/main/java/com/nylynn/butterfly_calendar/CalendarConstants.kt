package com.nylynn.butterfly_calendar

import java.text.SimpleDateFormat
import java.util.*

var mainCalendar: Calendar = Calendar.getInstance()
val ymdFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.US)
val ymFormatter = SimpleDateFormat("yyyy-MM", Locale.US)
val myShowFormatter = SimpleDateFormat("MMM yyyy", Locale.US)
val dateFormat = SimpleDateFormat("dd", Locale.US)
val dayOfWeekFormat = SimpleDateFormat("EEEE", Locale.US)