package com.arpitonline.worldclock;
	
import java.util.ArrayList;
import java.util.Arrays;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

import org.joda.time.DateTimeZone;

import android.app.ListActivity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
	
	private MyLocationsDataAdapter adapter;
	private ListView lv;
	
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
	  
	  lv = getListView();
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
	  adapter = new MyLocationsDataAdapter(this, R.layout.world_list_item, WorldClock.getInstance().getMyLocations(), 
			  R.layout.world_list_item);
	  setListAdapter(adapter);
	  
	  lv.setOnItemClickListener(new OnItemClickListener() {
	    public void onItemClick(AdapterView<?> parent, View view,
	        int position, long id) {
	     
	    }
	  });
	  
	  final Handler handler = new Handler(); 
	  final Runnable doUpdateView = new Runnable() { 
	    public void run() {
	    	Log.i(WorldClock.WORLD_CLOCK, "<update UI>");
	    	
	    	ArrayList<LocationVO> aList = WorldClock.getInstance().getMyLocations();
			for(int i=0; i<aList.size(); i++){
				LocationVO vo = aList.get(i);
				//vo.updateTime();
				Log.i(WorldClock.WORLD_CLOCK, "=> "+vo.getFormattedTime());
			}
	    	adapter.notifyDataSetChanged();
	    } 
	  }; 

	  TimerTask myTimerTask = new TimerTask() { 
	    public void run() { 
	        handler.post(doUpdateView); 
	    } 
	  };
	  
	  Timer t = new Timer();
	  t.scheduleAtFixedRate(myTimerTask, 0, 60000); 
	}
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.my_locations_menu, menu);
	    return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
		Log.i(WorldClock.WORLD_CLOCK, "menu item clicked");
		
	    switch (item.getItemId()) {
	    case R.id.exit:
	    	Log.i(WorldClock.WORLD_CLOCK, "finishing");
	        finish();
	        return true;
	    case R.id.about:
	    	Log.i(WorldClock.WORLD_CLOCK, "menu:About");
	        // send to arpitonline.com
	        return true;
	    case R.id.clear:
	    	Log.i(WorldClock.WORLD_CLOCK, "menu:clear");
	    	//WorldClock.getInstance().getMyLocations().clear();
	    	adapter.clear();
	    	SharedPreferences prefs = getSharedPreferences(PREF_LOCATION_FILE_NAME, MODE_PRIVATE);
	  	  	Editor e = prefs.edit();
	  	  	e.putString(PREF_KEY, "");
	  	  	e.commit();
	  	  	return true;
	    default:
	        return super.onOptionsItemSelected(item);
	    }
	}
	
	private View buildHeader(){
		View v = View.inflate(this, R.layout.listview_header, null);
		return v;
	}
}
