package com.riktamtech.picapoco.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;

import com.riktamtech.picapoco.R;
import com.riktamtech.picapoco.adapters.FriendsAdapter;

public class ForeignFriendsActivity extends Activity implements OnClickListener {
	private FriendsAdapter friendsAdapter;
	private ListView friendsList;

	private ImageView homeButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		friendsAdapter = new FriendsAdapter(ForeignFriendsActivity.this, 3,
				R.layout.friend_listitem);
		setContentView(R.layout.activity_foreign_friends);
		friendsList = (ListView) findViewById(R.id.friendslistView);

		homeButton = (ImageView) findViewById(R.id.HomeButton);
		homeButton.setOnClickListener(this);

		friendsList.setAdapter(friendsAdapter);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.HomeButton:
			startActivity(new Intent(ForeignFriendsActivity.this,
					StartActivity.class)
					.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

			break;
		default:
			break;
		}
	}

}
