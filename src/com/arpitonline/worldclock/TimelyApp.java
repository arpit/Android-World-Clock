package com.arpitonline.worldclock;

import com.arpitonline.worldclock.models.LocationVO;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;

public class TimelyApp extends Application {
	@Override
	public void onCreate(){
		super.onCreate();
		
		SharedPreferences prefs = getSharedPreferences(WorldClock.PREF_LOCATION_FILE_NAME, MODE_PRIVATE);
		String locations = prefs.getString(WorldClock.PREF_KEY, "");
		String[] cities = locations.split("\\|");
		Log.i(WorldClock.WORLD_CLOCK, "cities => "+cities.length ); 
		
		if(locations != ""){
			TimeZoneLookupService service =TimeZoneLookupService.getInstance(this);
			for(int i=0; i<cities.length; i++){
				LocationVO res = service.getTimeZoneForCity(cities[i]).get(0);
				res.initialize();
				WorldClock.getInstance().addLocation(res);
			}
		}
		
		
	}
}
