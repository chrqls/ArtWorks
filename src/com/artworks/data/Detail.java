package com.artworks.data;

public class Detail {

	int mId;
	String mContent;
	int mArtworkId;
	
	public Detail(){
		
	}

	public int getId() {
		return mId;
	}

	public void setId(int id) {
		this.mId = id;
	}

	public String getContent() {
		return mContent;
	}

	public void setContent(String mContent) {
		this.mContent = mContent;
	}

	public int getArtworkId() {
		return mArtworkId;
	}

	public void setArtworkId(int artworkId) {
		mArtworkId = artworkId;
	}

}
