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
	        implementation 'com.github.nhlynn:butterfly_calendar:Tag'
	}


You can use following function 

-custom event with icon

-single off day

-multiple off day


    <com.nylynn.butterfly_calendar.ButterflyCalendar
        android:id="@+id/my_calendar"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />


//Date Click Listener

        binding.myCalendar.setOnDateClickListener(object : OnDateClickListener{
            override fun onClick(date: Date) {
                Toast.makeText(this@MainActivity,CalendarConstants.ymdFormatter.format(date), Toast.LENGTH_LONG).show()
            }

            override fun onLongClick(date: Date) {
                Toast.makeText(this@MainActivity,CalendarConstants.ymdFormatter.format(date),Toast.LENGTH_LONG).show()
            }

        })


//Month Change Listener

 binding.myCalendar.setOnMonthChangeListener(object : OnMonthChangeListener{
            override fun onMonthChange(date: String) {
                Toast.makeText(this@MainActivity,date,Toast.LENGTH_LONG).show()
            }

        })


//multiple off day

        val offDayList= arrayListOf<Date>()
        for(value in 26..30) {
            val cal = Calendar.getInstance()
            cal.set(Calendar.DAY_OF_MONTH, value)
            offDayList.add(cal.time)
        }
        binding.myCalendar.setMultipleOffDay(offDayList)

	
//single off day
	
            val cal = Calendar.getInstance()
            cal.set(Calendar.DAY_OF_MONTH, 12)
            binding.myCalendar.addSingleOffDay(cal.time)
	
	

//add event

  binding.myCalendar.addEventWithIcon(calendar.time,R.drawable.ic_travel)
	
	
	
	
