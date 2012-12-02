package com.arpitonline.worldclock;

import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

import com.arpitonline.worldclock.models.LocationVO;

public class TimeZoneLookupService extends DatabaseHelper {
	
	//String storageDir = Environment.getExternalStorageDirectory().getAbsolutePath()+"/com.arpitonline.worldclock/";
	
	public static final String TAG = "TimeZoneLookupService";
	
	public static final String CITY_NAME = SearchManager.SUGGEST_COLUMN_TEXT_1;
	public static final String COUNTRY_NAME = SearchManager.SUGGEST_COLUMN_TEXT_2;
	
	public static final String _ID = "_id";
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
			Log.e(TimelyPiece.WORLD_CLOCK, "> error creating database");
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
			TimelyPiece app = null;
			if (c instanceof TimelyPiece){
				app = (TimelyPiece)c;
			}
			if(c instanceof Activity){
				app = (TimelyPiece)(((Activity)c).getApplication());
 			}
			if(app != null && app.isFirstRunOfNewVersion()){
				Log.d(TAG, "Force copy: setting value");
				DatabaseHelper.FORCE_DATABASE_COPY = true;
			}
			instance = new TimeZoneLookupService(c);
		}
		return instance;
	}
	
	public ArrayList<LocationVO> getTimeZoneForCity(String s){
		Cursor c ;
		try{
		c = theDatabase.query("data", new String[] {KEY_CITY, KEY_TIMEZONE, KEY_COUNTRY, KEY_TIMEZONE_DISPLAY_NAME, KEY_TIMEZONE_ID},KEY_CITY+" like '%"+s+"%';", null, null, null, null, null);
		}catch(Exception e){
			Log.e(TimelyPiece.WORLD_CLOCK, "Error getting Timezone for "+KEY_CITY+" : "+e.getMessage());
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
		
		c.close();
		return list;
	}
	
	 public LocationVO getLocationForId(String id){
		 Cursor c ;
		 try{
			c = theDatabase.query("data", new String[] {KEY_CITY, KEY_TIMEZONE, KEY_COUNTRY, KEY_TIMEZONE_DISPLAY_NAME, KEY_TIMEZONE_ID},_ID+" = '"+id+"';", null, null, null, null, null);
		 }catch(Exception e){
				Log.e(TimelyPiece.WORLD_CLOCK, "Error getting Timezone for "+KEY_CITY+" : "+e.getMessage());
				return null;
		}
		c.moveToFirst();
		
		LocationVO vo = new LocationVO();
		vo.cityName = c.getString(0);
		vo.countryName = c.getString(2);
		vo.setTimezoneString(c.getString(1));
		vo.setTimeZoneDisplayName(c.getString(3));
		vo.setTimeZoneId(c.getString(4));
		
		c.close();
		
		return vo;
		
	 }
	
	 public Cursor getCursorForQuery(String query) {
		Cursor c ;
		try{
			c = theDatabase.rawQuery("select _id, city AS "+SearchManager.SUGGEST_COLUMN_TEXT_1+", country AS "+SearchManager.SUGGEST_COLUMN_TEXT_2+", _id AS "+SearchManager.SUGGEST_COLUMN_INTENT_EXTRA_DATA+"  from data where city like '"+query+"%';", null);
		}catch(Exception e){
			Log.e(TimelyPiece.WORLD_CLOCK, "Error getting Timezone for "+KEY_CITY+" : "+e.getMessage());
			return null;
		}
		Log.i(TimelyPiece.WORLD_CLOCK, "query cursor result: "+c.getCount());
		return c;
	 }
}
