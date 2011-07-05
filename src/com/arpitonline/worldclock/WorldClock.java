package com.arpitonline.worldclock;

import java.util.ArrayList;

import com.arpitonline.worldclock.models.LocationVO;

/**
 * Main business logic class for the app 
 */
public class WorldClock {

	public static final String WORLD_CLOCK = "world clock";
	public static final String PREF_LOCATION_FILE_NAME = "savedLocations";
	public static final String PREF_KEY = "locations";
	
	private ArrayList<LocationVO> locations = new ArrayList<LocationVO>();
	
	private WorldClock(){
		super();
	}
	
	
	private static WorldClock instance;
	public static WorldClock getInstance(){
		if(instance == null){
			instance = new WorldClock();
		}
		
		return instance;
	}
	
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
	}
}
