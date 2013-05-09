package com.riktamtech.picapoco.albumsortutils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap.Config;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.disc.DiscCacheAware;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.riktamtech.picapoco.R;
import com.riktamtech.picapoco.application.MyApplication;
import com.riktamtech.picapoco.beans.ReviewerDesignBean;

/**
 * This class is used with a GridView object. It provides a set of ImageCell
 * objects that support dragging and dropping.
 * 
 */

public class ImageCellAdapter extends BaseAdapter {

	// Constants
	public static final int DEFAULT_NUM_IMAGES = 8;

	/**
 */
	// Variables
	public ViewGroup mParentView = null;
	private Context mContext;
	private ReviewerDesignBean designBean;
	private int pageCount;

	private ImageLoaderConfiguration config;

	public ImageCellAdapter(Context c, ReviewerDesignBean designBean) {
		mContext = c;
		this.designBean = designBean;
		pageCount = (designBean.reviewerPageBeanDetailsArrayList.size() + 2) / 2;

	

	}

	/**
 */
	// Methods

	/**
	 * getCount
	 */

	public int getCount() {

		return pageCount;
	}

	public Object getItem(int position) {
		return null;
	}

	public long getItemId(int position) {
		return position;
	}

	/**
	 * getView Return a view object for the grid.
	 * 
	 * @return ImageCell
	 */
	public View getView(int position, View convertView, ViewGroup parent) {
		mParentView = parent;
		View row = convertView;

		PageHolder holder = null;

		if (row == null) {
			// If it's not recycled, create a new ImageCell.
			LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
			row = inflater.inflate(R.layout.albumsort_griditem, parent, false);
			holder = new PageHolder();

			holder.imagecell = (ImageCell) row.findViewById(R.id.imageCell);
			holder.leftImage = (ImageView) row.findViewById(R.id.leftImage);
			holder.rightImage = (ImageView) row.findViewById(R.id.rightImage);
			row.setTag(holder);

		} else {
			holder = (PageHolder) row.getTag();
		}

		holder.imagecell.mCellNumber = position;
		holder.imagecell.mGrid = (GridView) mParentView;
		holder.imagecell.mEmpty = true;
		if (position == 1 || position == pageCount - 1) {
			holder.imagecell.mEmpty = false;
			if (position == 1)
				ImageLoader
						.getInstance()
						.displayImage(
								"http://ppinterface.staging.pictureplix.net/app_staging.php/designPage/read/"

										+ designBean.reviewerPageBeanDetailsArrayList.get(2 * position).designPageApiId
										+ "/" + "thumbnail.jpg",
								holder.rightImage);
			else
				ImageLoader
						.getInstance()
						.displayImage(
								"http://ppinterface.staging.pictureplix.net/app_staging.php/designPage/read/"

										+ designBean.reviewerPageBeanDetailsArrayList.get(2 * position - 1).designPageApiId
										+ "/" + "thumbnail.jpg", holder.leftImage);
		} else if (position == 0) {
			ImageLoader
					.getInstance()
					.displayImage(
							"http://ppinterface.staging.pictureplix.net/app_staging.php/designPage/read/"

									+ designBean.reviewerPageBeanDetailsArrayList.get(2 * position).designPageApiId
									+ "/" + "thumbnail.jpg", holder.leftImage);
			ImageLoader
					.getInstance()
					.displayImage(
							"http://ppinterface.staging.pictureplix.net/app_staging.php/designPage/read/"

									+ designBean.reviewerPageBeanDetailsArrayList.get(2 * position + 1).designPageApiId
									+ "/" + "thumbnail.jpg", holder.rightImage);
		} else {
			ImageLoader
					.getInstance()
					.displayImage(
							"http://ppinterface.staging.pictureplix.net/app_staging.php/designPage/read/"

									+ designBean.reviewerPageBeanDetailsArrayList.get(2 * position - 1).designPageApiId
									+ "/" + "thumbnail.jpg", holder.leftImage);

			ImageLoader
					.getInstance()
					.displayImage(
							"http://ppinterface.staging.pictureplix.net/app_staging.php/designPage/read/"

									+ designBean.reviewerPageBeanDetailsArrayList.get(2 * position).designPageApiId
									+ "/" + "thumbnail.jpg", holder.rightImage);
		}
		// v.setBackgroundResource (R.color.drop_target_enabled);

		// Set up to relay events to the activity.
		// The activity decides which events trigger drag operations.
		// Activities like the Android Launcher require a long click to get a
		// drag operation started.
		holder.imagecell
				.setOnLongClickListener((View.OnLongClickListener) mContext);
		holder.imagecell.setOnClickListener((View.OnClickListener) mContext);

		return row;
	}

	static class ViewHolder {
		ImageCell imagecell;
		ImageView leftImage, rightImage;
	}

	static class PageHolder {

		ImageCell imagecell;
		ImageView leftImage, rightImage;

	}
} // end class
