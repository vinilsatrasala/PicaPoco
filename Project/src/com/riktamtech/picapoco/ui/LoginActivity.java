package com.riktamtech.picapoco.ui;

import com.riktamtech.picapoco.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class LoginActivity extends Activity implements OnClickListener {
	private TextView loginButton;
	private RelativeLayout Register;
	private RelativeLayout forgotPassword;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		setContentView(R.layout.activity_login);
		loginButton = (TextView) findViewById(R.id.LoginButton);
		Register = (RelativeLayout) findViewById(R.id.registerRelativeLayout);
		forgotPassword = (RelativeLayout) findViewById(R.id.ForgotPasswordRelativeLayout);
		loginButton.setOnClickListener(this);
		Register.setOnClickListener(this);
		forgotPassword.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.LoginButton:
			finish();
			break;
		case R.id.registerRelativeLayout:
			startActivity(new Intent(LoginActivity.this, RegisterActivity.class)
					.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY));
			finish();
			break;
		case R.id.ForgotPasswordRelativeLayout:
			startActivity(new Intent(LoginActivity.this, ForgotPassword.class)
					.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY));
			break;
		default:
			break;
		}
	}
}
