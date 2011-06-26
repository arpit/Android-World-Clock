package com.arpitonline.worldclock;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
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
        countryTF.setText(o.countryName);                            
        
        TextView cityTF = (TextView) v.findViewById(R.id.cityTF);
        cityTF.setText(o.cityName);                            

        TextView timeTF = (TextView) v.findViewById(R.id.currentTimeTF);
        timeTF.setText(o.getFormattedTime());
        
        
        BitmapDrawable bg;
		if(o.getTime().getHourOfDay() > 18 || o.getTime().getHourOfDay() < 6){
			bg = (BitmapDrawable)this.getContext().getResources().getDrawable(R.drawable.night_bg);
		}
		else{
			bg = (BitmapDrawable)this.getContext().getResources().getDrawable(R.drawable.day2_bg);
		}
		
		ImageView img = (ImageView)v.findViewById(R.id.backgroundImage);
		img.setImageDrawable(bg);
		
		ScrollView sc = (ScrollView)v.findViewById(R.id.scrollview);
		int pos = (int)(100);
		//sc.scrollTo(-pos, 0);
		
		return v;
        
	}
}
