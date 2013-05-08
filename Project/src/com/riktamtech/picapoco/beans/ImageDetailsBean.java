package com.riktamtech.picapoco.beans;

import java.io.Serializable;

public class ImageDetailsBean implements Serializable{

	public int IMAGE_ID, SPOOL_ID, IMAGE_QUANTITY;
	public long THUMBNAIL_ORIGIN_ID;
	public String IMAGE_NAME, IMAGE_PATH;
	public boolean THUMBNAILSELECTION = false;

}
