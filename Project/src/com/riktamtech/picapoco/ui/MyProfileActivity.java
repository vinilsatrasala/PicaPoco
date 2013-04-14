package com.riktamtech.picapoco.ui;

import com.riktamtech.picapoco.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

public class MyProfileActivity extends Activity implements OnClickListener {
	private boolean isInEditMode = false;
	private ImageView menu_confirmButton;
	private ImageView homeButton;
	private ViewSwitcher profileViewSwitcher;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_myprofile);
		menu_confirmButton = (ImageView) findViewById(R.id.Menu_AcceptButton);
		homeButton = (ImageView) findViewById(R.id.HomeButton);
		profileViewSwitcher = (ViewSwitcher) findViewById(R.id.profileViewSwitcher);
		homeButton.setOnClickListener(this);
		menu_confirmButton.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.HomeButton:
			startActivity(new Intent(MyProfileActivity.this, StartActivity.class));
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
		default:
			break;
		}
	}

}
