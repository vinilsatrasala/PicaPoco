package com.riktamtech.picapoco.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

import com.riktamtech.picapoco.R;

public class MyProfileActivity extends Activity implements OnClickListener {
	private boolean isInEditMode = false;
	private ImageView menu_confirmButton;
	private ImageView homeButton;
	private ViewSwitcher profileViewSwitcher;
	private FrameLayout friendsFrameButton;
	private FrameLayout friendRequestsButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_myprofile);
		menu_confirmButton = (ImageView) findViewById(R.id.Menu_AcceptButton);
		homeButton = (ImageView) findViewById(R.id.HomeButton);
		friendsFrameButton = (FrameLayout) findViewById(R.id.FriendsFrameButton);
		friendRequestsButton = (FrameLayout) findViewById(R.id.FriendRequestsFrameButton);
		profileViewSwitcher = (ViewSwitcher) findViewById(R.id.profileViewSwitcher);

		homeButton.setOnClickListener(this);
		menu_confirmButton.setOnClickListener(this);
		friendsFrameButton.setOnClickListener(this);
		friendRequestsButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.HomeButton:
			startActivity(new Intent(MyProfileActivity.this,
					StartActivity.class)
					.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
			break;
		case R.id.Menu_AcceptButton:
			if (!isInEditMode) {
				isInEditMode = true;
				menu_confirmButton.setImageDrawable(getResources().getDrawable(
						R.drawable.tab_accept_selector));
				profileViewSwitcher.showNext();

			} else {
				isInEditMode = false;
				menu_confirmButton.setImageDrawable(getResources().getDrawable(
						R.drawable.tab_menu_selector));
				profileViewSwitcher.showPrevious();

			}
			break;
		case R.id.FriendsFrameButton:
			startActivity(new Intent(MyProfileActivity.this,
					MyFriendsActivity.class)
					.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY));
			break;

		case R.id.FriendRequestsFrameButton:
			startActivity(new Intent(MyProfileActivity.this,
					FriendRequestsActivity.class));
			break;

		default:
			break;
		}
	}
}
