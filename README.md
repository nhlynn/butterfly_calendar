# butterfly_calendar

<div>
	<img src="https://github.com/nhlynn/butterfly_calendar/assets/57884748/89f833b9-4ef7-4a8a-8b80-0101890fe23c" width=15% height=20%/>
	<img src="https://github.com/nhlynn/butterfly_calendar/assets/57884748/54ce7378-e7ac-455f-9205-4704dc5eb006" width=15% height=20%/>
</div>


customize calendar

To get a butterfly calendar into your build:

Step 1. Add the JitPack repository to your build file

Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
  
Step 2. Add the dependency

	dependencies {
	        implementation 'com.github.nhlynn:butterfly_calendar:1.2.1'
	}


You can use following function 

-custom event with icon

-single off day

-multiple off day

Usage ::

1.Add Calendar to your layout

    <com.nylynn.dynamic_calendar.LynnCustomizeCalendar
        android:id="@+id/my_calendar"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginTop="8dp"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

2.You can use layout attributes

    app:clearHistoryOnMonthChange="true" // default is false if you want to clear on month change event and day off history
    app:nextIcon="@drawable/ic_next" // you can change next button icon
    app:previousIcon="@drawable/ic_previous" // you can change previous button icon
    app:superSundayOff="true" // default is false
    app:weekendOff="true" // default is false


3.Date Click Listener

        binding.myCalendar.setOnDateClickListener(object : OnDateClickListener{
            override fun onClick(date: String) {
                Toast.makeText(this@MainActivity,date, Toast.LENGTH_LONG).show()
            }

            override fun onLongClick(date: String) {
                Toast.makeText(this@MainActivity,date,Toast.LENGTH_LONG).show()
            }

        })


4.Month Change Listener

       binding.myCalendar.setOnMonthChangeListener(object : OnMonthChangeListener{
            override fun onMonthChange(currentMonth:String, startDate:String, endDate:String) {

                Log.d("LogData","Monthly Start Date = ${binding.lynnCal.getStartDate()}")
                Log.d("LogData","Monthly End Date = ${binding.lynnCal.getEndDate()}")

                Log.d("LogData","Monthly Date Listener = $currentMonth")
                Log.d("LogData","Monthly Start Date Listener = $startDate")
                Log.d("LogData","Monthly End Date Listener = $endDate")
            }
	})


5.If you want to set multiple off day

        val offDayList= arrayListOf<String>()
        offDayList.add("2022-02-13")
        offDayList.add("2022-02-14")
        offDayList.add("2022-02-15")
        binding.myCalendar.setMultipleOffDay(offDayList)
	
6.If you want to set single off day
	
            binding.myCalendar.addSingleOffDay("2023-02-17")
	
	

7.If you want to add event

        binding.myCalendar.addEventWithIcon("2023-02-19",R.drawable.ic_travel)
        

8.To get calendar month

        binding.myCalendar.getCalendarMonth()
        

9.To refresh calendar

        binding.btnRefresh.setOnClickListener {
            binding.myCalendar.setOnRefreshCalendar()
        }

10. To change next button icon
        
        binding.lynnCal.setNextIcon(R.drawable.ic_next)


11. To change previous button icon

        binding.lynnCal.setPreviousIcon(R.drawable.ic_previous)

12. If you want to clear on month change event and day off history

        binding.lynnCal.setClearHistoryOnMonthChange(true)


