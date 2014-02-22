package com.artworks.data;

import java.util.Date;
import android.net.Uri;

public class Artwork {

	public enum ArtWorkKind{PEINTURE,STATUE}	

	private int mId;
	private String mArtistName;
	private String mArtworkName;
	private Date mCreationDate;
	private String mDescription;
	private Uri mSoundUri;
	private Uri mImageUri;
	
	public Artwork(String artworkName, 
			String artistName,
			Uri imageUri){
		this.mArtistName=artistName;
		this.mArtworkName=artworkName;
		//this.mCreationDate=creationDate;
		//this.mDescription=description;
		this.mImageUri=imageUri;
	}
	
	public Artwork(){}

	public String getmArtistName() {
		return mArtistName;
	}

	public void setmArtistName(String mArtistName) {
		this.mArtistName = mArtistName;
	}

	public String getmArtworkName() {
		return mArtworkName;
	}

	public void setmArtworkName(String mArtworkName) {
		this.mArtworkName = mArtworkName;
	}

	public Date getmCreationDate() {
		return mCreationDate;
	}

	public void setmCreationDate(Date mCreationDate) {
		this.mCreationDate = mCreationDate;
	}

	public String getmDescription() {
		return mDescription;
	}

	public void setmDescription(String mDescription) {
		this.mDescription = mDescription;
	}

	public String getmSoundUri() {
		return mSoundUri.toString();
	}

	public void setmSoundUri(String mSoundUri) {
		this.mSoundUri = Uri.parse(mSoundUri);
	}

	public Uri getImageUri() {
		return mImageUri;
	}

	public void setmImageUri(Uri imageUri) {
		this.mImageUri = imageUri;
	}
	
	public int getId() {
		return mId;
	}

	public void setId(int id) {
		this.mId = id;
	}
	

}
