package com.arpitonline.worldclock;

import java.util.ArrayList;

import org.joda.time.DateTime;
import org.joda.time.LocalTime;

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
        
        DateTime dateTime = o.getDateTime();
        LocalTime time = dateTime.toLocalTime();
        
        TextView cityTF = (TextView) v.findViewById(R.id.cityTF);
        cityTF.setText(o.cityName);                            

        TextView timeTF = (TextView) v.findViewById(R.id.currentTimeTF);
        timeTF.setText(o.getFormattedTime());
        
        TextView countryTF = (TextView) v.findViewById(R.id.dayTF);
        
        
        countryTF.setText(dateTime.dayOfWeek().getAsText());                            
        
        
        BitmapDrawable bg;
        
        
		if(time.getHourOfDay() > 18 || time.getHourOfDay() < 6){
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
