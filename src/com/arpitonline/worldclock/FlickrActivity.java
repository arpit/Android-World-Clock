package com.arpitonline.worldclock;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.aetrion.flickr.Flickr;
import com.aetrion.flickr.FlickrException;
import com.aetrion.flickr.REST;
import com.aetrion.flickr.RequestContext;
import com.aetrion.flickr.auth.Auth;
import com.aetrion.flickr.auth.AuthInterface;
import com.aetrion.flickr.auth.Permission;
import com.aetrion.flickr.photos.Photo;
import com.aetrion.flickr.photos.PhotoList;
import com.aetrion.flickr.photos.PhotosInterface;
import com.aetrion.flickr.photos.SearchParameters;
import com.arpitonline.utils.ImageUtils;
import com.arpitonline.worldclock.R;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

public class FlickrActivity extends Activity {
	
    private Flickr flickr;
    private static final String key = "";
    private static final String secret = "";
    
    private RequestContext requestContext;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Log.i(WorldClock.WORLD_CLOCK, "begin App");
        
        try{
        	flickr = new Flickr(key, secret, new REST());
        }catch(ParserConfigurationException ex){
        	Log.e(WorldClock.WORLD_CLOCK, "ParserConfigException creating Flickr service: "+ex.getStackTrace());
        }
        requestContext = RequestContext.getRequestContext();
       
        Auth auth = new Auth();
        auth.setPermission(Permission.READ);
        auth.setToken(null);
        requestContext.setAuth(auth);
        Flickr.debugRequest = false;
        Flickr.debugStream = false;
        
       doSearch();
    }
    
    private void doSearch(){
    	PhotosInterface photoIf = flickr.getPhotosInterface();
    	SearchParameters params = new SearchParameters();
    	params.setText("Paris");
    	params.setSort(SearchParameters.INTERESTINGNESS_DESC);
    	
    	try{
    		PhotoList photos = photoIf.search(params, 5, 0);
    		Log.i(WorldClock.WORLD_CLOCK, "photos size > "+photos.size() );
    		for(int i=0; i<photos.size(); i++){
    			Photo p = (Photo)photos.get(i);
    			Log.i(WorldClock.WORLD_CLOCK, p.getUrl());
    		}
    		
    		ImageView img = (ImageView)findViewById(R.id.imageView1);
        	Photo p = (Photo)photos.get(0);
        	
        	String photoURL = p.getMediumUrl();
        	Log.i(WorldClock.WORLD_CLOCK, "<>"+photoURL);
        	
        	Bitmap bmp = ImageUtils.getBitmapForImageAtURL(photoURL);
        	Log.i(WorldClock.WORLD_CLOCK, "<=>"+bmp);
        	
        	img.setImageBitmap(bmp);
        	
    		
    	}
    	catch(FlickrException fex){
    		Log.e(WorldClock.WORLD_CLOCK, "Flickr Exception > "+fex.getMessage());
    	}
    	catch(IOException fex){
    		Log.e(WorldClock.WORLD_CLOCK, "IOException > "+fex.getMessage());
    	}
    	catch(SAXException fex){
    		Log.e(WorldClock.WORLD_CLOCK, "SAXException > "+fex.getMessage());
    	}
    	
    	
    }
}