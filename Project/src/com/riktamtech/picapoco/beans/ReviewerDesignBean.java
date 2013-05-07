package com.riktamtech.picapoco.beans;

import java.util.ArrayList;

public class ReviewerDesignBean {

	public String externalId = "", designOwnerId = "", bookTitle = "",
			designResolution = "", designId = "";

	public ArrayList<ReviewerPageBeanDetails> reviewerPageBeanDetailsArrayList;

	public ReviewerDesignBean() {
		reviewerPageBeanDetailsArrayList = new ArrayList<ReviewerPageBeanDetails>();
	}

	/**
	 * Returns Page Width
	 * 
	 * @return
	 */
	public int getWidth() {

		return Integer.parseInt(designResolution.substring(0,
				designResolution.indexOf("x")).trim());
	}

	/**
	 * Returns Page Height
	 * 
	 * @return
	 */
	public int getHeight() {
		return Integer.parseInt(designResolution.substring(designResolution
				.indexOf("x") + 1));
	}

	public int getChangedDimension(int actualresolutionDimension,
			int actualDimension, int targerResolutionDimension, float scaling) {
		return (int) ((actualDimension * (scaling / 100) / actualresolutionDimension) * targerResolutionDimension);
	}
}
