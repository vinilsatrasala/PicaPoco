package com.riktamtech.picapoco.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.riktamtech.picapoco.R;

public class MyDiamonds extends Activity implements OnClickListener {

	private ImageView homeButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_diamonds);
		homeButton = (ImageView) findViewById(R.id.HomeButton);
		homeButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.HomeButton:
			finish();
			break;

		default:
			break;
		}
	}

}
