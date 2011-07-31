package com.arpitonline.worldclock;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Uses the method mentioned in the link below to copy a database thats packaged
 * with the application itself.  
 * http://www.reigndesign.com/blog/using-your-own-sqlite-database-in-android-applications/
 * 
 * @author Arpit Mathur
 *
 */
public class DatabaseHelper extends SQLiteOpenHelper {
	
	public static String DB_PATH = "/data/data/com.arpitonline.worldclock/databases/";
	private String dbName = "world_time.db";
	
	
    protected SQLiteDatabase theDatabase; 
    private final Context context;
    
    public DatabaseHelper(Context c, String dbName){
    	super(c,dbName, null, 1);
    	this.dbName = dbName;
		this.context = c;
	}
    
    public void createDataBase() throws IOException{
    	boolean dbExist = checkDataBase();
    	if(dbExist){
    		//do nothing - database already exist
    	}else{
    		this.getReadableDatabase();
        	try {
    			copyDataBase();
    		} catch (IOException e) {
        		throw new Error("Error copying database");
        	}
    	}
    }
    
    private boolean checkDataBase(){
    	SQLiteDatabase checkDB = null;
    	try{
    		String path = DB_PATH + dbName;
    		checkDB = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
    	}catch(SQLiteException e){
    	}
    	if(checkDB != null){
    		checkDB.close();
    	}
    	return checkDB != null ? true : false;
    }
    
    private void copyDataBase() throws IOException{
    	Log.i(TimelyPiece.WORLD_CLOCK, "[copying database]");
    	File f = new File(DB_PATH);
    	f.mkdirs();
    	
    	InputStream myInput = context.getAssets().open(dbName);
    	String outFileName = DB_PATH + dbName;
    	OutputStream myOutput = new FileOutputStream(outFileName);
    	byte[] buffer = new byte[1024];
    	int length;
    	while ((length = myInput.read(buffer))>0){
    		myOutput.write(buffer, 0, length);
    	}
    	myOutput.flush();
    	myOutput.close();
    	myInput.close();
    }
    
    public void openDataBase() throws SQLException{
        String myPath = DB_PATH + dbName;
        theDatabase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY|SQLiteDatabase.NO_LOCALIZED_COLLATORS);
    }
 
    @Override
	public synchronized void close() {
    	if(theDatabase != null)
    		theDatabase.close();
    	super.close();
 	}
 
	@Override
	public void onCreate(SQLiteDatabase db) {
 
	}
 
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}
	
}
