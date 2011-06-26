package com.arpitonline.worldclock;
	
import java.util.ArrayList;

import android.app.ListActivity;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.arpitonline.worldclock.models.LocationVO;

public class MyTimeZones extends ListActivity {
	
	@Override
	public void onAttachedToWindow() {
	    super.onAttachedToWindow();
	    Window window = getWindow();
	    window.setFormat(PixelFormat.RGBA_8888);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  
	  
	  MyLocationsDataAdapter adapter = new MyLocationsDataAdapter(this, R.layout.world_list_item, WorldClock.getInstance().getMyLocations(), 
			  R.layout.world_list_item);
	  setListAdapter(adapter);
	  
	  ListView lv = getListView();
	  lv.setDividerHeight(0);
	  lv.setTextFilterEnabled(true);

	  
	  lv.setOnItemClickListener(new OnItemClickListener() {
	    public void onItemClick(AdapterView<?> parent, View view,
	        int position, long id) {
	      // When clicked, show a toast with the TextView text
	      //Toast.makeText(getApplicationContext(), ((TextView) view).getText(),
	         // Toast.LENGTH_SHORT).show();
	    }
	  });
	  
	  Bundle extras = getIntent().getExtras();
	  if (extras == null) {
		return;
	  }
	  else{
		  String addedLocation = extras.getString("locationAdded");
		  Toast.makeText(this, "Added "+addedLocation , Toast.LENGTH_SHORT).show();
	  }
	  
	}
	
	
	
}
