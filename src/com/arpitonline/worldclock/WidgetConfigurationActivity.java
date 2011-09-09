package com.arpitonline.worldclock;

import java.util.ArrayList;

import com.arpitonline.worldclock.models.LocationVO;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class WidgetConfigurationActivity extends ListActivity {
	
	private ListView lv;
	private ArrayAdapter<LocationVO> adapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  requestWindowFeature(Window.FEATURE_NO_TITLE);
	  
	  lv = getListView();
	  lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
	  final ArrayList<LocationVO> locations = ((TimelyPiece)getApplication()).getMyLocations();
	  adapter = new ArrayAdapter<LocationVO>(this, android.R.layout.simple_list_item_multiple_choice, locations){
		  @Override
		  public View getView(int position, View convertView, ViewGroup parent) {

		  TextView view = (TextView) super.getView(position, convertView, parent);
		  view.setText(locations.get(position).cityName);
		  return view;
		  }
	  };
	  setListAdapter(adapter);
	  
	  
	}
}
