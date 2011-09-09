package com.arpitonline.worldclock;

import java.util.ArrayList;

import com.arpitonline.worldclock.models.LocationVO;

import android.R.bool;
import android.app.Activity;
import android.app.ListActivity;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.RemoteViews.RemoteView;
import android.view.View.OnClickListener;

public class WidgetConfigurationActivity extends ListActivity {
	
	private ListView lv;
	private ArrayAdapter<LocationVO> adapter;
	private int mAppWidgetId;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  
	  Intent intent = getIntent();
	  Bundle extras = intent.getExtras();
	  if (extras != null) {
	      mAppWidgetId = extras.getInt(
	              AppWidgetManager.EXTRA_APPWIDGET_ID, 
	              AppWidgetManager.INVALID_APPWIDGET_ID);
	  }
	  
	  
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
	  
	  
	 lv.setOnItemClickListener(new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			SparseBooleanArray checkedPos = lv.getCheckedItemPositions();
			for(int i=0; i<checkedPos.size(); i++){
				boolean s  = checkedPos.get(i);
				Log.i("...",i+" : "+s);
			}
			
		}
		 
	});
	
         
	 /*
	  
	  Button addButton = new Button(this);
	  addButton.setText("Add");
	  addButton.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, 35));
	  lv.addFooterView(addButton, null, true);
	  
	  */
	  View footer = getLayoutInflater().inflate(R.layout.widget_config_footer, null);
	  lv.addFooterView(footer);
	  setListAdapter(adapter);
	  
	  Button done = (Button)findViewById(R.id.widget_add_button);
	  final Activity self = this;
	  done.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(self);
			RemoteViews views = new RemoteViews(self.getPackageName(),R.layout.app_widget);
			appWidgetManager.updateAppWidget(mAppWidgetId, views);
			
			Intent resultValue = new Intent();
			resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
			setResult(RESULT_OK, resultValue);
			finish();
		}
	});
	  
	  
	}
}
