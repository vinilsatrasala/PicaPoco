package com.riktamtech.picapoco.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.riktamtech.picapoco.R;
import com.riktamtech.picapoco.beans.ReviewerDesignBean;
import com.riktamtech.picapoco.beans.ReviewerPageBeanDetails;
import com.riktamtech.picapoco.services.ServiceRequestHelper;
import com.riktamtech.picapoco.ui.ReviewerActivity;

public class ReviewerAlbumAdapter extends BaseAdapter {
	private Context context;
	private ReviewerDesignBean DesignBean;
	private ImageLoader imageLoader;
	private ImageLoaderConfiguration config;
	private Double heightScaleRatio, widthScaleRatio;

	public ReviewerAlbumAdapter(Context context, ReviewerDesignBean DesignBean,
			Double heightScaleRatio, Double widthScaleratio) {
		this.context = context;
		this.DesignBean = DesignBean;
		this.heightScaleRatio = heightScaleRatio;
		this.widthScaleRatio = widthScaleratio;
		config = new ImageLoaderConfiguration.Builder(context)
				.memoryCache(new UsingFreqLimitedMemoryCache(5000000))
				.defaultDisplayImageOptions(DisplayImageOptions.createSimple())
				.build();
		imageLoader = ImageLoader.getInstance();
		imageLoader.init(config);
	}

	@Override
	public int getCount() {
		return (int) Math.ceil(DesignBean.reviewerPageBeanDetailsArrayList
				.size() / 2);
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

		int leftPageIndex = position * 2;
		int rightPageIndex = (position * 2) + 1;
		LayoutInflater inflater = ((Activity) context).getLayoutInflater();
		View PageView = inflater.inflate(R.layout.pagelayout, null);
		ReviewerPageBeanDetails leftPageBean = DesignBean.reviewerPageBeanDetailsArrayList
				.get(leftPageIndex);
		ReviewerPageBeanDetails rightPageBean = DesignBean.reviewerPageBeanDetailsArrayList
				.get(rightPageIndex);
		FrameLayout leftPage = (FrameLayout) PageView
				.findViewById(R.id.leftPageFrame);
		FrameLayout rightPage = (FrameLayout) PageView
				.findViewById(R.id.rightPageFrame);

		leftPage.getMeasuredHeight();
		leftPage.getMeasuredWidth();
		leftPage.getWidth();
		leftPage.getHeight();
		for (int i = 0; i < leftPageBean.designLayoutGroupsArrayList.size(); i++) {

			for (int j = 0; j < leftPageBean.designLayoutGroupsArrayList.get(i).reviewerImageDetailsArrayList
					.size(); j++) {
				LayoutParams params = new FrameLayout.LayoutParams(
						FrameLayout.LayoutParams.WRAP_CONTENT,
						FrameLayout.LayoutParams.WRAP_CONTENT);
				params.setMargins(
						(int) (leftPageBean.designLayoutGroupsArrayList.get(i).reviewerImageDetailsArrayList
								.get(j).positionLeft * widthScaleRatio),
						(int) (leftPageBean.designLayoutGroupsArrayList.get(i).reviewerImageDetailsArrayList
								.get(j).positionTop * heightScaleRatio), 0, 0);
				ImageView imageView = new ImageView(context);
				imageView
						.setTag(leftPageBean.designLayoutGroupsArrayList.get(i).reviewerImageDetailsArrayList
								.get(j).designPageImageLayerApiId);
				leftPage.addView(imageView, params);
				imageLoader.displayImage(
						ServiceRequestHelper.picapocoReviewerImageBaseUrl
								+ leftPageBean.designLayoutGroupsArrayList
										.get(i).reviewerImageDetailsArrayList
										.get(j).designPageImageLayerApiId
								+ ".jpg", imageView);

			}
		}
		return PageView;
	}

	static class PageHolder {

		FrameLayout flipItem;

	}
}
