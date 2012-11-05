package com.arpitonline.worldclock;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockListFragment;
import com.arpitonline.worldclock.models.LocationVO;

public class MyLocationsFragment extends SherlockListFragment {

	private MyLocationsDataAdapter adapter;
	private Timer t;
	private Handler handler;
	private ListView lv;
	
	final Runnable doUpdateView = new Runnable() {
		public void run() {
			adapter.notifyDataSetChanged();
		}
	};
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		
		lv = getListView();

		lv.addHeaderView(buildHeader());
		lv.setTextFilterEnabled(true);

		ArrayList<LocationVO> locations = ((TimelyPiece) getActivity().getApplication())
				.getMyLocations();
		adapter = new MyLocationsDataAdapter(
				(this.getActivity()),
				R.layout.world_list_item, locations, R.layout.world_list_item);
		setListAdapter(adapter);

		// if(locations.size() == 0){
		// if(introDialog == null){
		// //, android.R.style.Theme_Translucent_NoTitleBar
		// introDialog = new Dialog(this, android.R.style.Theme_Panel);
		// introDialog.setContentView(R.layout.intro);
		//
		// Window window = introDialog.getWindow();
		// window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
		// WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
		// window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
		// //window.setGravity(Gravity.BOTTOM);
		//
		//
		// }
		// introDialog.show();
		// }
		// else{
		// if(introDialog != null){
		// introDialog.dismiss();
		// introDialog = null;
		// }
		// }

//		lv.setOnItemClickListener(new OnItemClickListener() {
//			public void onItemClick(AdapterView<?> parent, View view,
//					int position, long id) {
//
//			}
//		});
//
//		registerForContextMenu(lv);
		handler = new Handler();
		createUpdateTimer();
	}

	private void createUpdateTimer() {
		TimerTask updateTimerTask = new TimerTask() {
			public void run() {
				handler.post(doUpdateView);
			}
		};

		t = new Timer();
		t.scheduleAtFixedRate(updateTimerTask, 60000, 60000);
	}

	@Override
	public void onResume() {
		super.onResume();
		if (t == null) {
			try {
				createUpdateTimer();
			} catch (Exception e) {
				Log.e(TimelyPiece.WORLD_CLOCK, e.getMessage());
			}
		}
	}

	@Override
	public void onStop() {
		t.cancel();
		t = null;
		super.onStop();

	}
	
	private View buildHeader(){
		View v = View.inflate(getActivity(), R.layout.listview_header, null);
		ImageButton btn = (ImageButton)v.findViewById(R.id.search_button);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				if(introDialog != null){
//					introDialog.dismiss();
//					introDialog = null;
//				}
				getActivity().onSearchRequested();
				
			}
		});
		return v;
	}

}
