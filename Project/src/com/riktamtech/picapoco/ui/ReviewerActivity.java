package com.riktamtech.picapoco.ui;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.apache.james.mime4j.io.PositionInputStream;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aphidmobile.flip.FlipViewController;
import com.aphidmobile.flip.FlipViewController.ViewFlipListener;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.riktamtech.picapoco.R;
import com.riktamtech.picapoco.adapters.CommentsAdapter;
import com.riktamtech.picapoco.adapters.ReviewerAlbumAdapter;
import com.riktamtech.picapoco.application.MyApplication.ServiceStatusListener;
import com.riktamtech.picapoco.beans.CommentsBean;
import com.riktamtech.picapoco.beans.ImageBean;
import com.riktamtech.picapoco.beans.ReviewerDesignBean;
import com.riktamtech.picapoco.beans.ReviewerPageBeanDetails;
import com.riktamtech.picapoco.services.ServiceRequestHelper;
import com.riktamtech.picapoco.ui.utils.parseReviewerDesignXml;

public class ReviewerActivity extends Activity implements OnClickListener,
		OnTouchListener, OnLongClickListener, ViewFlipListener {

	private static final int Intent_Edit_Album_Title = 100;
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
	private ReviewerDesignBean designBean;

	ArrayList<ArrayList<ImageBean>> lPageBeans = new ArrayList<ArrayList<ImageBean>>();
	private int _xDelta;
	private int _yDelta;

	protected View page;
	protected ArrayList<View> views = new ArrayList<View>();
	private TextView albumTitleTextView;
	private String albumMode;
	public ArrayList<CommentsBean> rightcommentsBeansArrayList,
			leftcommentsBeansArrayList;
	public TextView chatCountLeft, chatCountRight;
	private EditText leftCommentsEditText;
	private EditText rightCommentsEditText;
	private ImageView leftCommentsTick;
	private ImageView rightCommentsTick;
	private TextView likeCountLeft;
	private TextView likeCountRight;
	private ImageView likeLeftButton;
	private ImageView likeRightButton;

	public static String externalId = "PO2013051553FAF1AEC5";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		albumMode = getIntent().getStringExtra("mode");
		setContentView(R.layout.activity_view_album);

		// TODO remove login by changing actual required one
		new ServiceRequestHelper().reviewerLogin(externalId,
				reviewerLoginListener);

		albumTitleTextView = (TextView) findViewById(R.id.headerTextView);
		homeSaveButton = (ImageView) findViewById(R.id.HomeSaveButton);
		aboutButton = (ImageView) findViewById(R.id.AboutButton);
		printButton = (ImageView) findViewById(R.id.PrintButton);
		shareButton = (ImageView) findViewById(R.id.ShareButton);
		commentsButton = (ImageView) findViewById(R.id.CommentsButton);
		commentLeftTop = (ImageView) findViewById(R.id.chatLeftButton);
		chatCountLeft = (TextView) findViewById(R.id.chatCountLeft);
		commentRightTop = (ImageView) findViewById(R.id.chatRightButton);
		chatCountRight = (TextView) findViewById(R.id.chatCountRight);
		likeCountLeft = (TextView) findViewById(R.id.likeCountLeft);
		likeCountRight = (TextView) findViewById(R.id.likeCountRight);
		likeLeftButton = (ImageView) findViewById(R.id.likeLeftButton);
		likeRightButton = (ImageView) findViewById(R.id.likeRightButton);

		commentLeftTop.setOnClickListener(this);
		commentRightTop.setOnClickListener(this);
		likeLeftButton.setOnClickListener(this);
		likeRightButton.setOnClickListener(this);

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
		flipView = (FlipViewController) findViewById(R.id.flipView);

		flipView.setOverFlipEnabled(false);
		flipView.setOnViewFlipListener(this);

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
			startActivityForResult(new Intent(ReviewerActivity.this,
					EditAlbumTitle.class), Intent_Edit_Album_Title);

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
			Intent sortIntent = new Intent(ReviewerActivity.this,
					ActivityAlbumSort.class);

			sortIntent.putExtra("designBean", designBean);
			startActivity(sortIntent);

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
		case R.id.leftTick:
			if (!leftCommentsEditText.getText().toString().trim().equals("")) {
				getCurrentPageIndexes(flipView.getSelectedItemPosition());
				if (leftPageIndex != -1)
					try {
						new ServiceRequestHelper().postComments(
								designBean.reviewerPageBeanDetailsArrayList
										.get(leftPageIndex).designPageId + "",
								leftCommentsEditText.getText().toString()
										.trim(), leftPostCommentsListener);
						leftCommentsEditText.setText("");
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
			}
			break;
		case R.id.rightTick:
			if (!rightCommentsEditText.getText().toString().trim().equals("")) {
				getCurrentPageIndexes(flipView.getSelectedItemPosition());
				if (rightPageIndex != -1)
					try {
						new ServiceRequestHelper().postComments(
								designBean.reviewerPageBeanDetailsArrayList
										.get(rightPageIndex).designPageId + "",
								rightCommentsEditText.getText().toString()
										.trim(), rightPostCommentsListener);
						rightCommentsEditText.setText("");
					} catch (UnsupportedEncodingException e) {

						e.printStackTrace();
					}
			}
			break;
		case R.id.likeLeftButton:
			if (leftPageIndex != -1)
				try {
					new ServiceRequestHelper().postLike(
							designBean.reviewerPageBeanDetailsArrayList
									.get(leftPageIndex).designPageId + "",
							leftPostLikesListener);

				} catch (UnsupportedEncodingException e) {

					e.printStackTrace();
				}
			break;

		case R.id.likeRightButton:
			if (rightPageIndex != -1)
				try {
					new ServiceRequestHelper().postLike(
							designBean.reviewerPageBeanDetailsArrayList
									.get(rightPageIndex).designPageId + "",
							rightPostLikesListener);

				} catch (UnsupportedEncodingException e) {

					e.printStackTrace();
				}
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
		leftCommentsEditText = (EditText) commentsDialog
				.findViewById(R.id.addCommentEditTextLeft);
		rightCommentsEditText = (EditText) commentsDialog
				.findViewById(R.id.addCommentEditTextRight);
		leftCommentsTick = (ImageView) commentsDialog
				.findViewById(R.id.leftTick);
		rightCommentsTick = (ImageView) commentsDialog
				.findViewById(R.id.rightTick);
		leftCommentsTick.setOnClickListener(this);
		rightCommentsTick.setOnClickListener(this);

		// getComments();

		leftListView.setAdapter(new CommentsAdapter(ReviewerActivity.this,
				R.layout.comment_listitem, leftcommentsBeansArrayList));
		rightListView.setAdapter(new CommentsAdapter(ReviewerActivity.this,
				R.layout.comment_listitem, rightcommentsBeansArrayList));
		commentsDialog.show();
		((TextView) commentsDialog.findViewById(R.id.headerTextView))
				.setTextColor(Color.BLACK);
		commentsDialog.getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		((TextView) commentsDialog.findViewById(R.id.chatCountLeft))
				.setText(chatCountLeft.getText());
		((TextView) commentsDialog.findViewById(R.id.chatCountRight))
				.setText(chatCountRight.getText());
		((TextView) commentsDialog.findViewById(R.id.headerTextView))
				.setText(albumTitleTextView.getText().toString().trim());

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
			new ServiceRequestHelper().fetchDesigns(fetchDesignerListener);

		}

		@Override
		public void onFailure(Exception exception) {
			checkerror(exception);
		}

	};

	ServiceStatusListener leftPostCommentsListener = new ServiceStatusListener() {

		@Override
		public void onSuccess(Object object) {
			getComments(leftPageIndex, getleftCommentsListener);

		}

		@Override
		public void onFailure(Exception exception) {

		}
	};
	ServiceStatusListener rightPostCommentsListener = new ServiceStatusListener() {

		@Override
		public void onSuccess(Object object) {
			getComments(rightPageIndex, getrightCommentsListener);

		}

		@Override
		public void onFailure(Exception exception) {

		}
	};
	ServiceStatusListener fetchDesignerListener = new ServiceStatusListener() {

		@Override
		public void onSuccess(Object object) {
			designBean = new parseReviewerDesignXml()
					.parseReviewerDesignXml(object.toString());
			ReviewerAlbumAdapter adapter = new ReviewerAlbumAdapter(
					ReviewerActivity.this, designBean,
					flipView.getMeasuredHeight()
							/ (double) designBean.getHeight(),
					(flipView.getMeasuredWidth() / (double) 2)
							/ designBean.getWidth(), albumMode);

			flipView.setAdapter(adapter);

			albumTitleTextView.setText(designBean.bookTitle);

		}

		@Override
		public void onFailure(Exception exception) {
			checkerror(exception);
		}
	};
	private int leftPageIndex;
	private int rightPageIndex;

	private void checkerror(Exception exception) {

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case Intent_Edit_Album_Title: {

			if (resultCode == RESULT_OK) {
				albumTitleTextView.setText(data.getStringExtra("Title"));
			}

		}

			break;

		default:
			break;
		}

	}

	private void getCurrentPageIndexes(int position) {
		if (position == 0) {
			leftPageIndex = -1;
			rightPageIndex = 1;
		} else if (position == flipView.getCount() - 1) {
			rightPageIndex = -1;
			leftPageIndex = 0;
		} else if (position == 1) {
			rightPageIndex = 2;
			leftPageIndex = -1;
		} else if (position == flipView.getCount() - 2) {
			rightPageIndex = -1;
			leftPageIndex = designBean.reviewerPageBeanDetailsArrayList.size() - 1;
		} else {
			leftPageIndex = 2 * position - 1;
			rightPageIndex = 2 * position;
		}
	}

	@Override
	public void onViewFlipped(View view, int position) {
		getCurrentPageIndexes(position);
		if (leftPageIndex == -1) {
			chatCountLeft.setText("0");
			likeCountLeft.setText("0");

		}
		if (rightPageIndex == -1) {
			chatCountRight.setText("0");
			likeCountRight.setText("0");
		}
		getComments(leftPageIndex, getleftCommentsListener);
		getComments(rightPageIndex, getrightCommentsListener);
		getLikes(leftPageIndex, getLeftLikesListener);
		getLikes(rightPageIndex, getRightLikesListener);
	}

	public void getComments(int PageIndex, ServiceStatusListener listener) {
		// Getting page beans
		if (PageIndex != -1) {
			try {
				new ServiceRequestHelper().getComments(
						""
								+ designBean.reviewerPageBeanDetailsArrayList
										.get(PageIndex).designPageId, "0",
						"30", listener);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}

	}

	public void getLikes(int PageIndex, ServiceStatusListener listener) {
		// Getting page beans
		if (PageIndex != -1) {
			try {
				new ServiceRequestHelper().getLikes(
						""
								+ designBean.reviewerPageBeanDetailsArrayList
										.get(PageIndex).designPageId, "", "",
						listener);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}

	}

	ServiceStatusListener getleftCommentsListener = new ServiceStatusListener() {

		@Override
		public void onSuccess(Object object) {
			try {
				leftcommentsBeansArrayList = new ArrayList<CommentsBean>();
				JSONObject commentsObject = new JSONObject(object.toString());

				chatCountLeft.setText(""
						+ commentsObject.getInt("commentCount"));
				for (int i = 0; i < commentsObject.getInt("commentCount"); i++) {
					CommentsBean commentsBean = new CommentsBean();
					commentsBean.user = commentsObject.getJSONArray("comments")
							.getJSONObject(i).getJSONObject("user")
							.getString("name");
					commentsBean.Comment = commentsObject
							.getJSONArray("comments").getJSONObject(i)
							.getString("comment");
					commentsBean.Date = commentsObject
							.getJSONArray("comments")
							.getJSONObject(i)
							.getString("created_at")
							.toString()
							.substring(
									0,
									commentsObject.getJSONArray("comments")
											.getJSONObject(i)
											.getString("created_at").toString()
											.indexOf("T"));
					leftcommentsBeansArrayList.add(commentsBean);

				}
				if (commentsDialog.isShowing()) {
					((BaseAdapter) leftListView.getAdapter())
							.notifyDataSetChanged();
					leftListView.setAdapter(new CommentsAdapter(
							ReviewerActivity.this, R.layout.comment_listitem,
							leftcommentsBeansArrayList));
					((TextView) commentsDialog.findViewById(R.id.chatCountLeft))
							.setText(chatCountLeft.getText());

				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void onFailure(Exception exception) {

		}
	};
	ServiceStatusListener getrightCommentsListener = new ServiceStatusListener() {

		@Override
		public void onSuccess(Object object) {
			try {
				rightcommentsBeansArrayList = new ArrayList<CommentsBean>();
				JSONObject commentsObject = new JSONObject(object.toString());
				chatCountRight.setText(""
						+ commentsObject.getInt("commentCount"));
				for (int i = 0; i < commentsObject.getInt("commentCount"); i++) {
					CommentsBean commentsBean = new CommentsBean();
					commentsBean.user = commentsObject.getJSONArray("comments")
							.getJSONObject(i).getJSONObject("user")
							.getString("name");
					commentsBean.Comment = commentsObject
							.getJSONArray("comments").getJSONObject(i)
							.getString("comment");
					commentsBean.Date = commentsObject
							.getJSONArray("comments")
							.getJSONObject(i)
							.getString("created_at")
							.toString()
							.substring(
									0,
									commentsObject.getJSONArray("comments")
											.getJSONObject(i)
											.getString("created_at").toString()
											.indexOf("T"));
					rightcommentsBeansArrayList.add(commentsBean);

				}
				if (commentsDialog.isShowing()) {
					((BaseAdapter) rightListView.getAdapter())
							.notifyDataSetChanged();
					rightListView.setAdapter(new CommentsAdapter(
							ReviewerActivity.this, R.layout.comment_listitem,
							rightcommentsBeansArrayList));
					((TextView) commentsDialog.findViewById(R.id.chatCountLeft))
							.setText(chatCountLeft.getText());
					((TextView) commentsDialog
							.findViewById(R.id.chatCountRight))
							.setText(chatCountRight.getText());
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			Log.d("alpha", object.toString());
		}

		@Override
		public void onFailure(Exception exception) {

		}
	};

	ServiceStatusListener getRightLikesListener = new ServiceStatusListener() {

		@Override
		public void onSuccess(Object object) {

			object.toString();
		}

		@Override
		public void onFailure(Exception exception) {

		}
	};
	ServiceStatusListener getLeftLikesListener = new ServiceStatusListener() {

		@Override
		public void onSuccess(Object object) {
			// TODO Auto-generated method stub
			object.toString();
		}

		@Override
		public void onFailure(Exception exception) {

		}
	};

	ServiceStatusListener leftPostLikesListener = new ServiceStatusListener() {

		@Override
		public void onSuccess(Object object) {
			object.toString();
		}

		@Override
		public void onFailure(Exception exception) {

		}
	};

	ServiceStatusListener rightPostLikesListener = new ServiceStatusListener() {

		@Override
		public void onSuccess(Object object) {
			object.toString();
		}

		@Override
		public void onFailure(Exception exception) {

		}
	};
}
