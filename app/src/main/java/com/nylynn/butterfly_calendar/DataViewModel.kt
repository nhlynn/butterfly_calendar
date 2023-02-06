package com.nylynn.butterfly_calendar

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DataViewModel: ViewModel() {
    var mDateResponse = MutableLiveData<ArrayList<String>>()

    fun getOffDate(month:String){
        val dateList= arrayListOf("2023-$month-12","2023-$month-13","2023-$month-14")
        mDateResponse.postValue(dateList)
    }
}