package com.riktamtech.picapoco.ui;

import java.io.UnsupportedEncodingException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;

import com.riktamtech.picapoco.R;
import com.riktamtech.picapoco.application.MyApplication.ServiceStatusListener;
import com.riktamtech.picapoco.services.ServiceRequestHelper;

public class EditAlbumTitle extends Activity implements OnClickListener,
		TextWatcher {
	private ImageView rejectButton;
	private ImageView acceptButton;
	private EditText titleEditText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		setContentView(R.layout.activity_edit_albumtitle);
		titleEditText = (EditText) findViewById(R.id.albumTitleEditText);
		acceptButton = (ImageView) findViewById(R.id.AcceptButton);
		rejectButton = (ImageView) findViewById(R.id.CloseButton);
		acceptButton.setOnClickListener(this);
		rejectButton.setOnClickListener(this);
		titleEditText.addTextChangedListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {
		case R.id.AcceptButton:
			try {
				new ServiceRequestHelper().updateBookTitle(titleEditText
						.getText().toString(), ReviewerActivity.externalId,
						updateTitleListener);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case R.id.CloseButton:
			finish();
			break;
		default:
			break;
		}

	}

	@Override
	public void afterTextChanged(Editable s) {
		// TODO Auto-generated method stub
		if (s == null || s.length() == 0) {
			acceptButton.setEnabled(false);
		} else {
			if (titleEditText.getText().toString().equals(""))
				acceptButton.setEnabled(false);
			else {
				acceptButton.setEnabled(true);
			}
		}
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO Auto-generated method stub

	}

	ServiceStatusListener updateTitleListener = new ServiceStatusListener() {

		@Override
		public void onSuccess(Object object) {
			// TODO Auto-generated method stub
			Log.d("alpha", object.toString());
			Intent intent = new Intent();
			intent.putExtra("Title", titleEditText.getText().toString());
			setResult(RESULT_OK, intent);
			finish();

		}

		@Override
		public void onFailure(Exception exception) {
			// TODO Auto-generated method stub

		}
	};
}
