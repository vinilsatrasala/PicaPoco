package com.riktamtech.picapoco.ui;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aphidmobile.flip.FlipViewController;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.riktamtech.picapoco.R;
import com.riktamtech.picapoco.adapters.CommentsAdapter;
import com.riktamtech.picapoco.adapters.ReviewerAlbumAdapter;
import com.riktamtech.picapoco.application.MyApplication.ServiceStatusListener;
import com.riktamtech.picapoco.beans.ImageBean;
import com.riktamtech.picapoco.beans.ReviewerDesignBean;
import com.riktamtech.picapoco.services.ServiceRequestHelper;
import com.riktamtech.picapoco.ui.utils.parseReviewerDesignXml;

public class ReviewerActivity extends Activity implements OnClickListener,
		OnTouchListener, OnLongClickListener {

	private ImageView homeSaveButton;
	private ImageView aboutButton;
	private ImageView printButton;
	private ImageView shareButton;
	private ImageView commentsButton;
	private FrameLayout reviewerModeFrame;
	private RelativeLayout viewModeLayout;
	private RelativeLayout editModeLayout;
	private RelativeLayout editImageModeLayout;
	private RelativeLayout editTextModeLayout;
	private ImageView editTitleButton;
	private ImageView sortButton;
	private ImageView designerButton;
	private ImageView addImageButton;
	private ImageView addTextButton;
	private ImageView designerImageEditButton;
	private ImageView deleteImageButton;
	private ImageView deleteTextButton;
	private ImageView editTextButton;
	private ImageView designerTextEditButton;
	private ListView leftListView;
	private ListView rightListView;
	private Dialog commentsDialog;
	private ImageView commentLeftTop;
	private ImageView commentRightTop;
	protected FlipViewController flipView;

	ArrayList<ArrayList<ImageBean>> lPageBeans = new ArrayList<ArrayList<ImageBean>>();
	private int _xDelta;
	private int _yDelta;
	private ImageLoaderConfiguration config;
	private ImageLoader imageLoader;
	protected View page;
	protected ArrayList<View> views = new ArrayList<View>();
	protected PageHolder holder;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		setContentView(R.layout.activity_view_album);

		// TODO remove login by changing actual required one
		new ServiceRequestHelper().reviewerLogin("PO20130507BA14A0D27D",
				reviewerLoginListener);

		for (int i = 0; i < 4; i++) {
			ArrayList<ImageBean> imagebeans = new ArrayList<ImageBean>();
			for (int j = 0; j < 2; j++) {
				ImageBean imageBean = new ImageBean();
				if (j == 0) {
					imageBean.leftMargin = 50;
					imageBean.topMargin = 30;
				} else {
					imageBean.leftMargin = 150;
					imageBean.topMargin = 100;
				}
				imagebeans.add(imageBean);

			}
			lPageBeans.add(imagebeans);
		}

		homeSaveButton = (ImageView) findViewById(R.id.HomeSaveButton);
		aboutButton = (ImageView) findViewById(R.id.AboutButton);
		printButton = (ImageView) findViewById(R.id.PrintButton);
		shareButton = (ImageView) findViewById(R.id.ShareButton);
		commentsButton = (ImageView) findViewById(R.id.CommentsButton);
		commentLeftTop = (ImageView) findViewById(R.id.chatLeftButton);
		commentRightTop = (ImageView) findViewById(R.id.chatRightButton);
		commentLeftTop.setOnClickListener(this);
		commentRightTop.setOnClickListener(this);
		reviewerModeFrame = (FrameLayout) findViewById(R.id.reviwerModeFrame);
		viewModeLayout = (RelativeLayout) reviewerModeFrame
				.findViewById(R.id.viewMode);
		editTitleButton = (ImageView) viewModeLayout
				.findViewById(R.id.EditTitleButton);
		sortButton = (ImageView) viewModeLayout
				.findViewById(R.id.SortAlbumButton);
		editModeLayout = (RelativeLayout) reviewerModeFrame
				.findViewById(R.id.editMode);
		designerButton = (ImageView) editModeLayout
				.findViewById(R.id.DesignerButton);
		addImageButton = (ImageView) editModeLayout
				.findViewById(R.id.addImageButton);
		addTextButton = (ImageView) editModeLayout
				.findViewById(R.id.addTextButton);
		editImageModeLayout = (RelativeLayout) reviewerModeFrame
				.findViewById(R.id.editImageMode);
		designerImageEditButton = (ImageView) editImageModeLayout
				.findViewById(R.id.DesignerImageButton);
		deleteImageButton = (ImageView) editImageModeLayout
				.findViewById(R.id.deleteImageButton);
		editTextModeLayout = (RelativeLayout) reviewerModeFrame
				.findViewById(R.id.editTextMode);
		deleteTextButton = (ImageView) editTextModeLayout
				.findViewById(R.id.deleteTextButton);
		editTextButton = (ImageView) editTextModeLayout
				.findViewById(R.id.editTextButton);
		designerTextEditButton = (ImageView) editTextModeLayout
				.findViewById(R.id.DesignerTextButton);
		config = new ImageLoaderConfiguration.Builder(ReviewerActivity.this)
				.memoryCache(new UsingFreqLimitedMemoryCache(5000000))
				.defaultDisplayImageOptions(DisplayImageOptions.createSimple())
				.build();
		imageLoader = ImageLoader.getInstance();
		imageLoader.init(config);

		flipView = (FlipViewController) findViewById(R.id.flipView);

		flipView.setOverFlipEnabled(false);
		// flipView.setAdapter(new BaseAdapter() {
		// private ViewGroup parent;
		//
		// @Override
		// public int getCount() {
		// return 4;
		// }
		//
		// @Override
		// public Object getItem(int position) {
		// return position;
		// }
		//
		// @Override
		// public long getItemId(int position) {
		// return position;
		// }
		//
		// @Override
		// public View getView(int position, View convertView, ViewGroup parent)
		// {
		//
		// View row = convertView;
		// final Context context = parent.getContext();
		// PageHolder holder = null;
		// if (row == null) {
		//
		// LayoutInflater inflater = ((Activity) context)
		// .getLayoutInflater();
		// row = inflater.inflate(R.layout.pagelayout, parent, false);
		// holder = new PageHolder();
		// holder.leftPage = (FrameLayout) row
		// .findViewById(R.id.leftPageFrame);
		// holder.rightPage = (FrameLayout) row
		// .findViewById(R.id.rightPageFrame);
		// holder.liv1 = (ImageView) row.findViewById(R.id.leftIv1);
		// holder.liv2 = (ImageView) row.findViewById(R.id.leftIv2);
		// holder.liv3 = (ImageView) row.findViewById(R.id.leftIv3);
		// holder.liv4 = (ImageView) row.findViewById(R.id.leftIv4);
		// holder.liv5 = (ImageView) row.findViewById(R.id.leftIv5);
		// holder.liv6 = (ImageView) row.findViewById(R.id.leftIv6);
		// holder.liv7 = (ImageView) row.findViewById(R.id.leftIv7);
		// holder.liv8 = (ImageView) row.findViewById(R.id.leftIv8);
		// holder.leftPage
		// .setOnLongClickListener(ReviewerActivity.this);
		// holder.rightPage
		// .setOnLongClickListener(ReviewerActivity.this);
		// row.setTag(holder);
		// } else {
		// holder = (PageHolder) row.getTag();
		//
		// }
		// for (int i = 0; i < lPageBeans.get(position).size(); i++) {
		// LayoutParams params = new FrameLayout.LayoutParams(
		// FrameLayout.LayoutParams.WRAP_CONTENT,
		// FrameLayout.LayoutParams.WRAP_CONTENT);
		// switch (i) {
		//
		// case 0:
		// holder.liv1.setVisibility(View.VISIBLE);
		// holder.liv1.setTag(holder.liv1.getId(), position);
		// holder.liv1.setTag(holder.liv1.getParent().hashCode(),
		// i);
		// holder.liv1.setTag(position + "" + i);
		//
		// params.setMargins(
		// lPageBeans.get(position).get(i).leftMargin,
		// lPageBeans.get(position).get(i).topMargin, 0, 0);
		// holder.leftPage.removeView(holder.liv1);
		// holder.leftPage.addView(holder.liv1, params);
		// imageLoader
		// .displayImage(
		// "http://alicia.mobile9.com/download/thumb/453/120/ironman3_JRzCNY2O.jpg",
		// holder.liv1);
		// break;
		// case 1:
		// holder.liv2.setVisibility(View.VISIBLE);
		// holder.liv2.setTag(holder.liv2.getId(), position);
		// holder.liv2.setTag(holder.liv2.getParent().hashCode(),
		// i);
		//
		// params.setMargins(
		// lPageBeans.get(position).get(i).leftMargin,
		// lPageBeans.get(position).get(i).topMargin, 0, 0);
		// holder.leftPage.removeView(holder.liv2);
		//
		// holder.leftPage.addView(holder.liv2, params);
		// imageLoader
		// .displayImage(
		// "http://wallpaper.pickywallpapers.com/samsung-reality/thumbnail/iron-man-standing.jpg",
		// holder.liv2);
		// break;
		// default:
		// break;
		// }
		//
		// }
		//
		// return row;
		// }
		// });

		homeSaveButton.setOnClickListener(this);
		aboutButton.setOnClickListener(this);
		printButton.setOnClickListener(this);
		shareButton.setOnClickListener(this);
		commentsButton.setOnClickListener(this);
		editTitleButton.setOnClickListener(this);
		editTextButton.setOnClickListener(this);
		designerButton.setOnClickListener(this);
		designerImageEditButton.setOnClickListener(this);
		designerTextEditButton.setOnClickListener(this);
		deleteImageButton.setOnClickListener(this);
		deleteTextButton.setOnClickListener(this);
		addImageButton.setOnClickListener(this);
		addTextButton.setOnClickListener(this);
		sortButton.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.EditTitleButton:
			startActivity(new Intent(ReviewerActivity.this,
					EditAlbumTitle.class));
			break;

		case R.id.ShareButton:
			startActivity(new Intent(ReviewerActivity.this, ShareActivity.class));
			break;

		case R.id.HomeSaveButton:
			startActivity(new Intent(ReviewerActivity.this, StartActivity.class)
					.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
			break;

		case R.id.CommentsButton:
			showCommentsDialog();

			break;

		case R.id.PrintButton:
			startActivity(new Intent(ReviewerActivity.this,
					PrintScreenOneActivity.class));
			break;

		case R.id.AboutButton:

			startActivity(new Intent(ReviewerActivity.this,
					InfoPriceActivity.class));
			break;

		case R.id.SortAlbumButton:
			startActivity(new Intent(ReviewerActivity.this,
					ActivityAlbumSort.class));
			break;

		case R.id.DesignerButton:

			break;

		case R.id.DesignerImageButton:

			break;

		case R.id.DesignerTextButton:

			break;

		case R.id.addImageButton:

			break;

		case R.id.addTextButton:

			break;

		case R.id.deleteImageButton:

			break;

		case R.id.deleteTextButton:

			break;

		case R.id.editTextButton:

			break;
		case R.id.chatLeftButton:
			showCommentsDialog();

			break;
		case R.id.chatRightButton:
			showCommentsDialog();

			break;

		default:
			break;
		}
	}

	private void showCommentsDialog() {
		commentsDialog = new Dialog(ReviewerActivity.this,
				android.R.style.Theme_Black_NoTitleBar_Fullscreen);
		commentsDialog.setContentView(R.layout.activity_reviewer_comments);
		((ImageView) commentsDialog.findViewById(R.id.chatLeftButton))
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						commentsDialog.dismiss();
					}
				});
		((ImageView) commentsDialog.findViewById(R.id.chatRightButton))
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						commentsDialog.dismiss();
					}
				});
		leftListView = (ListView) commentsDialog
				.findViewById(R.id.commentsLeftListView);
		rightListView = (ListView) commentsDialog
				.findViewById(R.id.commentsRightListView);
		leftListView.setAdapter(new CommentsAdapter(ReviewerActivity.this,
				R.layout.comment_listitem));
		rightListView.setAdapter(new CommentsAdapter(ReviewerActivity.this,
				R.layout.comment_listitem));
		commentsDialog.show();
		((TextView) commentsDialog.findViewById(R.id.headerTextView))
				.setTextColor(Color.BLACK);
		commentsDialog.getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
	}

	static class PageHolder {

		FrameLayout leftPage, rightPage;
		ImageView liv1, liv2, liv3, liv4, liv5, liv6, liv7, liv8, riv1, riv2,
				riv3, riv4, riv5, riv6, riv7, riv8;
	}

	@Override
	public boolean onTouch(View view, MotionEvent event) {
		if (view instanceof ImageView) {

			final int X = (int) event.getRawX();
			final int Y = (int) event.getRawY();
			switch (event.getAction() & MotionEvent.ACTION_MASK) {
			case MotionEvent.ACTION_DOWN:
				FrameLayout.LayoutParams lParams = (FrameLayout.LayoutParams) view
						.getLayoutParams();
				_xDelta = X - lParams.leftMargin;
				_yDelta = Y - lParams.topMargin;
				break;
			case MotionEvent.ACTION_UP:
				break;
			case MotionEvent.ACTION_POINTER_DOWN:
				break;
			case MotionEvent.ACTION_POINTER_UP:
				break;
			case MotionEvent.ACTION_MOVE:
				FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) view
						.getLayoutParams();
				layoutParams.leftMargin = X - _xDelta;
				layoutParams.topMargin = Y - _yDelta;
				layoutParams.rightMargin = 0;
				layoutParams.bottomMargin = 0;

				view.setLayoutParams(layoutParams);
				lPageBeans.get((Integer) view.getTag(view.getId())).get(
						(Integer) view.getTag(view.getParent().hashCode())).leftMargin = X
						- _xDelta;
				lPageBeans.get((Integer) view.getTag(view.getId())).get(
						(Integer) view.getTag(view.getParent().hashCode())).topMargin = Y
						- _yDelta;

				break;
			}
			((FrameLayout) view.getParent()).invalidate();
			((LinearLayout) view.getParent().getParent()).invalidate();

		}
		return true;

	}

	@Override
	public boolean onLongClick(View v) {
		if (v instanceof FrameLayout) {

			if (flipView.isFlipByTouchEnabled()) {
				flipView.setFlipByTouchEnabled(false);
				for (int i = 0; i < ((FrameLayout) v).getChildCount(); i++) {
					((FrameLayout) v).getChildAt(i).setOnTouchListener(
							ReviewerActivity.this);
				}
			} else {
				flipView.setFlipByTouchEnabled(true);
				for (int i = 0; i < ((FrameLayout) v).getChildCount(); i++) {
					((FrameLayout) v).getChildAt(i).setOnTouchListener(null);
				}
			}
		}
		return true;
	}

	ServiceStatusListener reviewerLoginListener = new ServiceStatusListener() {

		@Override
		public void onSuccess(Object object) {
			// TODO handle resposne
			new ServiceRequestHelper().fetchDesigns(fetchDesignerListener);

		}

		@Override
		public void onFailure(Exception exception) {
			checkerror(exception);
		}

	};

	ServiceStatusListener fetchDesignerListener = new ServiceStatusListener() {

		@Override
		public void onSuccess(Object object) {
			ReviewerDesignBean designBean = new parseReviewerDesignXml()
					.parseReviewerDesignXml(object.toString());
			ReviewerAlbumAdapter adapter = new ReviewerAlbumAdapter(
					ReviewerActivity.this, designBean,
					flipView.getMeasuredHeight()
							/ (double) designBean.getHeight(),
					(flipView.getMeasuredWidth() / (double) 2)
							/ designBean.getWidth());

			flipView.setAdapter(adapter);

		}

		@Override
		public void onFailure(Exception exception) {
			checkerror(exception);
		}
	};

	private void checkerror(Exception exception) {
		// TODO Auto-generated method stub

	}
}
