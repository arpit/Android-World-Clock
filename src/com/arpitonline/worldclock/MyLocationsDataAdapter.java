package com.arpitonline.worldclock;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.arpitonline.worldclock.models.LocationVO;

public class MyLocationsDataAdapter extends CountriesAdapter{
	
	public MyLocationsDataAdapter (Activity activity, int textViewResourceId, ArrayList<LocationVO> items, int renderer){
		 super(activity, textViewResourceId, items, renderer);
	}
	
	public View getView(int position, View convertView, ViewGroup parent) {
		LocationVO o = items.get(position);
        if(o==null){
        	return null;
        }
		
        
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater)(getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE));
            v = vi.inflate(renderer, null);
        }
        
        
        TextView countryTF = (TextView) v.findViewById(R.id.countryTF);
        if (countryTF != null) {
        	countryTF.setText(o.countryName);                            }
        
        TextView cityTF = (TextView) v.findViewById(R.id.cityTF);
        if (cityTF != null) {
        	cityTF.setText(o.cityName);                            }

        BitmapDrawable bg;
		if(o.getTime().getHourOfDay() > 18 || o.getTime().getHourOfDay() < 6){
			bg = (BitmapDrawable)this.getContext().getResources().getDrawable(R.drawable.night_bg);
		}
		else{
			bg = (BitmapDrawable)this.getContext().getResources().getDrawable(R.drawable.day_bg);
		}
		
		ImageView img = (ImageView)v.findViewById(R.id.backgroundImage);
		img.setImageDrawable(bg);
		
		//v.setBackgroundDrawable(bg);
		TextView timeTF = (TextView) v.findViewById(R.id.currentTimeTF);
        if (timeTF != null) {
           timeTF.setText(o.getFormattedTime());
        }
        return v;
        
	}
}
