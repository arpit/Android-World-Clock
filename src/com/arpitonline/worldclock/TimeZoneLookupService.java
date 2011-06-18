package com.arpitonline.worldclock;

import java.io.IOException;

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
	
	public String getTimeZoneFor(String s){
		Cursor c ;
		try{
		c = theDatabase.query("data", new String[] {KEY_CITY, KEY_TIMEZONE, KEY_COUNTRY},KEY_COUNTRY+"='"+s+"';", null, null, null, null, null);
		}catch(Exception e){
			Log.e(WorldClock.WORLD_CLOCK, "ERROR: "+e.getMessage());
			return "Exception "+e.getMessage();
		}
		
		int count = c.getCount();
	
		if(count == 0){
			return "Nothing found";
		}
		c.moveToFirst();
		String ret = "Return: ";
		for(int i=0;  i<count; i++){
			ret += c.getString(0)+c.getString(1)+c.getString(2);
		}
		return ret;
		
	}
}
