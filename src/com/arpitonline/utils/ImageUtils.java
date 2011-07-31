package com.arpitonline.utils;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Log;

import com.arpitonline.worldclock.TimelyPiece;

public class ImageUtils {
	public static Bitmap getBitmapForImageAtURL(String fileUrl) {
		
		URL myFileUrl =null;          
        Bitmap bmImg = null ;
        
		try{
          myFileUrl= new URL(fileUrl);
          HttpURLConnection conn= (HttpURLConnection)myFileUrl.openConnection();
          conn.setDoInput(true);
          conn.connect();
          InputStream is = conn.getInputStream();
          bmImg = BitmapFactory.decodeStream(new SanInputStream(is));
          conn.disconnect();
		}catch(Exception ex){
			Log.e(TimelyPiece.WORLD_CLOCK, "Image to Bitmap Error "+ex.getClass().getName());
		}
		return bmImg;
     }
	
	public static Bitmap getScaledAndRotatedBitmap(Bitmap src, float scaleX, float scaleY, float rotation){
		Matrix m = new Matrix();
		m.postRotate(rotation);
		m.postScale(scaleX, scaleY);
		Bitmap newBitmap = Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), m, false);
		return newBitmap;
	}
	
	
}


