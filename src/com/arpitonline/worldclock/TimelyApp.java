package com.arpitonline.worldclock;

import java.util.ArrayList;

import com.arpitonline.worldclock.models.LocationVO;

import android.app.Application;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class TimelyApp extends Application {
	
	public static final String WORLD_CLOCK = "world clock";
	public static final String PREF_LOCATION_FILE_NAME = "savedLocations";
	public static final String PREF_KEY = "locations";
	
	private SharedPreferences prefs;
	
	@Override
	public void onCreate(){
		super.onCreate();
		
		prefs = getSharedPreferences(PREF_LOCATION_FILE_NAME, MODE_PRIVATE);
		String locations = prefs.getString(PREF_KEY, "");
		
		// Note the cities length will be 1 even when the locations string is empty
		// ( "" ) since splitting that will still return the empty string as is.
		String[] cities = locations.split("\\|");
		
		if(locations != ""){
			TimeZoneLookupService service =TimeZoneLookupService.getInstance(this);
			for(int i=0; i<cities.length; i++){
				LocationVO res = service.getTimeZoneForCity(cities[i]).get(0);
				res.initialize();
				addLocation(res);
			}
		}
	}
	
	private ArrayList<LocationVO> locations = new ArrayList<LocationVO>();
	public ArrayList<LocationVO> getMyLocations(){
		return locations;
	}
	
	public String getCitiesString(){
		String c = "";
		for(int i=0; i<locations.size(); i++){
			c+=locations.get(i).cityName+"|";
		}
		return c;
	}
	
	public void addLocation(LocationVO l){
		for(LocationVO v: locations){
			if(v.cityName == l.cityName){
				return;
			}
		}
		locations.add(l);
		savePreferences();
	}
	
	public void removeLocation(LocationVO loc){
		for(LocationVO v: locations){
			if(v == loc){
				locations.remove(v);
			}
		}
		savePreferences();
	}
	
	public void savePreferences(){
		String c = "";
		for(int i=0; i<locations.size(); i++){
			c+=locations.get(i).cityName+"|";
		}
		//
		Editor edit = prefs.edit();
		edit.putString(TimelyApp.PREF_KEY,c);
		edit.commit();
	}
}
