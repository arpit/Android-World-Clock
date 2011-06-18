package com.arpitonline.worldclock;

import java.io.IOException;
import java.util.ArrayList;

import com.arpitonline.worldclock.models.CountryVO;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Environment;
import android.util.Log;

public class TimeZoneLookupService extends DatabaseHelper {
	
	String storageDir = Environment.getExternalStorageDirectory().getAbsolutePath()+"/com.arpitonline.worldclock/";
	
	public static final String DATABASE_TABLE = "data";
	public static final String KEY_CITY = "city";
	public static final String KEY_TIMEZONE = "timezone";
	public static final String KEY_COUNTRY= "country";
	
	public TimeZoneLookupService(Context c){
		 
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
	
	public ArrayList<CountryVO> getTimeZoneForCountry(String s){
		Cursor c ;
		try{
		c = theDatabase.query("data", new String[] {KEY_CITY, KEY_TIMEZONE, KEY_COUNTRY},KEY_COUNTRY+"='"+s+"';", null, null, null, null, null);
		}catch(Exception e){
			Log.e(WorldClock.WORLD_CLOCK, "ERROR: "+e.getMessage());
			return null;
		}
		
		int count = c.getCount();
	
		if(count == 0){
			return null;
		}
		c.moveToFirst();
		ArrayList<CountryVO> list = new ArrayList<CountryVO>();
		for(int i=0;  i<count; i++){
			
			CountryVO vo = new CountryVO(c.getString(2), null);
			vo.cityName = c.getString(0);
			vo.timezone = c.getString(1);
			list.add(vo);
			c.moveToNext();
		}
		return list;
		
	}
}
