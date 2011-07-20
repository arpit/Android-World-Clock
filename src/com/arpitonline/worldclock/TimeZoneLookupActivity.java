package com.arpitonline.worldclock;

import java.util.ArrayList;
import java.util.TimeZone;

import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.arpitonline.worldclock.models.LocationVO;


public class TimeZoneLookupActivity extends ListActivity {
	
	private TimeZoneLookupService lookup;
	private ArrayList<LocationVO> searchResults = new ArrayList<LocationVO>();
	private CountriesAdapter searchAdapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    searchAdapter = new CountriesAdapter(this, R.layout.search_result_item, searchResults,R.layout.search_result_item);
	    setListAdapter(searchAdapter);
	    
	    //TimeZoneLookupService.DB_PATH = Environment.getExternalStorageDirectory().getAbsolutePath()+"/com.arpitonline.worldclock/";
		lookup = TimeZoneLookupService.getInstance(this);
		  
	    // Get the intent, verify the action and get the query
		Intent intent = getIntent();
		onNewIntent(intent);
	}
	
	@Override 
	protected void onNewIntent(Intent intent){
		searchAdapter.clear();
	    if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
	      String query = intent.getStringExtra(SearchManager.QUERY);
	      lookupTimeZone(query);
	    }
	    
	    ListView lv = getListView();
		lv.setTextFilterEnabled(true);
		
		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				LocationVO location = searchResults.get(position);
				TimeZone tz = location.getTimeZone();
				
				if(tz==null){
					Toast.makeText(view.getContext(), "Timezone not found", Toast.LENGTH_SHORT).show();
				}
				else{
					
					location.initialize();
					((TimelyApp)getApplication()).addLocation(location);
					
					addTimeZone(location.toString());
					
					
				}
			}
		});
	}
	
	private void addTimeZone(String s){
		Intent intent = new Intent(this, MyLocationsActivity.class);
		intent.putExtra("locationAdded",s );
		startActivity(intent);
		finish();
	}
	
	private void lookupTimeZone(String s){
		ArrayList<LocationVO> alist = lookup.getTimeZoneForCity(s);
		if(alist == null){
			Toast.makeText(this, "No results found", Toast.LENGTH_SHORT).show();
			return;
		}
		searchResults.addAll(alist);
	}
}
