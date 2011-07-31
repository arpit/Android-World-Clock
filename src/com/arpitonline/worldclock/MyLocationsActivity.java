package com.arpitonline.worldclock;
	
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.arpitonline.worldclock.models.LocationVO;

public class MyLocationsActivity extends ListActivity {
	
	private Dialog introDialog;
	private MyLocationsDataAdapter adapter;
	private ListView lv;
	private String[] menuItems = new String[]{"Remove"};
    
	private Timer t;
	private Handler handler;
	final Runnable doUpdateView = new Runnable() { 
	    public void run() {
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
	  requestWindowFeature(Window.FEATURE_NO_TITLE);
	  
	  lv = getListView();
	  
	  lv.addHeaderView(buildHeader());
	  lv.setTextFilterEnabled(true);
	
	  ArrayList<LocationVO> locations = ((TimelyApp)getApplication()).getMyLocations();
	  adapter = new MyLocationsDataAdapter(this, R.layout.world_list_item, 
			  locations, 
			  R.layout.world_list_item);
	  setListAdapter(adapter);
	  
	  if(locations.size() == 0){
		  if(introDialog == null){
			  //, android.R.style.Theme_Translucent_NoTitleBar
			  introDialog = new Dialog(this, android.R.style.Theme_Panel);
			  introDialog.setContentView(R.layout.intro);
			  
			  Window window = introDialog.getWindow();
	          window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
	                    WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
	            window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
	            //window.setGravity(Gravity.BOTTOM);
	            
			  
		  }
		  introDialog.show();
	  }
	  else{
		  if(introDialog != null){
			  introDialog.dismiss();
			  introDialog = null;
		  }
	  }
	  
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
	    switch (item.getItemId()) {
	    case R.id.feedback_menuitem:
	    	Intent gotoFeedback = new Intent(Intent.ACTION_VIEW, 
	    			Uri.parse(getString(R.string.feedback_url)));
	    	startActivity(gotoFeedback);
	        return true;
	    case R.id.about:
	    	
	    	
	    	LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
	    	View layout = inflater.inflate(R.layout.about_dialog,
	    	                               (ViewGroup) findViewById(R.id.layout_root));

	    	
	    	TextView t1 = (TextView) layout.findViewById(R.id.by);
	    	t1.setMovementMethod(LinkMovementMethod.getInstance());
	    	t1.setText(Html.fromHtml(getString(R.string.developed_by)));
	    	
	    	TextView t2 = (TextView) layout.findViewById(R.id.feedback_at);
	    	t2.setMovementMethod(LinkMovementMethod.getInstance());
	    	t2.setText(Html.fromHtml(getString(R.string.feedback_at)));
	    	
	    	TextView t3 = (TextView) layout.findViewById(R.id.source_at);
	    	t3.setMovementMethod(LinkMovementMethod.getInstance());
	    	t3.setText(Html.fromHtml(getString(R.string.source_at)));
	    	
	    	TextView versionTxt =  (TextView) layout.findViewById(R.id.version);
	    	
	    	try{
	    		PackageManager manager = this.getPackageManager();
	    		PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
	    		versionTxt.setText("Version: "+ info.versionName);
	    	}catch(Exception e){
	    		versionTxt.setText("x");
	    	}
	       
	    	
	    	
	    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
	    	builder.setView(layout);
	    	builder.setPositiveButton("Cool", null);
	    	AlertDialog alert = builder.create();
	    	alert.setTitle("About "+getString(R.string.app_name));
	    	
	    	alert.show();
	    	
	        return true;
	    case R.id.clear:
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
			 adapter.removeFromAnimatedObjects(loc);
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
				if(introDialog != null){
					introDialog.dismiss();
					introDialog = null;
				}
				onSearchRequested();
				
			}
		});
		return v;
	}
}
