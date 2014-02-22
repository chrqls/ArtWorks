package com.artworks.ui;

import java.io.File;
import java.util.List;

import com.artworks.R;
import com.artworks.data.Artwork;
import com.artworks.data.ArtworkSQLiteHelper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ArtworksListActivity extends Activity {

	static final String DEBUG_TAG = "ArtworksListActivity";
	static final String ACTION_SHOW_DETAILS="ACTION_SHOW_DETAILS";
	static final String EXTRA_ARTWORK_ID="EXTRA_ARTWORK_ID";
	
	ArtworksListAdapter mAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.artwork_list_activity);
		
		final ListView listView = (ListView) findViewById(R.id.artworks_listview);
		
		 File pictureFileDir = getPictureDirectory();
		 String pictureFileName = "IMG_20140221_151143.jpg";
		 String picturePath = pictureFileDir.getPath()+File.separator+"DCIM"+File.separator+"Camera"+File.separator+pictureFileName;

		Artwork artwork1 = new Artwork("art 3", "me",Uri.parse(picturePath));
		Artwork artwork2 = new Artwork("Joconde","DaVinci",Uri.parse(picturePath));
		
		
		final ArtworkSQLiteHelper db = new ArtworkSQLiteHelper(this);

		
			//db.addArtworks(artwork1);
			//db.addArtworks(artwork2);
		
	        List<Artwork> list = db.getAllArtworks();

	        mAdapter= new ArtworksListAdapter(this, list); 
	        listView.setAdapter(mAdapter);
	         
	         
	        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

	             @Override
	             public void onItemClick(AdapterView<?> parent, final View view,
	                 int position, long id) {
	               final Artwork item = (Artwork) parent.getItemAtPosition(position);
	               
	               //Toast.makeText(getApplicationContext(), item.getmArtworkName() + " selected", Toast.LENGTH_SHORT).show();
	               Log.i(DEBUG_TAG, "item clicked ");
	               
	               Intent intent = new Intent(ArtworksListActivity.this,ArtworkDescriptionActivity.class);
	               intent.setAction(ACTION_SHOW_DETAILS);
	               intent.putExtra(EXTRA_ARTWORK_ID, item.getId());
	               startActivity(intent);
	               
	             }
	             
	           });
	        
	      
	         
	}
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		return super.onCreateOptionsMenu(menu);
	}
	
	private File getPictureDirectory() {
          File sdDir = Environment.getExternalStorageDirectory();
          return sdDir;
	}

	public interface NewArtworksListener {
	    void OnNewArtworks(Artwork artwork);
	}
	
	public void updateUi() {
		mAdapter.notifyDataSetChanged();
	}
	
	/*public static AlertDialog getTextDialog(Context ctx,
	        final NewArtworksListener listener) {
	   
		
		View view = LayoutInflater.from(ctx).inflate(R.layout.dialog_add_goals, null);
	    
	    final TextView nameTextView = (TextView) view.findViewById(R.id.goal_name);
	    final TextView amountToReachTextView = (TextView) view.findViewById(R.id.goal_amount_to_reach);
	    
	    AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
	    builder.setView(view);
	    
	    //
	    builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

	        @Override
	        public void onClick(DialogInterface dialog, int which) {
	            Goal goal = new Goal();
	            String t = nameTextView.getText().toString();
	            goal.setName(t);
	        	goal.setAmountToReach(Float.valueOf(amountToReachTextView.getText().toString()));
	        	listener.OnNewGoals(goal);
	        }
	    });
	    builder.setNegativeButton(android.R.string.cancel, null);
	    return builder.create();
	}*/
	
	
}
