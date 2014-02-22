package com.artworks.ui;

import java.util.List;

import com.artworks.R;
import com.artworks.data.Artwork;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class ArtworksListAdapter extends ArrayAdapter<Artwork> {
		  private final Activity mContext;
		  private final List<Artwork> mArtworks;

		  static class ViewHolder {
			public ImageView image;
		    public TextView artistName;	    
		    public TextView artworkName;
		    public TextView creationDate;
		  }

		  public ArtworksListAdapter(Activity context, List<Artwork> artworks) {
		    super(context, R.layout.artwork_list_row_layout, artworks);
		    this.mContext = context;
		    this.mArtworks = artworks;
		  }

		  @Override
		  public View getView(int position, View convertView, ViewGroup parent) {
		    View rowView = convertView;
		    if (rowView == null) {
		      LayoutInflater inflater = mContext.getLayoutInflater();
		      rowView = inflater.inflate(R.layout.artwork_list_row_layout, null);
		      ViewHolder viewHolder = new ViewHolder();
		      viewHolder.image = (ImageView) rowView.findViewById(R.id.imageView1);
		      viewHolder.artistName = (TextView) rowView.findViewById(R.id.textview_artist_name);
		      viewHolder.artworkName = (TextView) rowView.findViewById(R.id.textview_creationdate);
		      viewHolder.creationDate = (TextView) rowView.findViewById(R.id.textview_artwork_name);
		      
		      rowView.setTag(viewHolder);
		    }

		    Artwork artwork = mArtworks.get(position);
		    ViewHolder holder = (ViewHolder) rowView.getTag();
		    String s = artwork.getmArtistName();
		    holder.artistName.setText(s);
		   
		    s = String.valueOf(artwork.getmArtworkName());
		    holder.artworkName.setText(s);
		   
		    s = String.valueOf(artwork.getmCreationDate());
		    holder.creationDate.setText(s);
		   
		    holder.image.setImageBitmap(decodeSampledBitmap(String.valueOf(artwork.getImageUri()), 100, 100));
		    

		    return rowView;
		  
		}

		//mImageView.setImageBitmap(
		  //  decodeSampledBitmapFromResource(getResources(), R.id.myimage, 100, 100)); 
		  
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