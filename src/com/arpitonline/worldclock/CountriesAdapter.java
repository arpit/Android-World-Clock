package com.arpitonline.worldclock;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.arpitonline.worldclock.models.CountryVO;
import com.example.android.apis.R;

public class CountriesAdapter extends ArrayAdapter<CountryVO> {

    private ArrayList<CountryVO> items;

    public CountriesAdapter(Activity activity, int textViewResourceId, ArrayList<CountryVO> items) {
            super(activity, textViewResourceId, items);
            this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if (v == null) {
                LayoutInflater vi = (LayoutInflater)(getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE));
                v = vi.inflate(R.layout.world_list_item, null);
            }
            CountryVO o = items.get(position);
            if (o != null) {
                    TextView nameTF = (TextView) v.findViewById(R.id.nameTF);
                    if (nameTF != null) {
                    	nameTF.setText("Name: "+o.name);                            }
                    
            }
            return v;
    }
}