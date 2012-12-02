package com.arpitonline.worldclock;

import java.util.ArrayList;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import com.arpitonline.worldclock.models.LocationVO;

public class TimelyPiece extends Application {
	
	public static final String WORLD_CLOCK = "world clock";
	public static final String PREF_LOCATION_FILE_NAME = "savedLocations";
	public static final String PREF_KEY = "locations";
	
	private static final String TAG = "TimelyPiece";
	private static final String APPLICATION_PREFS = "TimepiecePrefs";
	
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
	
	private boolean _isFirstRun ;
	
	public boolean isFirstRunOfNewVersion(){
		PackageInfo pInfo;
    	Context context = getApplicationContext();
    	
		
			try{
	    		pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_META_DATA);
	    		int lastRunVersionCode = prefs.getInt( "lastRunVersionCode", 0);
	    		
	    		Log.d(TAG, "copy Last run: "+lastRunVersionCode +", Version: "+pInfo.versionCode);
	    		
	    		if ( lastRunVersionCode < pInfo.versionCode ) {
	    			
	    			_isFirstRun = true;
	    		}
	    		else{
	    			_isFirstRun = false;
	    		}
	    		
	    	}catch(Exception e){
	    		Log.d(TAG, "Error checking version: "+e.getStackTrace());
	    		_isFirstRun = true;
	    	}
    	
		
		Log.d(TAG, "copy Is First Run? "+_isFirstRun);
		return _isFirstRun;
		
    }
	
	private void doOnFirstRun(){
		Log.d(TAG, "Setting Force copy");
		DatabaseHelper.FORCE_DATABASE_COPY = true;
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
			if(v.cityName.equals(l.cityName)){
				return;
			}
		}
		locations.add(l);
		savePreferences();
	}
	
	public void removeLocation(LocationVO loc){
		locations.remove(loc);
		savePreferences();
	}
	
	public void savePreferences(){
		String c = "";
		for(int i=0; i<locations.size(); i++){
			c+=locations.get(i).cityName+"|";
		}
		//
		Editor edit = prefs.edit();
		edit.putString(TimelyPiece.PREF_KEY,c);
		edit.commit();
	}
}
