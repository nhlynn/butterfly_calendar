package com.nylynn.butterfly_calendar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.nylynn.butterfly_calendar.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.lynnCal.setSuperSundayOff()

        binding.lynnCal.setOnDateClickListener(object : OnDateClickListener{
            override fun onClick(date: String) {
                Toast.makeText(this@MainActivity,"$date",Toast.LENGTH_LONG).show()
            }

            override fun onLongClick(date: String) {
                Log.d("LogData","$date")
            }

        })

        binding.lynnCal.setOnMonthChangeListener(object : OnMonthChangeListener{
            override fun onMonthChange(date: String) {
                Toast.makeText(this@MainActivity,date,Toast.LENGTH_LONG).show()
            }

        })


        binding.lynnCal.addEventWithIcon("2023-02-03",R.drawable.ic_normal)
        binding.lynnCal.addEventWithIcon("2023-02-04",R.drawable.ic_normal)

        val offList= arrayListOf("2023-02-14","2023-02-15","2023-02-16")
        binding.lynnCal.setMultipleOffDay(offList)

        binding.lynnCal.addSingleOffDay("2023-02-13")
    }
}