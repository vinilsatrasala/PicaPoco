package com.riktamtech.picapoco.beans;

import java.util.ArrayList;

public class AlbumDetailsBean {

	public int AlbumId;
	public int albumType = 0, albumFormat = 0, designerType = 0, numberOfPages;
	public String albumCategory;
	public ArrayList<String> selectedImagesPaths, selectedImageNames;

	public AlbumDetailsBean() {
		selectedImagesPaths = new ArrayList<String>();
		selectedImageNames = new ArrayList<String>();
	}
}
