package com.arpitonline.worldclock.models;

import java.util.TimeZone;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalTime;

import com.arpitonline.worldclock.FlickrActivity;
import com.arpitonline.worldclock.WorldClock;

import android.util.Log;

public class LocationVO {
	
	public String cityName;
	public String countryName;
	public String gmtOffsetString;
	
	public DateTimeZone dateTimeZone;
	
	private String timezoneString;
	private String timezoneDisplayName;
	private String[] citiesInTimeZone;
	private String timeZoneId;
	

	public LocationVO(){
	}
	
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
	
	public DateTime getDateTime(){
		if(dateTimeZone == null){
			return null;
		}
		DateTime dt = new DateTime();
		DateTime current = dt.withZone(this.dateTimeZone);
		return current;
	}
	
	public LocalTime getTime(){
		DateTime current = getDateTime();
		if(current == null){
			return null;
		}
		return current.toLocalTime();
	}
	
	public String getFormattedTime(){
		LocalTime time = getTime();
		String amPm = "am";
		int hr;
		if(time.getHourOfDay() > 12){
			amPm = "pm";
			hr = time.getHourOfDay()-12;
		}
		else if (time.getHourOfDay() == 12){
			amPm = "pm";
			hr = time.getHourOfDay();
		}
		else{
			amPm = "am";
			hr = time.getHourOfDay();
		}
		return (hr<10?"0"+hr:hr)+":"+ (time.getMinuteOfHour()>9?time.getMinuteOfHour():"0"+time.getMinuteOfHour())+" "+amPm;
	}
	
	public String toString(){
		return cityName+", "+countryName;
	}
}
