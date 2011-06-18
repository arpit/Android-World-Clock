package com.arpitonline.worldclock.models;

public class CountryVO {
	public String countryName;
	public String cityName;
	public String backgroundImageURL;
	public String timezone;
	
	
	public CountryVO(String name, String backgroundImageURL){
		this.countryName = name;
		this.backgroundImageURL = backgroundImageURL;
	}
}
