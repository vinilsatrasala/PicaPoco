package com.riktamtech.picapoco.adapters;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView.ScaleType;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.download.ImageDownloader;
import com.riktamtech.picapoco.R;
import com.riktamtech.picapoco.beans.ReviewerDesignBean;
import com.riktamtech.picapoco.beans.ReviewerPageBeanDetails;
import com.riktamtech.picapoco.services.ServiceRequestHelper;
import com.riktamtech.picapoco.ui.ReviewerActivity;

public class ReviewerAlbumAdapter extends BaseAdapter {
	private Context context;
	private ReviewerDesignBean DesignBean;

	private Double heightScaleRatio, widthScaleRatio;
	private String albumMode;

	public ReviewerAlbumAdapter(Context context, ReviewerDesignBean DesignBean,
			Double heightScaleRatio, Double widthScaleratio, String albumMode) {
		this.context = context;
		this.DesignBean = DesignBean;
		this.heightScaleRatio = heightScaleRatio;
		this.widthScaleRatio = widthScaleratio;
		this.albumMode = albumMode;

	}

	@Override
	public int getCount() {
		return (int) Math.ceil((DesignBean.reviewerPageBeanDetailsArrayList
				.size() + 4) / 2);
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View row = convertView;

		PageHolder holder = null;
		if (row == null) {

			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(R.layout.flip_item, parent, false);
			holder = new PageHolder();

			holder.flipItem = (FrameLayout) row.findViewById(R.id.flipItem);
			row.setTag(holder);
		} else {
			holder = (PageHolder) row.getTag();

		}
		holder.flipItem.removeAllViews();
		holder.flipItem.addView(PreparePage(position, parent));
		return row;
	}

	protected View PreparePage(int position, ViewGroup parent) {
		int leftPageIndex = 0, rightPageIndex = 0;

		if (position == 0) {
			leftPageIndex = -1;
			rightPageIndex = 1;
		} else if (position == getCount() - 1) {
			rightPageIndex = -1;
			leftPageIndex = 0;
		} else if (position == 1) {
			rightPageIndex = 2;
			leftPageIndex = -1;
		} else if (position == getCount() - 2) {
			rightPageIndex = -1;
			leftPageIndex = DesignBean.reviewerPageBeanDetailsArrayList.size() - 1;
		} else {
			leftPageIndex = 2 * position - 1;
			rightPageIndex = 2 * position;
		}

		LayoutInflater inflater = ((Activity) context).getLayoutInflater();
		View PageView = inflater.inflate(R.layout.pagelayout, null);

		FrameLayout leftPage = (FrameLayout) PageView
				.findViewById(R.id.leftPageFrame);
		FrameLayout rightPage = (FrameLayout) PageView
				.findViewById(R.id.rightPageFrame);
		if (leftPageIndex != -1) {
			ReviewerPageBeanDetails leftPageBean = DesignBean.reviewerPageBeanDetailsArrayList
					.get(leftPageIndex);
			
			for (int i = 0; i < leftPageBean.designLayoutGroupsArrayList.size(); i++) {

				for (int j = 0; j < leftPageBean.designLayoutGroupsArrayList
						.get(i).reviewerImageDetailsArrayList.size(); j++) {
					LayoutParams params;
					if (leftPageBean.designLayoutGroupsArrayList.get(i).reviewerImageDetailsArrayList
							.get(j).type == 2) {
						params = new FrameLayout.LayoutParams(
								FrameLayout.LayoutParams.MATCH_PARENT,
								FrameLayout.LayoutParams.WRAP_CONTENT);
					} else {
						params = new FrameLayout.LayoutParams(
								(int) (widthScaleRatio * leftPageBean.designLayoutGroupsArrayList
										.get(i).reviewerImageDetailsArrayList
										.get(j).width),
								(int) (heightScaleRatio * leftPageBean.designLayoutGroupsArrayList
										.get(i).reviewerImageDetailsArrayList
										.get(j).height));
					}

					params.setMargins(
							(int) (leftPageBean.designLayoutGroupsArrayList
									.get(i).reviewerImageDetailsArrayList
									.get(j).positionLeft * widthScaleRatio),
							(int) (leftPageBean.designLayoutGroupsArrayList
									.get(i).reviewerImageDetailsArrayList
									.get(j).positionTop * heightScaleRatio), 0,
							0);
					ImageView imageView = new ImageView(context);
					imageView.setScaleType(ScaleType.FIT_XY);
					imageView
							.setTag(leftPageBean.designLayoutGroupsArrayList
									.get(i).reviewerImageDetailsArrayList
									.get(j).designPageImageLayerApiId);
					imageView.setLayoutParams(params);
					imageView
							.setPivotX((float) (widthScaleRatio * leftPageBean.designLayoutGroupsArrayList
									.get(i).reviewerImageDetailsArrayList
									.get(j).photoCenterX));
					imageView
							.setPivotY((float) (heightScaleRatio * leftPageBean.designLayoutGroupsArrayList
									.get(i).reviewerImageDetailsArrayList
									.get(j).photoCenterY));
					imageView
							.setRotation(leftPageBean.designLayoutGroupsArrayList
									.get(i).reviewerImageDetailsArrayList
									.get(j).photoRotationAngle);
					leftPage.addView(imageView);

					ImageLoader
							.getInstance()
							.displayImage(
									ServiceRequestHelper.picapocoReviewerImageBaseUrl
											+ leftPageBean.designLayoutGroupsArrayList.get(i).reviewerImageDetailsArrayList.get(j).designPageImageLayerApiId
											+ ".jpg", imageView);

				}
			}
		}
		if (rightPageIndex != -1) {
			ReviewerPageBeanDetails rightPageBean = DesignBean.reviewerPageBeanDetailsArrayList
					.get(rightPageIndex);
			for (int i = 0; i < rightPageBean.designLayoutGroupsArrayList
					.size(); i++) {

				for (int j = 0; j < rightPageBean.designLayoutGroupsArrayList
						.get(i).reviewerImageDetailsArrayList.size(); j++) {
					LayoutParams params;
					if (rightPageBean.designLayoutGroupsArrayList.get(i).reviewerImageDetailsArrayList
							.get(j).type == 2) {
						params = new FrameLayout.LayoutParams(
								FrameLayout.LayoutParams.MATCH_PARENT,
								FrameLayout.LayoutParams.WRAP_CONTENT);
					} else {
						params = new FrameLayout.LayoutParams(
								(int) (widthScaleRatio * rightPageBean.designLayoutGroupsArrayList
										.get(i).reviewerImageDetailsArrayList
										.get(j).width),
								(int) (heightScaleRatio * rightPageBean.designLayoutGroupsArrayList
										.get(i).reviewerImageDetailsArrayList
										.get(j).height));
					}

					params.setMargins(
							(int) (rightPageBean.designLayoutGroupsArrayList
									.get(i).reviewerImageDetailsArrayList
									.get(j).positionLeft * widthScaleRatio),
							(int) (rightPageBean.designLayoutGroupsArrayList
									.get(i).reviewerImageDetailsArrayList
									.get(j).positionTop * heightScaleRatio), 0,
							0);
					ImageView imageView = new ImageView(context);
					imageView.setScaleType(ScaleType.FIT_XY);
					imageView
							.setTag(rightPageBean.designLayoutGroupsArrayList
									.get(i).reviewerImageDetailsArrayList
									.get(j).designPageImageLayerApiId);
					rightPage.addView(imageView, params);
					imageView
							.setPivotX((float) (widthScaleRatio * rightPageBean.designLayoutGroupsArrayList
									.get(i).reviewerImageDetailsArrayList
									.get(j).photoCenterX));
					imageView
							.setPivotY((float) (heightScaleRatio * rightPageBean.designLayoutGroupsArrayList
									.get(i).reviewerImageDetailsArrayList
									.get(j).photoCenterY));
					imageView
							.setRotation(rightPageBean.designLayoutGroupsArrayList
									.get(i).reviewerImageDetailsArrayList
									.get(j).photoRotationAngle);
					ImageLoader
							.getInstance()
							.displayImage(
									ServiceRequestHelper.picapocoReviewerImageBaseUrl
											+ rightPageBean.designLayoutGroupsArrayList.get(i).reviewerImageDetailsArrayList.get(j).designPageImageLayerApiId
											+ ".jpg", imageView);

				}
			}
		}
		return PageView;
	}

	static class PageHolder {

		FrameLayout flipItem;

	}
}
