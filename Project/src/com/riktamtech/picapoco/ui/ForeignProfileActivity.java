package com.riktamtech.picapoco.ui;

import com.riktamtech.picapoco.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class ForeignProfileActivity extends Activity implements OnClickListener {
	private ImageView homeButton, addFriendButton;
	private FrameLayout friendsFrameButton;
	private FrameLayout designFrameButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_foreignprofile);
		addFriendButton = (ImageView) findViewById(R.id.addFriendButton);
		homeButton = (ImageView) findViewById(R.id.HomeButton);
		designFrameButton = (FrameLayout) findViewById(R.id.DesignFrameButton);
		friendsFrameButton = (FrameLayout) findViewById(R.id.FriendsFrameButton);
		homeButton.setOnClickListener(this);
		addFriendButton.setOnClickListener(this);
		friendsFrameButton.setOnClickListener(this);
		designFrameButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.HomeButton:
			startActivity(new Intent(ForeignProfileActivity.this,
					StartActivity.class)
					.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

			break;
		case R.id.FriendsFrameButton:
			startActivity(new Intent(ForeignProfileActivity.this,
					ForeignFriendsActivity.class));

			break;
		case R.id.addFriendButton:
			startActivity(new Intent(ForeignProfileActivity.this,
					AddFriendActivity.class));
			break;
		case R.id.DesignFrameButton:
			startActivity(new Intent(ForeignProfileActivity.this,
					StartActivity.class)
					.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
			break;
		default:
			break;
		}

	}

}
