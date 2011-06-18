package com.arpitonline.worldclock;
	
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import android.app.ListActivity;
import android.database.SQLException;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.arpitonline.worldclock.models.CountryVO;
import com.example.android.apis.R;

public class AllTimeZones extends ListActivity {
	
	
	private ArrayList<CountryVO> COUNTRIES = new ArrayList<CountryVO>();
	private TimeZoneLookupService lookup;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  
	  TimeZoneLookupService.DB_PATH = Environment.getExternalStorageDirectory().getAbsolutePath()+"/com.arpitonline.worldclock/";
		
	  lookup = new TimeZoneLookupService(this);
	  
	  Log.i(WorldClock.WORLD_CLOCK, "--done opening db---");
	  
	  COUNTRIES.add(new CountryVO("India","" ));	
	  COUNTRIES.add(new CountryVO("USA","" ));	

	  
	  
	  setListAdapter(new CountriesAdapter(this, R.layout.world_list_item, COUNTRIES));

	  ListView lv = getListView();
	  lv.setTextFilterEnabled(true);

	  
	  lv.setOnItemClickListener(new OnItemClickListener() {
	    public void onItemClick(AdapterView<?> parent, View view,
	        int position, long id) {
	      // When clicked, show a toast with the TextView text
	      //Toast.makeText(getApplicationContext(), ((TextView) view).getText(),
	         // Toast.LENGTH_SHORT).show();
	    	
	    	lookupTimeZone("India");
	    }
	  });
	}
	
	private void lookupTimeZone(String s){
		String ret = lookup.getTimeZoneFor(s);
		Log.i(WorldClock.WORLD_CLOCK, "Return: "+ret);
		
		Toast.makeText(this, ret, Toast.LENGTH_SHORT);
	}
	
}
