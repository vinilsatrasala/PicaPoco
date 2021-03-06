package com.riktamtech.picapoco.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.ToggleButton;

import com.riktamtech.picapoco.R;

public class PrintScreenTwoActivity extends Activity implements OnClickListener {
	private ImageView rejectButton;
	private ImageView acceptButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		setContentView(R.layout.activity_print_screen_two);
		acceptButton = (ImageView) findViewById(R.id.AcceptButton);
		rejectButton = (ImageView) findViewById(R.id.CloseButton);
		((RadioGroup) findViewById(R.id.GenderRadioGroup))
				.setOnCheckedChangeListener(ToggleListener);
		acceptButton.setOnClickListener(this);
		rejectButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {
		case R.id.AcceptButton:
			startActivity(new Intent(PrintScreenTwoActivity.this,
					ReviewerActivity.class));
			break;
		case R.id.CloseButton:
			finish();
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
