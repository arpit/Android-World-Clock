package com.arpitonline.worldclock;

import java.util.ArrayList;

import com.arpitonline.worldclock.models.LocationVO;

/**
 * Main business logic class for the application 
 */
public class WorldClock {
	
	public static final String WORLD_CLOCK = "world clock";
	
	
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

}
