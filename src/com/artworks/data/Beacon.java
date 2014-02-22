package com.artworks.data;

public class Beacon {

	int mId;
	String mUUID;
	int mMinor;
	int mMajor;
	int mArtworkId;
	

	public Beacon(){

	}
	
	public Beacon(String UUID, int major, int minor){
		this.mUUID = UUID;
		this.mMajor = major;
		this.mMinor = minor;
	}


	public int getArtworkId() {
		return mArtworkId;
	}


	public void setArtworkId(int mArtworkId) {
		this.mArtworkId = mArtworkId;
	}


	public int getId() {
		return mId;
	}


	public void setId(int mId) {
		this.mId = mId;
	}


	public String getUUID() {
		return mUUID;
	}


	public void setUUID(String mUUID) {
		this.mUUID = mUUID;
	}


	public int getMinor() {
		return mMinor;
	}


	public void setMinor(int mMinor) {
		this.mMinor = mMinor;
	}


	public int getMajor() {
		return mMajor;
	}


	public void setMajor(int mMajor) {
		this.mMajor = mMajor;
	}

}
