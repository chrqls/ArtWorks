package com.artworks.data;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;

public class BeaconSQLiteHelper extends SQLiteOpenHelper {
	 
    // Database Version
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "artworkstest";   
   
    private static final String TABLE_BEACONS = "beacons";

    private static final String KEY_ID = "id";
    private static final String KEY_UUID = "uuid";
    private static final String KEY_MAJOR = "major";
    private static final String KEY_MINOR = "minor";
    private static final String KEY_ARTWORK_ID = "artworkid";
	  
    
    private static final String[] COLUMNS = {
    	KEY_ID,
    	KEY_UUID,
    	KEY_MAJOR,
    	KEY_MINOR,
    	KEY_ARTWORK_ID};
 
    public BeaconSQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION); 
    }
 
    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL statement to create book table
        String CREATE_TABLE = "CREATE TABLE "+ TABLE_BEACONS +" ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_UUID+ " TEXT, "+
                KEY_MAJOR+ " INTEGER, "+
                KEY_MINOR+ " INTEGER, "+
                KEY_ARTWORK_ID+ " INTEGER )";
 
        
        // create books table
        db.execSQL(CREATE_TABLE);
    }
 
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older books table if existed
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_BEACONS);
 
        // create fresh books table
        this.onCreate(db);
    }
    
    public void addBeacon(Beacon beacon){

    	SQLiteDatabase db = this.getWritableDatabase();

    	ContentValues values = new ContentValues();
    	values.put(KEY_UUID, beacon.getUUID()); 
    	values.put(KEY_MAJOR, beacon.getMajor()); 
    	values.put(KEY_MINOR, beacon.getMinor());
    	values.put(KEY_ARTWORK_ID, beacon.getArtworkId());
    	
    	db.insert(TABLE_BEACONS, 
        null, //nullColumnHack
        values); // key/value -> keys = column names/ values = column values

    	// 4. close
    	db.close();
    }
    
    public Beacon getBeacon(int id){
    	 
        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();
     
        // 2. build query
        Cursor cursor =
                db.query(TABLE_BEACONS, // a. table
                COLUMNS, // b. column names
                " id = ?", // c. selections
                new String[] { String.valueOf(id) }, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit
     
        // 3. if we got results get the first one
        if (cursor != null)
            cursor.moveToFirst();
     
        Beacon beacon = new Beacon();
        beacon.setId(Integer.parseInt(cursor.getString(0)));
        beacon.setUUID(cursor.getString(1));
        beacon.setMajor(Integer.parseInt(cursor.getString(2)));
        beacon.setMinor(Integer.parseInt(cursor.getString(2)));
        beacon.setArtworkId(Integer.parseInt(cursor.getString(3)));
        return beacon;
    }
    
    public Beacon getBeacon(String uuid, int major, int minor){
   	 
        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();
     
        // 2. build query
        Cursor cursor =
        		
                db.query(TABLE_BEACONS, 
                COLUMNS, 
                KEY_UUID+" = "+uuid+" AND "+KEY_MAJOR+" = "+major+ " AND "+KEY_MINOR+ " = "+minor, 
                null, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit
     
        // 3. if we got results get the first one
        if (cursor != null)
            cursor.moveToFirst();
     
        
        	Beacon beacon = new Beacon();
        	beacon.setId(Integer.parseInt(cursor.getString(0)));
        	beacon.setUUID(cursor.getString(1));
        	beacon.setMajor(Integer.parseInt(cursor.getString(2)));
        	beacon.setMinor(Integer.parseInt(cursor.getString(2)));
        	beacon.setArtworkId(Integer.parseInt(cursor.getString(3)));
        return beacon;
    }
    
    
    public Beacon getBeacon(String uuid){
      	 
        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();
     
        // 2. build query
        Cursor cursor =
        		
                db.query(TABLE_BEACONS, 
                COLUMNS, 
                KEY_UUID+" = "+uuid, 
                null, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit
     
        if (cursor != null)
            cursor.moveToFirst();
     
        
        	Beacon beacon = new Beacon();
        	beacon.setId(Integer.parseInt(cursor.getString(0)));
        	beacon.setUUID(cursor.getString(1));
        	beacon.setMajor(Integer.parseInt(cursor.getString(2)));
        	beacon.setMinor(Integer.parseInt(cursor.getString(2)));
        	beacon.setArtworkId(Integer.parseInt(cursor.getString(3)));
        return beacon;
    }
    
    
    public List<Beacon> getAllBeacons() {
        List<Beacon> beacons = new LinkedList<Beacon>();
  
        // 1. build the query
        String query = "SELECT  * FROM " + TABLE_BEACONS;
  
        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
  
        // 3. go over each row, build book and add it to list
        Beacon beacon = null;
        if (cursor.moveToFirst()) {
            do {
            	beacon = new Beacon();
            	beacon.setId(Integer.parseInt(cursor.getString(0)));
            	beacon.setUUID(cursor.getString(1));
            	beacon.setMajor(Integer.parseInt(cursor.getString(2)));
            	beacon.setMinor(Integer.parseInt(cursor.getString(3)));
            	beacon.setArtworkId(Integer.parseInt(cursor.getString(4)));
                beacons.add(beacon);
            } while (cursor.moveToNext());
        }
  
        Log.d("getAllBeacons()", beacons.toString());
 
        return beacons;
    }
    
    public int updateBeacon(Beacon beacon) {
    	 
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
     
        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_UUID, beacon.getUUID()); 
        values.put(KEY_MAJOR, beacon.getMajor()); 
        values.put(KEY_MINOR, beacon.getMinor()); 
        values.put(KEY_UUID, beacon.getArtworkId()); 
    	
        // 3. updating row
        int i = db.update(TABLE_BEACONS, //table
                values, // column/value
                KEY_ID+" = ?", // selections
                new String[] { String.valueOf(beacon.getId()) }); //selection args
     
        // 4. close
        db.close();
     
        return i;
      }
 
    public void deleteBeacon(Beacon beacon) {
    
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_BEACONS, //table name
                KEY_ID+" = ?",  // selections
                new String[] { String.valueOf(beacon.getId()) }); //selections args
 
        db.close();
        Log.d("delete artwork", beacon.toString());
 
    }
}

