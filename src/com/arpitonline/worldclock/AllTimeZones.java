package com.arpitonline.worldclock;
	
import java.util.ArrayList;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.arpitonline.worldclock.models.LocationVO;
import com.arpitonline.worldclock.R;

public class AllTimeZones extends ListActivity {
	
	
	private ArrayList<LocationVO> locations = new ArrayList<LocationVO>();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  
	  Log.i(WorldClock.WORLD_CLOCK, "--done opening db---");
	  setListAdapter(new CountriesAdapter(this, R.layout.world_list_item, locations, 
			  R.layout.world_list_item));

	  ListView lv = getListView();
	  lv.setTextFilterEnabled(true);

	  
	  lv.setOnItemClickListener(new OnItemClickListener() {
	    public void onItemClick(AdapterView<?> parent, View view,
	        int position, long id) {
	      // When clicked, show a toast with the TextView text
	      //Toast.makeText(getApplicationContext(), ((TextView) view).getText(),
	         // Toast.LENGTH_SHORT).show();
	    	
	    
	    }
	  });
	}
	
	
	
}
