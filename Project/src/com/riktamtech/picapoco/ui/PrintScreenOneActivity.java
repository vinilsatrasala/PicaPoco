package com.riktamtech.picapoco.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ToggleButton;

import com.riktamtech.picapoco.R;

public class PrintScreenOneActivity extends Activity implements OnClickListener {
	private ImageView rejectButton;
	private ImageView acceptButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_print_screen_one);
		acceptButton = (ImageView) findViewById(R.id.AcceptButton);
		rejectButton = (ImageView) findViewById(R.id.CloseButton);
		((RadioGroup) findViewById(R.id.radioGroupCover))
				.setOnCheckedChangeListener(ToggleListener);
		((RadioGroup) findViewById(R.id.radioGroupSize))
				.setOnCheckedChangeListener(ToggleListener);
		((RadioGroup) findViewById(R.id.radioGroupPaper))
				.setOnCheckedChangeListener(ToggleListener);
		acceptButton.setOnClickListener(this);
		rejectButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {
		case R.id.AcceptButton:
			startActivity(new Intent(PrintScreenOneActivity.this,
					PrintScreenTwoActivity.class)
					.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY));
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

			for (int j = 0; j < ((RelativeLayout) radioGroup.getChildAt(1))
					.getChildCount(); j++) {
				if (((RelativeLayout) radioGroup.getChildAt(1)).getChildAt(j) instanceof ToggleButton) {
					final ToggleButton view = (ToggleButton) ((RelativeLayout) radioGroup
							.getChildAt(1)).getChildAt(j);
					view.setChecked(view.getId() == i);
				}
			}
		}
	};

	public void onToggle(View view) {
		((RadioGroup) view.getParent().getParent()).check(view.getId());
		// app specific stuff ..
	}
}
