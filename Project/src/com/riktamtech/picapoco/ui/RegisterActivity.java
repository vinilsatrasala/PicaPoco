package com.riktamtech.picapoco.ui;

import com.riktamtech.picapoco.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class RegisterActivity extends Activity implements OnClickListener {

	private TextView registerButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registration);
		registerButton = (TextView) findViewById(R.id.registerButton);
		registerButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.registerButton:
       
			break;

		default:
			break;
		}
	}

}
