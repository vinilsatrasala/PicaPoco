package com.riktamtech.picapoco.beans;

import java.io.Serializable;

public class ReviewerImageDetailsBean implements Serializable{

	public String designPageImageLayerId = "", designPageImageLayerApiId = "";
	public int type = 0, zIndex = 0, width = 0, height = 0, positionTop = 0,
			positionLeft = 0;
	public float rotationAngle = 0, photoCenterX = 0, photoCenterY = 0,
			photoScalePercentX = 0, photoScalePercentY = 0,
			photoRotationAngle = 0;
}
