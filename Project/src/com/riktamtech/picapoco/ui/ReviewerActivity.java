package com.riktamtech.picapoco.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.riktamtech.picapoco.R;
import com.riktamtech.picapoco.adapters.CommentsAdapter;

public class ReviewerActivity extends Activity implements OnClickListener {

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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		setContentView(R.layout.activity_view_album);

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
		// TODO Auto-generated method stub
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
						// TODO Auto-generated method stub
						commentsDialog.dismiss();
					}
				});
		((ImageView) commentsDialog.findViewById(R.id.chatRightButton))
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
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
		commentsDialog.getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
	}

}
