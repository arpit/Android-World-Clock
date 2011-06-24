package com.arpitonline.worldclock;

import java.util.ArrayList;

import com.arpitonline.worldclock.models.LocationVO;

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
	
	public void addLocation(LocationVO l){
		locations.add(l);
	}
}
