package com.arpitonline.worldclock;

import java.io.IOException;
import java.util.ArrayList;

import com.arpitonline.worldclock.models.LocationVO;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Environment;
import android.util.Log;

public class TimeZoneLookupService extends DatabaseHelper {
	
	String storageDir = Environment.getExternalStorageDirectory().getAbsolutePath()+"/com.arpitonline.worldclock/";
	
	public static final String DATABASE_TABLE = "data";
	public static final String KEY_CITY = "city";
	public static final String KEY_COUNTRY= "country";
	public static final String KEY_TIMEZONE = "timezone";
	public static final String KEY_TIMEZONE_DISPLAY_NAME = "display_name";
	public static final String KEY_TIMEZONE_ID = "timezone_id";
	
	private TimeZoneLookupService(Context c){
		 
		  super(c, "world_time.db");
		  try {
			  createDataBase();
		  }catch (IOException ioe) {
			Log.e(WorldClock.WORLD_CLOCK, "> error creating database");
		  }

		  try {
			 openDataBase();
		  }catch(SQLException sqle){
		  	throw(sqle);
		  }
		
	}
	
	private static TimeZoneLookupService instance;
	public static TimeZoneLookupService getInstance(Context c){
		if(instance == null){
			instance = new TimeZoneLookupService(c);
		}
		return instance;
	}
	
	public ArrayList<LocationVO> getTimeZoneForCity(String s){
		Cursor c ;
		try{
		c = theDatabase.query("data", new String[] {KEY_CITY, KEY_TIMEZONE, KEY_COUNTRY, KEY_TIMEZONE_DISPLAY_NAME, KEY_TIMEZONE_ID},KEY_CITY+" like '%"+s+"%';", null, null, null, null, null);
		}catch(Exception e){
			Log.e(WorldClock.WORLD_CLOCK, "ERROR: "+e.getMessage());
			return null;
		}
		
		int count = c.getCount();
	
		if(count == 0){
			return null;
		}
		c.moveToFirst();
		ArrayList<LocationVO> list = new ArrayList<LocationVO>();
		for(int i=0;  i<count; i++){
			
			LocationVO vo = new LocationVO();
			vo.cityName = c.getString(0);
			vo.countryName = c.getString(2);
			vo.setTimezoneString(c.getString(1));
			vo.setTimeZoneDisplayName(c.getString(3));
			vo.setTimeZoneId(c.getString(4));
			list.add(vo);
			c.moveToNext();
		}
		return list;
		
	}
}
