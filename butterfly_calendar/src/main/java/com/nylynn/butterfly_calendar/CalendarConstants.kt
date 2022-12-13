package com.nylynn.butterfly_calendar

import java.text.SimpleDateFormat
import java.util.*

object CalendarConstants {
    var ymdFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.US)
    var myShowFormatter = SimpleDateFormat("MMM yyyy", Locale.US)
    var dateFormat = SimpleDateFormat("dd", Locale.US)
    var dayOfWeekFormat = SimpleDateFormat("EEEE", Locale.US)
}