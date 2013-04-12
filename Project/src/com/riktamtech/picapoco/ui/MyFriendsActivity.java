package com.riktamtech.picapoco.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;

import com.riktamtech.picapoco.R;
import com.riktamtech.picapoco.adapters.FriendsAdapter;

public class MyFriendsActivity extends Activity implements OnClickListener {

	private ImageView menu_confirmButton;
	private ImageView homeButton;
	private FriendsAdapter friendsAdapter;
	private ListView friendsList;
	private int normalMode = 1, editMode = 2;
	private boolean isInEditMode = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		friendsAdapter = new FriendsAdapter(MyFriendsActivity.this, 1,
				R.layout.friend_listitem);

		setContentView(R.layout.activity_friends);
		friendsList = (ListView) findViewById(R.id.friendslistView);
		menu_confirmButton = (ImageView) findViewById(R.id.Menu_AcceptButton);
		homeButton = (ImageView) findViewById(R.id.HomeButton);
		homeButton.setOnClickListener(this);
		menu_confirmButton.setOnClickListener(this);
		friendsList.setAdapter(friendsAdapter);

	}

	@Override
	protected void onResume() {
		super.onResume();

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.HomeButton:

			break;
		case R.id.Menu_AcceptButton:
			if (!isInEditMode) {
				isInEditMode = true;
				menu_confirmButton.setImageDrawable(getResources().getDrawable(
						R.drawable.tab_accept_selector));
				friendsAdapter.changeMode(editMode);
			} else {
				isInEditMode = false;
				menu_confirmButton.setImageDrawable(getResources().getDrawable(
						R.drawable.tab_menu_selector));
				friendsAdapter.changeMode(normalMode);
			}
			break;
		default:
			break;
		}
	}

}
