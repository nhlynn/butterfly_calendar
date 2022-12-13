package com.nylynn.butterfly_calendar

import java.util.*

interface OnDateClickListener {
    fun onClick(date: Date)

    fun onLongClick(date: Date)
}