package com.riktamtech.picapoco.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.ToggleButton;
import android.widget.ViewSwitcher;

import com.riktamtech.picapoco.R;

public class MyProfileActivity extends Activity implements OnClickListener {
	private boolean isInEditMode = false;
	private ImageView menu_confirmButton;
	private ImageView homeButton;
	private ViewSwitcher profileViewSwitcher;
	private FrameLayout friendsFrameButton;
	private FrameLayout friendRequestsButton;
	private FrameLayout designFrameButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_myprofile);
		menu_confirmButton = (ImageView) findViewById(R.id.Menu_AcceptButton);
		homeButton = (ImageView) findViewById(R.id.HomeButton);
		friendsFrameButton = (FrameLayout) findViewById(R.id.FriendsFrameButton);
		friendRequestsButton = (FrameLayout) findViewById(R.id.FriendRequestsFrameButton);
		profileViewSwitcher = (ViewSwitcher) findViewById(R.id.profileViewSwitcher);
		designFrameButton = (FrameLayout) findViewById(R.id.DesignFrameButton);
		((RadioGroup) findViewById(R.id.GenderRadioGroup))
				.setOnCheckedChangeListener(ToggleListener);

		homeButton.setOnClickListener(this);
		menu_confirmButton.setOnClickListener(this);
		friendsFrameButton.setOnClickListener(this);
		friendRequestsButton.setOnClickListener(this);
		designFrameButton.setOnClickListener(this);

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
		case R.id.DesignFrameButton:
			startActivity(new Intent(MyProfileActivity.this,
					StartActivity.class)
					.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
			break;
		default:
			break;
		}
	}

	static final RadioGroup.OnCheckedChangeListener ToggleListener = new RadioGroup.OnCheckedChangeListener() {
		@Override
		public void onCheckedChanged(final RadioGroup radioGroup, final int i) {
			for (int j = 0; j < radioGroup.getChildCount(); j++) {
				if (radioGroup.getChildAt(j) instanceof ToggleButton) {
					final ToggleButton view = (ToggleButton) radioGroup
							.getChildAt(j);
					view.setChecked(view.getId() == i);
				}
			}
		}
	};

	public void onToggle(View view) {
		((RadioGroup) view.getParent()).check(view.getId());
		// app specific stuff ..
	}
}
