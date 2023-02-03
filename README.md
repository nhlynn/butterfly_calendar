# butterfly_calendar
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
	        implementation 'com.github.nhlynn:butterfly_calendar:1.1.5'
	}


You can use following function 

-custom event with icon

-single off day

-multiple off day


    <com.nylynn.dynamic_calendar.LynnCustomizeCalendar
        android:id="@+id/my_calendar"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginTop="8dp"
        app:weekendOff="true" //default is false
        app:superSundayOff="true" //default is false
        app:previousIcon="@drawable/ic_previous"
        app:nextIcon="@drawable/ic_next"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />


//Date Click Listener

        binding.myCalendar.setOnDateClickListener(object : OnDateClickListener{
            override fun onClick(date: String) {
                Toast.makeText(this@MainActivity,date, Toast.LENGTH_LONG).show()
            }

            override fun onLongClick(date: String) {
                Toast.makeText(this@MainActivity,date,Toast.LENGTH_LONG).show()
            }

        })


//Month Change Listener

 binding.myCalendar.setOnMonthChangeListener(object : OnMonthChangeListener{
            override fun onMonthChange(date: String) {
                Toast.makeText(this@MainActivity,date,Toast.LENGTH_LONG).show()
            }

        })


//multiple off day

        val offDayList= arrayListOf<String>()
        offDayList.add("2022-02-13")
	offDayList.add("2022-02-14")
	offDayList.add("2022-02-15")
        binding.myCalendar.setMultipleOffDay(offDayList)

	
//single off day
	
            binding.myCalendar.addSingleOffDay("2023-02-17")
	
	

//add event

  binding.myCalendar.addEventWithIcon("2023-02-19",R.drawable.ic_travel)
	
	
	
//To get calendar month

        binding.myCalendar.getCalendarMonth()
        

//To refresh calendar


        binding.btnRefresh.setOnClickListener {
            binding.myCalendar.setOnRefreshCalendar()
        }

