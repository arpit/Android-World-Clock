package com.arpitonline.worldclock;
	
import java.util.Timer;
import java.util.TimerTask;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.arpitonline.worldclock.models.LocationVO;

public class MyTimeZones extends ListActivity {
	
	private MyLocationsDataAdapter adapter;
	private ListView lv;
	private String[] menuItems = new String[]{"Remove"};
    
	private Timer t;
	private Handler handler;
	final Runnable doUpdateView = new Runnable() { 
	    public void run() {
	    	Log.i(TimelyApp.WORLD_CLOCK, "<update UI>");
	    	MyLocationsDataAdapter.INTRO_ANIMATION_ENABLED = false;
	    	adapter.notifyDataSetChanged();
	    } 
	  };
	
	@Override
	public void onAttachedToWindow() {
	    super.onAttachedToWindow();
	    Window window = getWindow();
	    window.setFormat(PixelFormat.RGBA_8888);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  
	  Log.i(TimelyApp.WORLD_CLOCK, "onCreateFired");
	  
	  requestWindowFeature(Window.FEATURE_NO_TITLE);
	  
	  lv = getListView();
	  
	  lv.addHeaderView(buildHeader());
	  lv.setTextFilterEnabled(true);
	
	  adapter = new MyLocationsDataAdapter(this, R.layout.world_list_item, ((TimelyApp)getApplication()).getMyLocations(), 
			  R.layout.world_list_item);
	  setListAdapter(adapter);
	  
	  lv.setOnItemClickListener(new OnItemClickListener() {
	    public void onItemClick(AdapterView<?> parent, View view,
	        int position, long id) {
	     
	    }
	  });
	  
	  registerForContextMenu(lv);
	  handler = new Handler(); 
	  createUpdateTimer();
	}
	
	private void createUpdateTimer(){
		TimerTask updateTimerTask = new TimerTask() { 
			public void run() { 
		        handler.post(doUpdateView); 
		    } 
		  };
		
		t = new Timer();
		t.scheduleAtFixedRate(updateTimerTask, 60000, 60000); 
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
		Log.i(TimelyApp.WORLD_CLOCK, "menu item clicked");
		
	    switch (item.getItemId()) {
	    case R.id.feedback_menuitem:
	    	Intent gotoFeedback = new Intent(Intent.ACTION_VIEW, 
	    			Uri.parse("http://www.arpitonline.com/worldclock/about/"));
	    	startActivity(gotoFeedback);
	        return true;
	    case R.id.about:
	    	Log.i(TimelyApp.WORLD_CLOCK, "menu:About");
	    	Intent gotoAbout = new Intent(Intent.ACTION_VIEW, 
	    			Uri.parse("http://www.arpitonline.com/worldclock/about/"));
	    	startActivity(gotoAbout);
	        return true;
	    case R.id.clear:
	    	Log.i(TimelyApp.WORLD_CLOCK, "menu:clear");
	    	//WorldClock.getInstance().getMyLocations().clear();
	    	adapter.clear();
	    	((TimelyApp)getApplication()).savePreferences();
	  	  	return true;
	    default:
	        return super.onOptionsItemSelected(item);
	    }
	}
	
	
	@Override 
	protected void onStop(){
		super.onStop();
		t.cancel();
		t = null;
	}
	
	@Override 
	protected void onResume(){
		super.onResume();
		if(t == null){
			try{
				createUpdateTimer();
			}catch(Exception e){
				Log.e(TimelyApp.WORLD_CLOCK, e.getMessage());
			}
		}
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		if (v == this.getListView()) {
			AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
			LocationVO loc = ((TimelyApp)getApplication()).getMyLocations().get(info.position-1);
			menu.setHeaderTitle(loc.cityName);
			// info.position -1 since the header actually counts as position 0
			for(int i=0; i<menuItems.length; i++){
		    	menu.add(Menu.NONE, i, i, menuItems[i]);
		    }
		}
	}
	
	public boolean onContextItemSelected(MenuItem item) {
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
		int menuItemIndex = item.getItemId();
		if(menuItemIndex==0){
			LocationVO loc = ((TimelyApp)getApplication()).getMyLocations().get(info.position-1);
			 ((TimelyApp)getApplication()).removeLocation(loc);
			adapter.notifyDataSetChanged();
		}
		return true;
	}
	
	private View buildHeader(){
		View v = View.inflate(this, R.layout.listview_header, null);
		ImageButton btn = (ImageButton)v.findViewById(R.id.search_button);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				onSearchRequested();
				
			}
		});
		return v;
	}
}
