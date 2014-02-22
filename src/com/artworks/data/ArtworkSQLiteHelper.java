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

public class ArtworkSQLiteHelper extends SQLiteOpenHelper {
	 
    // Database Version
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "artworkste";   
   
    private static final String TABLE_ARTWORKS = "artworks";

    private static final String KEY_ID = "id";
    private static final String KEY_ARTIST_NAME = "artistname";
    private static final String KEY_ARTWORK_NAME = "artworkname";
    //private static final String KEY_DESCRIPTION = "description";
    //private static final String KEY_SOUND_URI = "sounduri";
    private static final String KEY_IMAGE_URI = "imageuri";
    //private static final String KEY_CREATION_DATE = "creationdate";
    
    
    private static final String[] COLUMNS = {
    	KEY_ID,
    	KEY_ARTIST_NAME,
    	KEY_ARTWORK_NAME,
    	//KEY_CREATION_DATE,
    	//KEY_DESCRIPTION,
    	//KEY_SOUND_URI,
    	KEY_IMAGE_URI
    	};
 
    public ArtworkSQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION); 
    }
 
    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL statement to create book table
        String CREATE_TABLE = "CREATE TABLE "+ TABLE_ARTWORKS +" ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_ARTIST_NAME+ " TEXT, "+
                KEY_ARTWORK_NAME+ " TEXT, "+
                //KEY_CREATION_DATE+ " DATETIME, "+
                //KEY_DESCRIPTION+ " TEXT, "+
                //KEY_SOUND_URI+ " TEXT, "+
                KEY_IMAGE_URI+ " TEXT ) ";
 
        
        // create books table
        db.execSQL(CREATE_TABLE);
    }
 
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older books table if existed
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_ARTWORKS);
 
        // create fresh books table
        this.onCreate(db);
    }
    
    public void addArtworks(Artwork artwork){
        //for logging
    	//Log.d("addBook", book.toString());

    	// 1. get reference to writable DB
    	SQLiteDatabase db = this.getWritableDatabase();

    	// 2. create ContentValues to add key "column"/value
    	ContentValues values = new ContentValues();
    	values.put(KEY_ARTIST_NAME, artwork.getmArtistName()); // get title
    	values.put(KEY_ARTWORK_NAME, artwork.getmArtworkName()); // get author
    	//values.put(KEY_CREATION_DATE, artwork.getmCreationDate()); // get author
    	/*if(artwork.getmDescription()!=null)
    		values.put(KEY_DESCRIPTION, artwork.getmDescription());
    	if(artwork.getmSoundUri()!=null)
    		values.put(KEY_SOUND_URI, artwork.getmSoundUri());*/
    	values.put(KEY_IMAGE_URI, String.valueOf(artwork.getImageUri()));
    	
    	// 3. insert
    	db.insert(TABLE_ARTWORKS, // table
        null, //nullColumnHack
        values); // key/value -> keys = column names/ values = column values

    	// 4. close
    	db.close();
    }
    
    public Artwork getArtwork(int id){
    	 
        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();
     
        // 2. build query
        Cursor cursor =
                db.query(TABLE_ARTWORKS, // a. table
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
     
        // 4. build book object
        Artwork artwork = new Artwork();
        artwork.setId(Integer.parseInt(cursor.getString(0)));
        artwork.setmArtistName(cursor.getString(1));
        artwork.setmArtworkName(cursor.getString(2));
        //artwork.setmDescription(cursor.getString(3));
        //artwork.setmSoundUri(cursor.getString(4));
        artwork.setmImageUri(Uri.parse(cursor.getString(3)));
     // artwork.setmCreationDate(Date.parse(cursor.getString(3)));
        
         
        
        //log
    Log.d("getArtwork("+id+")", artwork.toString());
     
        // 5. return book
        return artwork;
    }
    
    public List<Artwork> getAllArtworks() {
        List<Artwork> artworks = new LinkedList<Artwork>();
  
        // 1. build the query
        String query = "SELECT  * FROM " + TABLE_ARTWORKS;
  
        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
  
        // 3. go over each row, build book and add it to list
        Artwork artwork = null;
        if (cursor.moveToFirst()) {
            do {
            	artwork = new Artwork();
            	artwork.setId(Integer.parseInt(cursor.getString(0)));
            	artwork.setmArtistName(cursor.getString(1));
            	artwork.setmArtworkName(cursor.getString(2));
            	//artwork.setmDescription(cursor.getString(3));
                //artwork.setmSoundUri(cursor.getString(4));
                artwork.setmImageUri(Uri.parse(cursor.getString(3)));
                //TODO artwork.setmCreationDate(Date.parse(cursor.getString(3)));
                
                artworks.add(artwork);
            } while (cursor.moveToNext());
        }
  
        Log.d("getAllArtworkss()", artworks.toString());
        return artworks;
    }
    
    public int updateArtwork(Artwork artwork) {
    	 
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
     
        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_ARTIST_NAME, artwork.getmArtistName()); // get title
    	values.put(KEY_ARTWORK_NAME, artwork.getmArtworkName()); // get author
    	//values.put(KEY_DESCRIPTION, artwork.getmDescription());
    	//values.put(KEY_SOUND_URI, artwork.getmSoundUri());
    	values.put(KEY_IMAGE_URI, String.valueOf(artwork.getImageUri()));
    	//values.put(KEY_CREATION_DATE, artwork.getmCreationDate()); // get author
    	
    	
        // 3. updating row
        int i = db.update(TABLE_ARTWORKS, //table
                values, // column/value
                KEY_ID+" = ?", // selections
                new String[] { String.valueOf(artwork.getId()) }); //selection args
     
        // 4. close
        db.close();
     
        return i;
      }
 
    public void deleteArtwork(Artwork artwork) {
    	 
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
 
        // 2. delete
        db.delete(TABLE_ARTWORKS, //table name
                KEY_ID+" = ?",  // selections
                new String[] { String.valueOf(artwork.getId()) }); //selections args
 
        // 3. close
        db.close();
 
        //log
    Log.d("delete artwork", artwork.toString());
 
    }
}

