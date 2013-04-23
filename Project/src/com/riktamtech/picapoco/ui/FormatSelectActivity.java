package com.riktamtech.picapoco.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.riktamtech.picapoco.R;
import com.riktamtech.picapoco.adapters.FormatAdaper;
import com.riktamtech.picapoco.application.MyApplication;

public class FormatSelectActivity extends Activity implements OnClickListener {

	public TextView nextTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.activity_login_screen);
		setContentView(R.layout.activity_style_select);

		GridView gridView = (GridView) findViewById(R.id.gridView1);
		gridView.setAdapter(new FormatAdaper(this));

		nextTextView = (TextView) findViewById(R.id.nextTextView);
		nextTextView.setOnClickListener(this);
		nextTextView.setEnabled(false);

		// Bottom menu

		ViewGroup bottomMenuGroup = (ViewGroup) findViewById(R.id.bottomMenuGroup);
		((MyApplication) getApplication()).initializeBottomMenu(this,
				bottomMenuGroup);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == nextTextView) {
			// TODO change flow
			startActivity(new Intent(FormatSelectActivity.this,
					ImagePickerActivity.class));
		}

	}
}
