package com.artworks.ui;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import com.artworks.ui.R;
import com.artworks.data.Artwork;
import com.artworks.data.ArtworkSQLiteHelper;
import com.artworks.data.Beacon;
import com.artworks.data.BeaconSQLiteHelper;
import com.radiusnetworks.ibeacon.IBeacon;
import com.radiusnetworks.ibeacon.IBeaconConsumer;
import com.radiusnetworks.ibeacon.IBeaconManager;
import com.radiusnetworks.ibeacon.MonitorNotifier;
import com.radiusnetworks.ibeacon.RangeNotifier;
import com.radiusnetworks.ibeacon.Region;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.RemoteException;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class ArtworksListActivity extends Activity implements IBeaconConsumer {

	static final String DEBUG_TAG = "ArtworksListActivity";
	static final String ACTION_SHOW_DETAILS="ACTION_SHOW_DETAILS";
	static final String EXTRA_ARTWORK_ID="EXTRA_ARTWORK_ID";
	//static final String RADBEACON_UUID="ADFBB825-4268-4269-BFA7-C6B392CDB02A";
	static final String RADBEACON_UUID="adfbb825-4268-4269-bfA7-c6b392cdb02a";
	static final String RADBEACON_UUID_TEST="adfbb82542684269bfa7c6b392cdb02a";
	static final String UNIQUE_ID="Museum";
	
	private IBeaconManager iBeaconManager;
	private List<Beacon> mAvailableBeacon;
	private List<Artwork> mAvailableArtworks;
	private Collection<IBeacon> mLastBeaconList;
	 ListView mListView;
	
	ArtworksListAdapter mAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.artwork_list_activity);
		
		/*iBeaconManager = IBeaconManager.getInstanceForApplication(this);
        verifyBluetooth();
        iBeaconManager.bind(this);	*/
	
        
        
        //SimulatedScanData data = new SimulatedScanData();
		
		
		mListView = (ListView) findViewById(R.id.artworks_listview);
		mAvailableArtworks = new ArrayList<Artwork>();
		mAvailableBeacon = new ArrayList<Beacon>();
		File pictureFileDir = getPictureDirectory();
		String pictureFileName = "IMG_20140221_151143.jpg";
		String picturePath = pictureFileDir.getPath()+File.separator+"DCIM"+File.separator+"Camera"+File.separator+pictureFileName;
		
		final ArtworkSQLiteHelper db = new ArtworkSQLiteHelper(this);

		if(db.getSize()==0){
			Artwork artwork1 = new Artwork(
					"Male-Female (1942)",
					"Jackson Pollock",
					"Male - Female is a 1942 oil on canvas painting by Edward Hopper " +
					"that portrays people sitting in a downtown diner late at night. " + 
					"It is Hopper's most famous work and is one of the most recognizable paintings in American art. " +
					"Within months of its completion, it was sold to the Art Institute of Chicago for $3,000 and has remained there ever since.",
					Uri.parse(picturePath));
			
			Artwork artwork2 = new Artwork(
					"Mao (1973)",
					"Andy Warhol",
					"The influential Pop artist Andy Warhol cast a cool, ironic light on the pervasiveness of commercial " +
					"culture and contemporary celebrity worship. " +
					"\n\nEarly in his career, he began to utilize the silkscreen process to transfer photographic images to canvas: " +
					"images of mass-produced consumer products and Hollywood film stars are among his most recognizable subjects. " +
					"In this example from his Mao series, Warhol melded his signature style with the scale of totalitarian propaganda " +
					"to address the cult of personality surrounding Chinese ruler Mao Zedong (1893–1976). " +
					"Nearly 15 feet tall, this towering work mimics the representations of the political figure that were ubiquitous " +
					"throughout China. In contrast to the photographic nature of the image, garish colors were applied like makeup to " +
					"Mao’s face. Ultimately, the portrait shows Warhol at his most painterly, rendering Mao, an enemy of individualism, " +
					"in a brazenly personal style.",
					Uri.parse(picturePath));
			//Artwork artwork2 = new Artwork("Joconde","DaVinci",Uri.parse(picturePath));
			db.addArtworks(artwork1);
			db.addArtworks(artwork2);
		}
			
	    List<Artwork> list = db.getAllArtworks();
	    BeaconSQLiteHelper beaconDB = new BeaconSQLiteHelper(ArtworksListActivity.this);
	    
	    
	    if(list.size()>0){
			Beacon beacon = new Beacon(RADBEACON_UUID_TEST,1,1);
			beacon.setArtworkId(list.get(0).getId());
			beaconDB.addBeacon(beacon);
	    }
	    
	    
	    
	    mAdapter= new ArtworksListAdapter(this, list); 
	    mListView.setAdapter(mAdapter);
	              
	    mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

	             @Override
	             public void onItemClick(AdapterView<?> parent, final View view,
	                 int position, long id) {
	               final Artwork item = (Artwork) parent.getItemAtPosition(position);
	               
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
	
	 @Override 
	 protected void onDestroy() {   
		 super.onDestroy();
	        //iBeaconManager.unBind(this);
	 }
	 
	 @Override 
	 protected void onPause() {
	    super.onPause();
	    //if (iBeaconManager.isBound(this)) iBeaconManager.setBackgroundMode(this, true);    		
	 }
	    
	 @Override 
	 protected void onResume() {
	    super.onResume();
	    //if (iBeaconManager.isBound(this)) iBeaconManager.setBackgroundMode(this, false);    		
	 }    
	    
	
	private File getPictureDirectory() {
          File sdDir = Environment.getExternalStorageDirectory();
          return sdDir;
	}

	public interface NewArtworksListener {
	    void OnNewArtworks(Artwork artwork);
	}
	
	public void updateUi() {
		
		runOnUiThread(new Runnable() {
    	    public void run() {
    	    	mAdapter.notifyDataSetChanged();
    			mListView.refreshDrawableState();
    	    }
    	});
		
	}

	private void verifyBluetooth() {

		try {
			if (!IBeaconManager.getInstanceForApplication(this).checkAvailability()) {
				final AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setTitle("Bluetooth not enabled");			
				builder.setMessage("Please enable bluetooth in settings and restart this application.");
				builder.setPositiveButton(android.R.string.ok, null);
				builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
					@Override
					public void onDismiss(DialogInterface dialog) {
						finish();
			            System.exit(0);					
					}					
				});
				builder.show();
			}			
		}
		catch (RuntimeException e) {
			final AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Bluetooth LE not available");			
			builder.setMessage("Sorry, this device does not support Bluetooth LE.");
			builder.setPositiveButton(android.R.string.ok, null);
			builder.setOnDismissListener(new DialogInterface.OnDismissListener() {

				@Override
				public void onDismiss(DialogInterface dialog) {
					finish();
		            System.exit(0);					
				}
				
			});
			builder.show();
			
		}
		
	}

	@Override
	public void onIBeaconServiceConnect() {
		 
		final BeaconSQLiteHelper beaconDb = new BeaconSQLiteHelper(ArtworksListActivity.this);
		final ArtworkSQLiteHelper artworkDb = new ArtworkSQLiteHelper(ArtworksListActivity.this);
		
		iBeaconManager.setMonitorNotifier(new MonitorNotifier() {
		       
			 	@Override
		        public void didEnterRegion(Region region) {
			 	  //ToastToDisplay("Enter UUID : "+region.getProximityUuid());
		          String uuid = region.getProximityUuid().replace("-", "");
		          
		          Beacon b = beaconDb.getBeacon(uuid);
		          if(b!=null){
						mAvailableArtworks.add(artworkDb.getArtwork(b.getArtworkId()));
						updateUi();
				  }
			 	}

		        @Override
		        public void didExitRegion(Region region) {
		        	 //ToastToDisplay("Exit UUID : "+region.getProximityUuid());
		        	try {
						iBeaconManager.stopMonitoringBeaconsInRegion(region);
					} catch (RemoteException e) {
						e.printStackTrace();
					}
		        	Beacon beacon = beaconDb.getBeacon(region.getProximityUuid().replace("-", ""));
		        	mAvailableArtworks.remove(beacon.getArtworkId()-1);
		        	//mLastBeaconList.remove(beacon);
		        	updateUi();
						
		        }

		        @Override
		        public void didDetermineStateForRegion(int state, Region region) {
		        	/*String s = null;
		        	switch (state) {
					case 0:
						s="OUTSIDE";
						break;
					case 1:
						s="INSIDE";
						break;

					default:
						break;
					}
		        	
		        	
		        	ToastToDisplay(s +" "+region.getProximityUuid());*/
    
		        }


		        });
		 
		 iBeaconManager.setRangeNotifier(new RangeNotifier() {
			
			@Override
			public void didRangeBeaconsInRegion(Collection<IBeacon> beacons, Region region) {
				
				String message = "New Region -  Beacon NB :"+ beacons.size();
				Log.e(DEBUG_TAG, message);
				
				if(mLastBeaconList!=null){

					Collection<IBeacon> c = CollectionUtils.disjunction(beacons, mLastBeaconList);
					for (IBeacon iBeacon : c) {
						
						if(beacons.contains(iBeacon)){ // It's a new beacon
							try {
								iBeaconManager.startMonitoringBeaconsInRegion(new Region(iBeacon.getProximityUuid(), iBeacon.getProximityUuid(), iBeacon.getMajor(), iBeacon.getMinor()));
							} catch (RemoteException e) {
								e.printStackTrace();
							}
						}else{
							beacons.add(iBeacon);
						}
					}
				}
					
				
				mLastBeaconList = beacons;	
				Log.i(DEBUG_TAG, "NB : "+mLastBeaconList.size());
				
				
				/*if(!mLastBeaconList.containsAll(beacons)){
					Log.i(DEBUG_TAG, "old list don't contain all");
				}*/
				
				
			
				
				
				/*for (IBeacon iBeacon : beacons) {
					
					
				
					ToastToDisplay("Beacon - uuid : "+ iBeacon.getProximityUuid()+
			        		  " proximity : "+iBeacon.getProximity()+
			        		  " Major : "+iBeacon.getMajor()+
			        		  " Minor : "+iBeacon.getMinor()+
			        		  " State : "+iBeacon.getAccuracy()+
			        		  " RSSI : "+ iBeacon.getRssi()+
			        		  " Tx Power : "+ iBeacon.getTxPower());
					
					
					
				}*/
				
				
				
				
				
				
				
			}
			

			

			
		});

		        try {
		            //iBeaconManager.startMonitoringBeaconsInRegion(new Region("1012", RADBEACON_UUID, 1, 1));
		            iBeaconManager.startRangingBeaconsInRegion(new Region("1012",null, null, null));
		        } catch (RemoteException e) { 
		        	Log.e(DEBUG_TAG,e.getMessage());
		        }
	}
	
	private void ToastToDisplay(final String line) {
    	runOnUiThread(new Runnable() {
    	    public void run() {
    	    	Toast.makeText(ArtworksListActivity.this,line, Toast.LENGTH_SHORT).show();
            	Log.i(DEBUG_TAG, line);
    	    }
    	});
    }
    
	
}
