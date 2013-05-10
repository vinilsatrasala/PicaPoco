package com.riktamtech.picapoco.beans;

import java.io.Serializable;
import java.util.ArrayList;

public class ReviewerPageBeanDetails implements Serializable{
	public int designPageId = 0, position = 0;
	public String designPageApiId = "";
	public boolean redesign = false;
	public ArrayList<ReviewerDesignLayoutGroups> designLayoutGroupsArrayList;

	public ReviewerPageBeanDetails() {
		designLayoutGroupsArrayList = new ArrayList<ReviewerDesignLayoutGroups>();
	}
}