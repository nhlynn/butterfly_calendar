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
