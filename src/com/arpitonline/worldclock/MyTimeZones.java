package com.arpitonline.worldclock;
	
import java.util.ArrayList;
import java.util.Arrays;
import java.util.TimeZone;

import org.joda.time.DateTimeZone;

import android.app.ListActivity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.arpitonline.worldclock.models.LocationVO;

public class MyTimeZones extends ListActivity {
	
	private static final String PREF_LOCATION_FILE_NAME = "savedLocations";
	private static final String PREF_KEY = "locations";
	
	@Override
	public void onAttachedToWindow() {
	    super.onAttachedToWindow();
	    Window window = getWindow();
	    window.setFormat(PixelFormat.RGBA_8888);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  
	  Log.i(WorldClock.WORLD_CLOCK, "onCreateFired");
	  
	  requestWindowFeature(Window.FEATURE_NO_TITLE);
	  
	  ListView lv = getListView();
	  lv.addHeaderView(buildHeader());
	  lv.setTextFilterEnabled(true);
	  
	  SharedPreferences prefs = getSharedPreferences(PREF_LOCATION_FILE_NAME, MODE_PRIVATE);
	  String locations = prefs.getString(PREF_KEY, "");
	  
	  
	  String[] cities = locations.split("\\|");
	  
	  //Toast.makeText(this, "Read locations:("+cities.length+")"+locations, Toast.LENGTH_LONG).show();
	  
	  
	  Bundle extras = getIntent().getExtras();
	  if (extras == null) {
		// looks like first run!
		
		if(locations != ""){
			
			TimeZoneLookupService service =TimeZoneLookupService.getInstance(this);
			for(int i=0; i<cities.length; i++){
				LocationVO res = service.getTimeZoneForCity(cities[i]).get(0);
				res.initialize();
				WorldClock.getInstance().addLocation(res);
			}
		}
		//return;
	  }
	  else{
		  String addedLocation = extras.getString("locationAdded");
		  if(addedLocation != null && addedLocation.length() > 0 && 
				  Arrays.asList(cities).indexOf(addedLocation)==-1){
			  
			  Editor edit = prefs.edit();
			  edit.putString(PREF_KEY,WorldClock.getInstance().getCitiesString());
			  edit.commit();
			  Toast.makeText(this, "Added "+addedLocation, Toast.LENGTH_SHORT).show();
		  }
	  }
	  MyLocationsDataAdapter adapter = new MyLocationsDataAdapter(this, R.layout.world_list_item, WorldClock.getInstance().getMyLocations(), 
			  R.layout.world_list_item);
	  setListAdapter(adapter);
	  
	  lv.setOnItemClickListener(new OnItemClickListener() {
	    public void onItemClick(AdapterView<?> parent, View view,
	        int position, long id) {
	     
	    }
	  });
	}
	
	private View buildHeader(){
		View v = View.inflate(this, R.layout.listview_header, null);
		return v;
	}
	
	
}
