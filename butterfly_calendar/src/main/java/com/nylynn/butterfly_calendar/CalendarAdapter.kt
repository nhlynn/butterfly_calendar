package com.nylynn.butterfly_calendar

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.nylynn.butterfly_calendar.CalendarConstants.dateFormat
import com.nylynn.butterfly_calendar.CalendarConstants.dayOfWeekFormat
import com.nylynn.butterfly_calendar.databinding.CalendarCellBinding
import java.util.*
import kotlin.collections.ArrayList

class CalendarAdapter(dateClickListener: OnDateClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var onDateClickListener=dateClickListener
    private var dateList = arrayListOf<DatesVO>()
    private var eventList = arrayListOf<EventVO>()
    private var offDayList = arrayListOf<Date>()

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
            if (dateList[position].date == null) "" else dateFormat.format(dateList[position].date!!)

        root.mainCell.setOnClickListener {
            onDateClickListener.onClick(dateList[position].date!!)
        }

        if (dateList[position].date != null) {
            if (dayOfWeekFormat.format(dateList[position].date!!) == "Saturday" ||
                dayOfWeekFormat.format(dateList[position].date!!) == "Sunday"
            ) {
                root.tvMonthDate.setTextColor(
                    AppCompatResources.getColorStateList(
                        root.tvMonthDate.context,
                        R.color.red
                    )
                )
            } else {
                if (CalendarConstants.ymdFormatter.format(todayDate) ==
                    CalendarConstants.ymdFormatter.format(dateList[position].date!!)
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
                }
            }

            for (event in eventList) {
                if (event.strDate == CalendarConstants.ymdFormatter.format(dateList[position].date!!)) {
                    root.ivIcon.visibility = View.VISIBLE
                    root.ivIcon.setImageResource(event.image)
                }
            }

            for (offDay in offDayList) {
                if (CalendarConstants.ymdFormatter.format(offDay) ==
                    CalendarConstants.ymdFormatter.format(dateList[position].date!!)) {
                    root.tvMonthDate.setTextColor(
                        AppCompatResources.getColorStateList(
                            root.tvMonthDate.context,
                            R.color.red
                        )
                    )
                    root.ivIcon.visibility = View.VISIBLE
                    root.ivIcon.setImageResource(R.drawable.ic_calendar)
                }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(dateList: ArrayList<DatesVO>) {
        this.dateList = dateList
        notifyDataSetChanged()
    }

    fun addEvent(event: EventVO) {
        this.eventList.add(event)
    }

    fun setSingleOffDay(event: Date) {
        this.offDayList.add(event)
    }

    fun setMultipleOffDay(event: ArrayList<Date>) {
        this.offDayList = event
    }
}