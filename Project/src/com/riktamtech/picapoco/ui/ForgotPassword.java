package com.riktamtech.picapoco.ui;

import com.riktamtech.picapoco.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class ForgotPassword extends Activity implements OnClickListener {
	private TextView requestPasswordButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		setContentView(R.layout.activity_forgotpassword);
		requestPasswordButton = (TextView) findViewById(R.id.requestPasswordButton);
		requestPasswordButton.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.requestPasswordButton:
			startActivity(new Intent(ForgotPassword.this, LoginActivity.class));
			break;

		default:
			break;
		}
	}

}
