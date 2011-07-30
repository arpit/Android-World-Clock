package com.arpitonline.worldclock;


import android.app.SearchManager;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

public class CitySearchSuggestionProvider extends ContentProvider {
	
	public static final String AUTHORITY = "com.arpitonline.worldclock.citysearchsuggestionprovider";
	public static final Uri CONTENT_URI = 
        Uri.parse("content://"+AUTHORITY);
	
	private static final int GET_CITY = 1;
	private static final UriMatcher sURIMatcher = buildUriMatcher();
	
	private TimeZoneLookupService service;
	@Override
	public boolean onCreate() {
		service = TimeZoneLookupService.getInstance(getContext());
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		/*Log.i(TimelyApp.WORLD_CLOCK, "query >"+uri.toString());
		Log.i(TimelyApp.WORLD_CLOCK, "path: "+uri.getPath());
		switch(sURIMatcher.match(uri)){
			case GET_CITY:
				 if (selectionArgs == null) {
					 throw new IllegalArgumentException(
	                      "selectionArgs must be provided for the Uri: " + uri);
				 }
	             return getSuggestions(selectionArgs[0]);
			default:
				throw new IllegalArgumentException("Unknown Uri: " + uri);
		}
		*/
		String query = uri.getLastPathSegment().toLowerCase();
		return getSuggestions(query);
	}
	
	 private Cursor getSuggestions(String query) {	
	      query = query.toLowerCase();
	      Log.i(TimelyApp.WORLD_CLOCK, "=> doing search for "+query);
	      return service.getCursorForQuery(query);
	    }
	

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	private static UriMatcher buildUriMatcher(){
		UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
		matcher.addURI(AUTHORITY, null, GET_CITY);
		matcher.addURI(AUTHORITY, SearchManager.SUGGEST_URI_PATH_QUERY, GET_CITY);
		matcher.addURI(AUTHORITY, SearchManager.SUGGEST_URI_PATH_QUERY+"/*", GET_CITY);
		return matcher;
	}
	
	// WE DONT DO THESE ANYWAY:
	@Override
	public int delete(Uri arg0, String arg1, String[] arg2) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		// TODO Auto-generated method stub
		return null;
	}

}
