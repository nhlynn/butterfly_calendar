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
            override fun onClick(date: Date) {
                Toast.makeText(this@MainActivity,"$date",Toast.LENGTH_LONG).show()
            }

            override fun onLongClick(date: Date) {
                Log.d("LogData","$date")
            }

        })

        binding.lynnCal.setOnMonthChangeListener(object : OnMonthChangeListener{
            override fun onMonthChange(date: String) {
                Toast.makeText(this@MainActivity,date,Toast.LENGTH_LONG).show()
            }

        })
    }
}