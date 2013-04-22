package com.riktamtech.picapoco.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

public class AlbumCatalogInfoAdapter extends PagerAdapter {

	private Context ctx;
	private ArrayList<Integer> images = new ArrayList<Integer>();

	public AlbumCatalogInfoAdapter(Context ctx, ArrayList<Integer> images) {
		this.ctx = ctx;
		this.images = images;
	}

	@Override
	public void destroyItem(View arg0, int arg1, Object arg2) {
		((ViewPager) arg0).removeView((View) arg2);
	}

	@Override
	public void finishUpdate(View arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return images.size();
	}

	@Override
	public Object instantiateItem(View arg0, int arg1) {
		ImageView imageView = new ImageView(ctx);
		imageView.setImageDrawable(ctx.getResources().getDrawable(
				images.get(arg1)));
		((ViewPager) arg0).addView(imageView);
		return imageView;
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == (View) arg1;
	}

	@Override
	public void restoreState(Parcelable arg0, ClassLoader arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public Parcelable saveState() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void startUpdate(View arg0) {
		// TODO Auto-generated method stub

	}

}