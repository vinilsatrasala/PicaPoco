package com.riktamtech.picapoco.ui;

import com.riktamtech.picapoco.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class ShareActivity extends Activity implements OnClickListener {
	private ImageView rejectButton;
	private ImageView acceptButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		setContentView(R.layout.activity_share);
		acceptButton = (ImageView) findViewById(R.id.AcceptButton);
		rejectButton = (ImageView) findViewById(R.id.CloseButton);
		((TextView) findViewById(R.id.AGBs)).setText(Html
				.fromHtml(getResources().getString(R.string.share_terms)));
		acceptButton.setOnClickListener(this);
		rejectButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.AcceptButton:
			finish();
			break;
		case R.id.CloseButton:
			finish();
			break;
		default:
			break;
		}
	}
}
