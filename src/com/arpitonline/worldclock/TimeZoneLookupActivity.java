package com.arpitonline.worldclock;

import java.util.ArrayList;

import com.arpitonline.worldclock.models.CountryVO;
import com.example.android.apis.R;

import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;


public class TimeZoneLookupActivity extends ListActivity {
	
	private TimeZoneLookupService lookup;
	private ArrayList<CountryVO> searchResults = new ArrayList<CountryVO>();
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    //setContentView(R.layout.search);
	    Log.i(WorldClock.WORLD_CLOCK, "------beginning search-----"); 
	    
	    setListAdapter(new CountriesAdapter(this, R.layout.search_result_item, searchResults,R.layout.search_result_item));
	    
	    TimeZoneLookupService.DB_PATH = Environment.getExternalStorageDirectory().getAbsolutePath()+"/com.arpitonline.worldclock/";
		lookup = new TimeZoneLookupService(this);
		  
	    // Get the intent, verify the action and get the query
	    Intent intent = getIntent();
	    if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
	      String query = intent.getStringExtra(SearchManager.QUERY);
	      lookupTimeZone(query);
	    }
	}
	
	private void lookupTimeZone(String s){
		ArrayList<CountryVO> alist = lookup.getTimeZoneForCountry(s);
		if(alist == null){
			Toast.makeText(this, "No results found", Toast.LENGTH_SHORT).show();
			return;
		}
		searchResults.addAll(alist);
	}
}
