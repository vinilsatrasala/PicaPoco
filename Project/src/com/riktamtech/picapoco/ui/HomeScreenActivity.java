package com.riktamtech.picapoco.ui;

import com.riktamtech.picapoco.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class HomeScreenActivity extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_screen);
		TextView uploaderModuleTextView = (TextView) findViewById(R.id.makeFreeDesignTestView);
		TextView communityModuleTextView = (TextView) findViewById(R.id.viewOrPrintAlbum);
		TextView moreIntoTextView = (TextView) findViewById(R.id.moreInfoTextView);

		uploaderModuleTextView.setOnClickListener(this);
		communityModuleTextView.setOnClickListener(this);
		moreIntoTextView.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.makeFreeDesignTestView:
			startActivity(new Intent(HomeScreenActivity.this,
					StyleSelectActivity.class));

			break;
		case R.id.viewOrPrintAlbum:

			break;
		case R.id.moreInfoTextView:

			break;

		default:
			break;
		}

	}
}
