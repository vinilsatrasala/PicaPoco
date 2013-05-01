package com.riktamtech.picapoco.customviews;

import java.util.ArrayList;

import android.R.integer;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

public class CustomImageView extends ImageView {
	private int top = 0, left = 0;

	public CustomImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public CustomImageView(Context context, int top, int left) {
		super(context);
		this.top = top;
		this.left = left;
	}

	public CustomImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void setMargins(int top,int left)
	{
		this.top =top;
		this.left = left;
	}
	
	public ArrayList<Integer> getMargins()
	{
   ArrayList<Integer> margin = new ArrayList<Integer>();
   margin.add(top);
   margin.add(left);
	return 	margin;
	}
	
	public CustomImageView(Context context) {
		super(context);
	}
	
	
}
