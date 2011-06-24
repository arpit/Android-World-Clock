package com.arpitonline.worldclock;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.arpitonline.worldclock.models.LocationVO;
import com.arpitonline.worldclock.R;

public class CountriesAdapter extends ArrayAdapter<LocationVO> {

    private ArrayList<LocationVO> items;
    private int renderer;
    
    public CountriesAdapter(Activity activity, int textViewResourceId, ArrayList<LocationVO> items, int renderer) {
            super(activity, textViewResourceId, items);
            this.renderer = renderer;
            this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if (v == null) {
                LayoutInflater vi = (LayoutInflater)(getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE));
                v = vi.inflate(renderer, null);
            }
            LocationVO o = items.get(position);
            if (o != null) {
                    TextView countryTF = (TextView) v.findViewById(R.id.countryTF);
                    if (countryTF != null) {
                    	countryTF.setText(o.countryName);                            }
                    
                    TextView cityTF = (TextView) v.findViewById(R.id.cityTF);
                    if (cityTF != null) {
                    	cityTF.setText(o.cityName);                            }
                    
                    
            }
            return v;
    }
}