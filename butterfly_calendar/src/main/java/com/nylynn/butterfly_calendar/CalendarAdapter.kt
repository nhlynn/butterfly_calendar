package com.nylynn.butterfly_calendar

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.nylynn.butterfly_calendar.databinding.CalendarCellBinding
import java.util.*
import kotlin.collections.ArrayList

class CalendarAdapter(dateClickListener: OnDateClickListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var onDateClickListener = dateClickListener
    private var dateList = arrayListOf<DatesVO>()
    private var eventList = arrayListOf<EventVO>()
    private var offDayList = arrayListOf<String>()
    private var mSuperSunday = false
    private var mWeekend = false

    private val c: Calendar = Calendar.getInstance()
    private val todayDate: Date = c.time

    class CalendarViewHolder(val viewBinder: CalendarCellBinding) :
        RecyclerView.ViewHolder(viewBinder.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        val binding =
            CalendarCellBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CalendarViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dateList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder = holder as CalendarViewHolder
        val root = viewHolder.viewBinder

        root.tvMonthDate.text =
            dateFormat.format(dateList[position].date)

        root.mainCell.setOnClickListener {
            onDateClickListener.onClick(ymdFormatter.format(dateList[position].date))
        }

        root.mainCell.setOnLongClickListener {
            onDateClickListener.onLongClick(ymdFormatter.format(dateList[position].date))
            true
        }

        if (ymFormatter.format(dateList[position].date) == ymFormatter.format(mainCalendar.time)) {
            if (mWeekend && (dayOfWeekFormat.format(dateList[position].date) == "Saturday" ||
                        dayOfWeekFormat.format(dateList[position].date) == "Sunday")
            ) {
                root.tvMonthDate.setTextColor(
                    AppCompatResources.getColorStateList(
                        root.tvMonthDate.context,
                        R.color.red
                    )
                )
            } else if (mSuperSunday &&
                dayOfWeekFormat.format(dateList[position].date) == "Sunday"
            ) {
                root.tvMonthDate.setTextColor(
                    AppCompatResources.getColorStateList(
                        root.tvMonthDate.context,
                        R.color.red
                    )
                )
            } else {
                if (ymdFormatter.format(todayDate) ==
                    ymdFormatter.format(dateList[position].date)
                ) {
                    root.tvMonthDate.setTextColor(
                        AppCompatResources.getColorStateList(
                            root.tvMonthDate.context,
                            R.color.blue
                        )
                    )
                    root.tvMonthDate.textSize = 20f
                } else {
                    root.tvMonthDate.setTextColor(
                        AppCompatResources.getColorStateList(
                            root.tvMonthDate.context,
                            R.color.grey
                        )
                    )
                    root.tvMonthDate.textSize = 14f
                }
            }

            for (event in eventList) {
                if (event.strDate == ymdFormatter.format(dateList[position].date)) {
                    root.ivEvent.setImageResource(event.image)
                }
            }

            for (offDay in offDayList) {
                if (offDay ==
                    ymdFormatter.format(dateList[position].date)
                ) {
                    root.tvMonthDate.setTextColor(
                        AppCompatResources.getColorStateList(
                            root.tvMonthDate.context,
                            R.color.red
                        )
                    )
                    root.ivEvent.setImageResource(R.drawable.ic_calendar)
                }
            }
        }else{
            root.tvMonthDate.setTextColor(
                AppCompatResources.getColorStateList(
                    root.tvMonthDate.context,
                    R.color.black_disable
                )
            )
            root.tvMonthDate.textSize = 14f

            if (this.mWeekend &&
                (dayOfWeekFormat.format(dateList[position].date) == "Saturday" ||
                        dayOfWeekFormat.format(dateList[position].date) == "Sunday")) {
                root.tvMonthDate.setTextColor(
                    AppCompatResources.getColorStateList(
                        root.tvMonthDate.context,
                        R.color.red_disable
                    )
                )
            }

            if (this.mSuperSunday &&
                dayOfWeekFormat.format(dateList[position].date) == "Sunday") {
                root.tvMonthDate.setTextColor(
                    AppCompatResources.getColorStateList(
                        root.tvMonthDate.context,
                        R.color.red_disable
                    )
                )
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(dateList: ArrayList<DatesVO>) {
        this.dateList = dateList
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clearCalendar() {
        this.eventList.clear()
        this.offDayList.clear()
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addEvent(event: EventVO) {
        this.eventList.add(event)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setSingleOffDay(event: String) {
        this.offDayList.add(event)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setMultipleOffDay(event: ArrayList<String>) {
        this.offDayList = event
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setSuperSundayOff() {
        this.mSuperSunday = true
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setWeekendOff() {
        this.mWeekend = true
        notifyDataSetChanged()
    }
}