package com.arpitonline.worldclock;

import java.util.ArrayList;

import org.joda.time.DateTime;
import org.joda.time.LocalTime;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.RelativeLayout.LayoutParams;

import com.arpitonline.utils.MathUtils;
import com.arpitonline.worldclock.models.LocationVO;

public class MyLocationsDataAdapter extends CountriesAdapter implements View.OnCreateContextMenuListener{
	
	public static boolean INTRO_ANIMATION_ENABLED = true;
	
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
            v.setOnCreateContextMenuListener(this);
      	  
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
        BitmapDrawable celestialImg;
        
        
        
		if(time.getHourOfDay() >= 18 || time.getHourOfDay() < 6){
			bg = (BitmapDrawable)this.getContext().getResources().getDrawable(R.drawable.night_bg);
			celestialImg = (BitmapDrawable)this.getContext().getResources().getDrawable(R.drawable.moon);
		}
		else{
			bg = (BitmapDrawable)this.getContext().getResources().getDrawable(R.drawable.day_bg);
			celestialImg = (BitmapDrawable)this.getContext().getResources().getDrawable(R.drawable.sun);
		}
		
		ImageView img = (ImageView)v.findViewById(R.id.backgroundImage);
		img.setImageDrawable(bg);
		
		final ImageView celestial = (ImageView)v.findViewById(R.id.celestial);
		celestial.setImageDrawable(celestialImg);
		
		//Log.i(TimelyApp.WORLD_CLOCK, "intro animation enabled? "+INTRO_ANIMATION_ENABLED);
		if(INTRO_ANIMATION_ENABLED == true){
			
			RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)celestial.getLayoutParams();
			params.rightMargin = MathUtils.randRange(25, 50);
			celestial.setLayoutParams(params);
			
			TranslateAnimation anim = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0,
					Animation.RELATIVE_TO_SELF, 10.0f, Animation.RELATIVE_TO_SELF, 0);
														
			anim.setDuration(2000);
			anim.setInterpolator(new DecelerateInterpolator());
			//anim.setFillAfter(true);
			
			anim.setAnimationListener(new Animation.AnimationListener() {
				
				@Override
				public void onAnimationStart(Animation animation) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onAnimationRepeat(Animation animation) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onAnimationEnd(Animation animation) {
					 MyLocationsDataAdapter.INTRO_ANIMATION_ENABLED = false;
					doRotationAnimation(celestial);
				}
			});
			
			celestial.setAnimation(anim);
		}else{
			//doRotationAnimation(celestial);
		}
		
		return v;
        
	}
	
	private void doRotationAnimation(ImageView celestial){
		RotateAnimation r =  new RotateAnimation(0, 40, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		r.setDuration((MathUtils.randRange(4, 8)/2)*1000);
		//r.setStartTime(Animation.START_ON_FIRST_FRAME+10000);
		r.setRepeatCount(Animation.INFINITE);
		r.setRepeatMode(Animation.REVERSE);
		
		celestial.clearAnimation();
		celestial.setAnimation(r);
		
	}
	

    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
     // empty implementation
    }
	
}
