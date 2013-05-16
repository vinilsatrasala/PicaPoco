package com.riktamtech.picapoco.adapters;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.p2p.WifiP2pManager.ServiceResponseListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.riktamtech.picapoco.R;
import com.riktamtech.picapoco.application.MyApplication.ServiceStatusListener;
import com.riktamtech.picapoco.beans.CommentsBean;
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
		View row = convertView;
		int leftPageIndex, rightPageIndex;
		PageHolder holder = null;
		if (row == null) {

			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(R.layout.flip_item, parent, false);
			holder = new PageHolder();

			holder.leftPage = (FrameLayout) row.findViewById(R.id.leftPage);
			holder.rightPage = (FrameLayout) row.findViewById(R.id.rightPage);
			holder.leftpageImage = (ImageView) holder.leftPage
					.findViewById(R.id.leftPageImage);
			holder.RightPageImage = (ImageView) holder.rightPage
					.findViewById(R.id.rightPageImage);
			row.setTag(holder);
		} else {

			holder = (PageHolder) row.getTag();

		}

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

		if (leftPageIndex == -1) {
			holder.leftpageImage.setVisibility(View.INVISIBLE);
		} else {
			holder.leftpageImage.setVisibility(View.VISIBLE);
		}
		if (rightPageIndex == -1) {
			holder.RightPageImage.setVisibility(View.INVISIBLE);
		} else {
			holder.RightPageImage.setVisibility(View.VISIBLE);
		}
		if (leftPageIndex != -1) {
			ReviewerPageBeanDetails leftPageBean = DesignBean.reviewerPageBeanDetailsArrayList
					.get(leftPageIndex);

			ImageLoader.getInstance().displayImage(
					ServiceRequestHelper.picapocoReviewerPageBaseUrl
							+ leftPageBean.designPageApiId + "/preview.jpg",
					holder.leftpageImage);
		}
		if (rightPageIndex != -1) {
			ReviewerPageBeanDetails rightPageBean = DesignBean.reviewerPageBeanDetailsArrayList
					.get(rightPageIndex);

			ImageLoader.getInstance().displayImage(
					ServiceRequestHelper.picapocoReviewerPageBaseUrl
							+ rightPageBean.designPageApiId + "/preview.jpg",
					holder.RightPageImage);
		}

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

		FrameLayout leftPage, rightPage;
		ImageView leftpageImage, RightPageImage;

	}

	// Getting comments

}
