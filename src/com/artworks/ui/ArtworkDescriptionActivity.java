package com.artworks.ui;

import java.text.SimpleDateFormat;

import com.artworks.ui.R;
import com.artworks.data.Artwork;
import com.artworks.data.ArtworkSQLiteHelper;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class ArtworkDescriptionActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.artwork_description_activity);
		
		Intent intent = getIntent();
		int artworkId = intent.getIntExtra(ArtworksListActivity.EXTRA_ARTWORK_ID, 0);
		ArtworkSQLiteHelper db = new ArtworkSQLiteHelper(this);
		Artwork artwork = db.getArtwork(artworkId);
		
		TextView artistNameTextView = (TextView)findViewById(R.id.textview_artist_name);
		artistNameTextView.setText(artwork.getmArtistName());
		
		TextView artworkNameTextView = (TextView)findViewById(R.id.textview_artwork_name);
		artworkNameTextView.setText(artwork.getmArtworkName());
		
		TextView descriptionTextView = (TextView)findViewById(R.id.textview_description);
		descriptionTextView.setText(artwork.getmDescription());
		
		TextView creationDateTextView = (TextView)findViewById(R.id.textview_creationdate);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 	    	
		creationDateTextView.setText(dateFormat.format(artwork.getmCreationDate()));
	    
	    
		
		ImageView imageView = (ImageView)findViewById(R.id.imageView1);
		imageView.setImageBitmap(decodeSampledBitmap(String.valueOf(artwork.getImageUri()), 250, 250));
		
		
	}

	  
	  public static int calculateInSampleSize(
	            BitmapFactory.Options options, int reqWidth, int reqHeight) {
	    // Raw height and width of image
	    final int height = options.outHeight;
	    final int width = options.outWidth;
	    int inSampleSize = 1;

	    if (height > reqHeight || width > reqWidth) {

	        final int halfHeight = height / 2;
	        final int halfWidth = width / 2;

	        // Calculate the largest inSampleSize value that is a power of 2 and keeps both
	        // height and width larger than the requested height and width.
	        while ((halfHeight / inSampleSize) > reqHeight
	                && (halfWidth / inSampleSize) > reqWidth) {
	            inSampleSize *= 2;
	        }
	    }

	    return inSampleSize;
		}

		
		public static Bitmap decodeSampledBitmap(String path,
		        int reqWidth, int reqHeight) {

		    // First decode with inJustDecodeBounds=true to check dimensions
		    final BitmapFactory.Options options = new BitmapFactory.Options();
		    options.inJustDecodeBounds = true;
		    BitmapFactory.decodeFile(path, options);
		    //BitmapFactory.decodeResource(res, resId, options);

		    // Calculate inSampleSize
		    options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

		    // Decode bitmap with inSampleSize set
		    options.inJustDecodeBounds = false;
		    
		    return BitmapFactory.decodeFile(path, options);
		}


}
