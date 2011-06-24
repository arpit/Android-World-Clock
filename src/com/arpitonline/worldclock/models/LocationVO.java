package com.arpitonline.worldclock.models;

import java.util.TimeZone;

import com.arpitonline.worldclock.WorldClock;

import android.util.Log;

public class LocationVO {
	public String cityName;
	public String countryName;
	public String gmtOffsetString;
	
	private String timezoneString;
	private String timezoneDisplayName;
	private String[] citiesInTimeZone;
	private String timeZoneId;
	
	public void setTimezoneString(String timezone) {
		this.timezoneString = timezone;
		gmtOffsetString = timezone.substring(timezone.indexOf("(")+1, timezone.indexOf(")")); 
		
		String cities = timezone.split("\\) ")[1];
		citiesInTimeZone = cities.split(", ");
		
	}
	
	public void setTimeZoneDisplayName(String s){
		timezoneDisplayName = s;
	}
	
	public String getTimeZoneDisplayName(){
		return timezoneDisplayName;
	}
	
	public void setTimeZoneId(String id){
		this.timeZoneId = id;
	}
	
	public TimeZone getTimeZone(){
		Log.i(WorldClock.WORLD_CLOCK, "TimezoneID: "+timeZoneId);
		if(timeZoneId==null|| timeZoneId.length() ==0){
			return null;
		}
		return TimeZone.getTimeZone(timeZoneId);
	}


	public LocationVO(){
	}
}
