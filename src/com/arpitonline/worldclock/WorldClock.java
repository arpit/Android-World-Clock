package com.arpitonline.worldclock;

import java.util.ArrayList;

import com.arpitonline.worldclock.models.LocationVO;

/**
 * Main business logic class for the app 
 */
public class WorldClock {

	public static final String WORLD_CLOCK = "world clock";
	
	private ArrayList<LocationVO> locations = new ArrayList<LocationVO>();
	
	private WorldClock(){
		
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
		locations.add(l);
	}
}
