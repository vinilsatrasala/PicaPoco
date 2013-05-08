package com.riktamtech.picapoco.beans;

import java.io.Serializable;
import java.util.ArrayList;

public class ReviewerDesignLayoutGroups implements Serializable {
	public int designPageLayerGroupId = 0, zIndex = 0;
	public ArrayList<ReviewerImageDetailsBean> reviewerImageDetailsArrayList;
	public ArrayList<ReviewerTextDetailsBean> reviewerTextDetailsArrayList;

}
