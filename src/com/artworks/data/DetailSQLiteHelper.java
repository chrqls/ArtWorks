package com.artworks.data;

import java.util.LinkedList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DetailSQLiteHelper extends SQLiteOpenHelper {
	 
    // Database Version
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "artworkstest";   
    private static final String TABLE_DETAILS = "detail";

    private static final String KEY_ID = "id";
    private static final String KEY_CONTENT = "content";
    private static final String KEY_ARTWORK_ID = "artworkid";
    
    private static final String[] COLUMNS = {
    	KEY_ID,
    	KEY_CONTENT,
    	KEY_ARTWORK_ID};
 
    public DetailSQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION); 
    }
 
    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL statement to create book table
        String CREATE_TABLE = "CREATE TABLE "+ TABLE_DETAILS +" ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_CONTENT+ " TEXT, "+
                KEY_ARTWORK_ID+ " INTEGER )";
 
        
        // create books table
        db.execSQL(CREATE_TABLE);
    }
 
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older books table if existed
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_DETAILS);
 
        // create fresh books table
        this.onCreate(db);
    }
    
    public void addDetails(Detail detail){
        //for logging
    	//Log.d("addBook", book.toString());

    	// 1. get reference to writable DB
    	SQLiteDatabase db = this.getWritableDatabase();

    	// 2. create ContentValues to add key "column"/value
    	ContentValues values = new ContentValues();
    	values.put(KEY_CONTENT, detail.getContent()); // get title
    	values.put(KEY_ARTWORK_ID, detail.getArtworkId()); // get author
    	
    	
    	// 3. insert
    	db.insert(TABLE_DETAILS, // table
        null, //nullColumnHack
        values); // key/value -> keys = column names/ values = column values

    	// 4. close
    	db.close();
    }
    
    public Detail getArtwork(int id){
    	 
        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();
     
        // 2. build query
        Cursor cursor =
                db.query(TABLE_DETAILS, // a. table
                COLUMNS, // b. column names
                " id = ?", // c. selections
                new String[] { String.valueOf(id) }, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit

        if (cursor != null)
            cursor.moveToFirst();
     
        Detail detail = new Detail();
        detail.setId(Integer.parseInt(cursor.getString(0)));
        detail.setContent(cursor.getString(1));
        detail.setArtworkId(Integer.parseInt(cursor.getString(2)));
         
        return detail;
    }
    
    public List<Detail> getAllDetails() {
        List<Detail> details = new LinkedList<Detail>();
  
        // 1. build the query
        String query = "SELECT  * FROM " + TABLE_DETAILS;
  
        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
  
        // 3. go over each row, build book and add it to list
        Detail detail = null;
        if (cursor.moveToFirst()) {
            do {
            	detail = new Detail();
            	detail.setId(Integer.parseInt(cursor.getString(0)));
            	detail.setContent(cursor.getString(1));
            	detail.setArtworkId(Integer.parseInt(cursor.getString(2)));
                details.add(detail);
            } while (cursor.moveToNext());
        }
  
        Log.d("getAllDetails()", details.toString());
  
        // return books
        return details;
    }
    
    
    public List<Detail> getArtworkDetails(int artworkId) {
        List<Detail> details = new LinkedList<Detail>();
  
        String query = "SELECT  * FROM " + TABLE_DETAILS + 
        		" WHERE " + KEY_ARTWORK_ID + " = '"+artworkId+"'";
  
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
  
        // 3. go over each row, build book and add it to list
        Detail detail = null;
        if (cursor.moveToFirst()) {
            do {
            	detail = new Detail();
            	detail.setId(Integer.parseInt(cursor.getString(0)));
            	detail.setContent(cursor.getString(1));
            	detail.setArtworkId(Integer.parseInt(cursor.getString(2)));
                details.add(detail);
            } while (cursor.moveToNext());
        }
  
        Log.d("getArtworkDetails()", details.toString());
  
        // return books
        return details;
    }
    
    
    public int updateDetail(Detail detail) {
    	 
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
     
        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_CONTENT, detail.getContent()); // get title
    	values.put(KEY_ARTWORK_ID, detail.getArtworkId()); // get author
    	
        // 3. updating row
        int i = db.update(TABLE_DETAILS, //table
                values, // column/value
                KEY_ID+" = ?", // selections
                new String[] { String.valueOf(detail.getId()) }); //selection args
     
        // 4. close
        db.close();
     
        return i;
      }
 
    public void deleteDetail(Detail detail) {
    	 
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
 
        // 2. delete
        db.delete(TABLE_DETAILS, //table name
                KEY_ID+" = ?",  // selections
                new String[] { String.valueOf(detail.getId()) }); //selections args
 
        // 3. close
        db.close();
 
        //log
    Log.d("delete artwork", detail.toString());
 
    }
}

