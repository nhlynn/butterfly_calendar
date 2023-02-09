package com.nylynn.butterfly_calendar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.nylynn.butterfly_calendar.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private lateinit var mDataViewModel: DataViewModel
    private val ymdFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.US)
    private val monthFormatter = SimpleDateFormat("MM", Locale.US)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mDataViewModel=ViewModelProvider(this)[DataViewModel::class.java]
        mDataViewModel.getOffDate(monthFormatter.format(ymdFormatter.parse(binding.lynnCal.getCalendarMonth())!!))

        binding.lynnCal.setSuperSundayOff()

        binding.lynnCal.setOnDateClickListener(object : OnDateClickListener{
            override fun onClick(date: String) {
                Toast.makeText(this@MainActivity,date,Toast.LENGTH_LONG).show()
            }

            override fun onLongClick(date: String) {
                Log.d("LogData",date)
            }

        })

        binding.lynnCal.setOnMonthChangeListener(object : OnMonthChangeListener{
            override fun onMonthChange(date: String) {

                val month = monthFormatter.format(ymdFormatter.parse(binding.lynnCal.getCalendarMonth())!!)
                mDataViewModel.getOffDate(month)

                binding.lynnCal.addEventWithIcon("2023-$month-03",R.drawable.ic_normal)
                binding.lynnCal.addEventWithIcon("2023-$month-04",R.drawable.ic_next)
                binding.lynnCal.addEventWithIcon("2023-$month-05",R.drawable.ic_previous)
                binding.lynnCal.addEventWithIcon("2023-$month-06",R.drawable.ic_normal)
            }

        })

        mDataViewModel.mOffDateResponse.observe(this){
            binding.lynnCal.setMultipleOffDay(it)
        }

    }
}