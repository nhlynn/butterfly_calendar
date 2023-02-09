package com.nylynn.butterfly_calendar

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.AppCompatImageButton
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView
import java.util.*

class ButterflyCalendar constructor(
    context: Context,
    attrs: AttributeSet
) : LinearLayout(context, attrs), OnDateClickListener {
    private lateinit var calendarAdapter: CalendarAdapter
    private var tvYearMonth: MaterialTextView
    private var lblSun: MaterialTextView
    private var lblSat: MaterialTextView
    private var btnNext: AppCompatImageButton
    private var btnPrevious: AppCompatImageButton
    private var rvDate: RecyclerView
    private var onDateClickListener: OnDateClickListener? = null
    private var onMonthChangeListener: OnMonthChangeListener? = null
    private var clearHistory = false

    init {
        val a = context.theme.obtainStyledAttributes(attrs, R.styleable.LynnCustomizeCalendar, 0, 0)
        a.recycle()

        val inflater: LayoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val rootView: View = inflater.inflate(R.layout.butterfly_calendar, this, true)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        tvYearMonth = rootView.findViewById(R.id.tv_header_date)
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
        if (clearHistory) {
            clearHistory()
        }
        setDate()
    }

    private fun onNext() {
        mainCalendar.set(
            Calendar.MONTH,
            mainCalendar.get(Calendar.MONTH) + 1
        )
        if (clearHistory) {
            clearHistory()
        }
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
        val dateModelList: ArrayList<DatesVO> = ArrayList()
        // set date start of month
        val calendar = Calendar.getInstance()
        calendar.time = mainCalendar.time
        calendar.set(Calendar.DAY_OF_MONTH, 1)

        val monthBeginningCell: Int = calendar.get(Calendar.DAY_OF_WEEK) - 1
        calendar.add(Calendar.DAY_OF_MONTH, -monthBeginningCell)
        dateModelList.clear()

        while (dateModelList.size < 42) {
            val model = DatesVO(calendar.time)
            dateModelList.add(model)
            calendar.add(Calendar.DAY_OF_MONTH, 1)
        }

        return dateModelList
    }

    private fun sundayOff() {
        lblSun.setTextColor(
            AppCompatResources.getColorStateList(
                lblSun.context,
                R.color.red
            )
        )
        calendarAdapter.setSuperSundayOff()
    }

    private fun weekendOff() {
        lblSun.setTextColor(
            AppCompatResources.getColorStateList(
                lblSun.context,
                R.color.red
            )
        )

        lblSat.setTextColor(
            AppCompatResources.getColorStateList(
                lblSat.context,
                R.color.red
            )
        )
        calendarAdapter.setWeekendOff()
    }

    override fun onClick(date: String) {
        if (onDateClickListener != null) {
            onDateClickListener!!.onClick(date)
        }
    }

    override fun onLongClick(date: String) {
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

            clearHistory = (typedArray.getBoolean(
                R.styleable.LynnCustomizeCalendar_clearHistoryOnMonthChange,
                false
            ))

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

    fun setNextIcon(resourceId: Int) {
        btnNext.setImageResource(resourceId)
    }

    fun setPreviousIcon(resourceId: Int) {
        btnPrevious.setImageResource(resourceId)
    }

    fun setOnRefreshCalendar() {
        mainCalendar = Calendar.getInstance()
        setDate()
    }

    fun getCalendarMonth(): String {
        return ymdFormatter.format(
            mainCalendar.time
        )
    }

    fun addEventWithIcon(date: String, icon: Int) {
        calendarAdapter.addEvent(EventVO(date, icon))
    }

    fun clearHistory() {
        calendarAdapter.clearCalendar()
    }

    fun addSingleOffDay(date: String) {
        calendarAdapter.setSingleOffDay(date)
    }

    fun setMultipleOffDay(offDayList: ArrayList<String>) {
        calendarAdapter.setMultipleOffDay(offDayList)
    }

    fun setSuperSundayOff() {
        sundayOff()
    }

    fun setWeekendOff() {
        weekendOff()
    }

    fun setClearHistoryOnMonthChange(status: Boolean) {
        clearHistory = status
    }

    fun setOnDateClickListener(onDateClickListener: OnDateClickListener) {
        this.onDateClickListener = onDateClickListener
    }

    fun setOnMonthChangeListener(monthChangeListener: OnMonthChangeListener) {
        onMonthChangeListener = monthChangeListener
    }

}