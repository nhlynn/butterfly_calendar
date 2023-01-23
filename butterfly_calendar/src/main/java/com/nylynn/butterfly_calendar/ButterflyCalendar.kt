package com.nylynn.butterfly_calendar

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.AppCompatImageButton
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView
import com.nylynn.butterfly_calendar.CalendarConstants.myShowFormatter
import com.nylynn.butterfly_calendar.CalendarConstants.ymdFormatter
import java.util.*

class ButterflyCalendar constructor(
    context: Context,
    attrs: AttributeSet
) : LinearLayout(context, attrs), OnDateClickListener {
    private var mainCalendar: Calendar = Calendar.getInstance()
    private lateinit var calendarAdapter: CalendarAdapter
    private var tvYearMonth: MaterialTextView
    private var lblSun:MaterialTextView
    private var lblSat:MaterialTextView
    private var btnNext: AppCompatImageButton
    private var btnPrevious: AppCompatImageButton
    private var rvDate: RecyclerView
    private var onDateClickListener: OnDateClickListener? = null
    private var onMonthChangeListener: OnMonthChangeListener? = null

    init {
        val a = context.theme.obtainStyledAttributes(attrs, R.styleable.LynnCustomizeCalendar, 0, 0)
        a.recycle()

        val inflater: LayoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val rootView: View = inflater.inflate(R.layout.butterfly_calendar, this, true)

        tvYearMonth = rootView.findViewById(R.id.tv_year_month)
        lblSun = rootView.findViewById(R.id.lbl_sun)
        lblSat = rootView.findViewById(R.id.lbl_sat)
        btnNext = rootView.findViewById(R.id.btn_next)
        btnPrevious = rootView.findViewById(R.id.btn_previous)
        rvDate = rootView.findViewById(R.id.rv_date)

        setMonthView()
        setDate()

        btnNext.setOnClickListener {
            onNext()
        }

        btnPrevious.setOnClickListener {
            onPrevious()
        }

        initIndicators(context, attrs)
    }

    private fun onPrevious() {
        mainCalendar.set(
            Calendar.MONTH,
            mainCalendar.get(Calendar.MONTH) - 1
        )
        setDate()
    }

    private fun onNext() {
        mainCalendar.set(
            Calendar.MONTH,
            mainCalendar.get(Calendar.MONTH) + 1
        )
        setDate()
    }

    private fun setMonthView() {
        calendarAdapter = CalendarAdapter(this)
        val layoutManager = GridLayoutManager(context, 7, LinearLayoutManager.VERTICAL, false)
        rvDate.layoutManager = layoutManager
        rvDate.setHasFixedSize(true)
        rvDate.adapter = calendarAdapter
    }

    private fun setDate() {
        tvYearMonth.text = myShowFormatter.format(mainCalendar.time)
        calendarAdapter.setData(daysInMonthArray())

        if (onMonthChangeListener != null) {
            onMonthChangeListener!!.onMonthChange(
                ymdFormatter.format(
                    mainCalendar.time
                )
            )
        }
    }

    private fun daysInMonthArray(): ArrayList<DatesVO> {
        val daysInMonthArray: ArrayList<DatesVO> = ArrayList()
        val daysInMonth: Int = getDayInMonth()
        val dayOfWeek: Int = getDayOfWeek()

        for (i in 1..42) {
            if (i <= dayOfWeek || i > daysInMonth + dayOfWeek) {
                if (dayOfWeek < 7) {
                    daysInMonthArray.add(DatesVO(null, null))
                }
            } else {
                val calendar = Calendar.getInstance()
                calendar.set(Calendar.YEAR, mainCalendar.get(Calendar.YEAR))
                calendar.set(Calendar.MONTH, mainCalendar.get(Calendar.MONTH))
                calendar.set(Calendar.DAY_OF_MONTH, (i - dayOfWeek))
                daysInMonthArray.add(DatesVO(calendar.time, null))
            }
        }
        return daysInMonthArray
    }

    private fun getDayInMonth(): Int {
        val year = mainCalendar.get(Calendar.YEAR)
        val month = mainCalendar.get(Calendar.MONTH) + 1
        return if (month == 4 || month == 6 || month == 9 || month == 11) {
            30
        } else if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
            31
        } else {
            if (year % 4 == 0) {
                29
            } else {
                28
            }
        }
    }

    private fun getDayOfWeek(): Int {
        mainCalendar.set(Calendar.DAY_OF_MONTH, 1)
        return mainCalendar.get(Calendar.DAY_OF_WEEK) - 1
    }

    fun setOnMonthChangeListener(monthChangeListener: OnMonthChangeListener) {
        onMonthChangeListener = monthChangeListener
    }

    fun addEventWithIcon(date: Date, icon: Int) {
        calendarAdapter.addEvent(EventVO(ymdFormatter.format(date), icon))
    }

    fun addSingleOffDay(date: Date) {
        calendarAdapter.setSingleOffDay(date)
    }

    fun setMultipleOffDay(offDayList: ArrayList<Date>) {
        calendarAdapter.setMultipleOffDay(offDayList)
    }

    fun setSuperSundayOff() {
        sundayOff()
    }

    fun setWeekendOff() {
        weekendOff()
    }

    private fun sundayOff() {
        lblSun.setTextColor(AppCompatResources.getColorStateList(
            lblSun.context,
            R.color.red
        ))
        calendarAdapter.setSuperSundayOff()
    }

    private fun weekendOff() {
        lblSun.setTextColor(AppCompatResources.getColorStateList(
            lblSun.context,
            R.color.red
        ))

        lblSat.setTextColor(AppCompatResources.getColorStateList(
            lblSat.context,
            R.color.red
        ))
        calendarAdapter.setWeekendOff()
    }

    fun setOnDateClickListener(onDateClickListener: OnDateClickListener) {
        this.onDateClickListener = onDateClickListener
    }

    override fun onClick(date: Date) {
        if (onDateClickListener != null) {
            onDateClickListener!!.onClick(date)
        }
    }

    override fun onLongClick(date: Date) {
        if (onDateClickListener != null) {
            onDateClickListener!!.onLongClick(date)
        }
    }

    private fun initIndicators(context: Context, attrs: AttributeSet) {

        /**
         * Get TypedArray holding the attribute values in set that are listed in attrs.
         * Default style specified by defStyleAttr and defStyleRes
         * defStyleAttr contains a reference to a style resource that supplies defaults values for attributes
         * defStyleRes is resource identifier of a style resource that supplies default values for the attributes,
         * used only if defStyleAttr is 0 or can not be found in the theme. Can be 0 to not look for defaults.
         */
        val typedArray =
            context.theme.obtainStyledAttributes(attrs, R.styleable.LynnCustomizeCalendar, 0, 0)

        try {
            if (typedArray.getBoolean(R.styleable.LynnCustomizeCalendar_superSundayOff, false)) {
                sundayOff()
            }

            if (typedArray.getBoolean(R.styleable.LynnCustomizeCalendar_weekendOff, false)) {
                weekendOff()
            }

            btnPrevious.setImageResource(
                typedArray.getResourceId(
                    R.styleable.LynnCustomizeCalendar_previousIcon,
                    R.drawable.ic_previous
                )
            )

            btnNext.setImageResource(
                typedArray.getResourceId(
                    R.styleable.LynnCustomizeCalendar_nextIcon,
                    R.drawable.ic_next
                )
            )
        } finally {
            typedArray.recycle()
        }
    }
}