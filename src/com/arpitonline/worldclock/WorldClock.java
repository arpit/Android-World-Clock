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
import com.example.android.apis.R;
import com.example.android.apis.R.id;
import com.example.android.apis.R.layout;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

public class WorldClock extends Activity {
	
	public static final String WORLD_CLOCK = "world clock";
	
    private Flickr flickr;
    private static final String key = "cc978b462c274d7c88275fb2bf45240a";
    private static final String secret = "dfc31ad697d55707";
    
    private RequestContext requestContext;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Log.i(WORLD_CLOCK, "begin App");
        
        try{
        	flickr = new Flickr(key, secret, new REST());
        }catch(ParserConfigurationException ex){
        	Log.e(WORLD_CLOCK, "ParserConfigException creating Flickr service: "+ex.getStackTrace());
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
    		Log.i(WORLD_CLOCK, "photos size > "+photos.size() );
    		for(int i=0; i<photos.size(); i++){
    			Photo p = (Photo)photos.get(i);
    			Log.i(WORLD_CLOCK, p.getUrl());
    		}
    		
    		ImageView img = (ImageView)findViewById(R.id.imageView1);
        	Photo p = (Photo)photos.get(0);
        	
        	String photoURL = p.getMediumUrl();
        	Log.i(WORLD_CLOCK, "<>"+photoURL);
        	
        	Bitmap bmp = ImageUtils.getBitmapForImageAtURL(photoURL);
        	Log.i(WORLD_CLOCK, "<=>"+bmp);
        	
        	img.setImageBitmap(bmp);
        	
    		
    	}
    	catch(FlickrException fex){
    		Log.e(WORLD_CLOCK, "Flickr Exception > "+fex.getMessage());
    	}
    	catch(IOException fex){
    		Log.e(WORLD_CLOCK, "IOException > "+fex.getMessage());
    	}
    	catch(SAXException fex){
    		Log.e(WORLD_CLOCK, "SAXException > "+fex.getMessage());
    	}
    	
    	
    }
}