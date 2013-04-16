package com.riktamtech.picapoco.ui;

import com.riktamtech.picapoco.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class AddFriendActivity extends Activity implements OnClickListener {
	private ImageView homeButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_friendrequest);
		homeButton = (ImageView) findViewById(R.id.HomeButton);
		homeButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.HomeButton:
			startActivity(new Intent(AddFriendActivity.this,
					StartActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
	
			break;
		default:
			break;
		}
	}

}
