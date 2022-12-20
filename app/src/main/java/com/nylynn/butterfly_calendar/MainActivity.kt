package com.nylynn.butterfly_calendar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nylynn.butterfly_calendar.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.lynnCal.setWeekendOff()
    }
}