package com.riktamtech.picapoco.ui;

import com.riktamtech.picapoco.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class ActivityAlbumSort extends Activity implements OnClickListener {
	private ImageView acceptButton;
	private ImageView rejectButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sortalbum);
		acceptButton = (ImageView) findViewById(R.id.AcceptButton);
		rejectButton = (ImageView) findViewById(R.id.CloseButton);
		acceptButton.setOnClickListener(this);
		rejectButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.AcceptButton:
			startActivity(new Intent(ActivityAlbumSort.this,
					ReviewerActivity.class));
			break;
		case R.id.CloseButton:
			finish();
			break;
		default:
			break;
		}

	}

}
