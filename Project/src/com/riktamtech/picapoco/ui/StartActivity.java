package com.riktamtech.picapoco.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.origamilabs.library.views.StaggeredGridView;
import com.riktamtech.picapoco.R;
import com.riktamtech.picapoco.adapters.HomeGridAdapter;

public class StartActivity extends Activity implements OnClickListener {
	private LinearLayout leftMenu;
	private LinearLayout rightMenu;
	private RelativeLayout FriendsDesigns;
	private RelativeLayout PublicDesigns;
	private RelativeLayout MyDesigns;
	private ImageView homeButton;
	private ImageView menuButton;
	private RelativeLayout myProfile;
	private RelativeLayout myDiamonds;
	private RelativeLayout myFriends;
	private RelativeLayout infoPrice;
	private RelativeLayout logout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
		menuButton = (ImageView) findViewById(R.id.MenuButton);
		homeButton = (ImageView) findViewById(R.id.HomeButton);
		leftMenu = (LinearLayout) findViewById(R.id.menu_Left);
		rightMenu = (LinearLayout) findViewById(R.id.menu_Right);
		PublicDesigns = (RelativeLayout) leftMenu
				.findViewById(R.id.PublicDesignsLayout);
		FriendsDesigns = (RelativeLayout) leftMenu
				.findViewById(R.id.friendDesignsLayout);
		MyDesigns = (RelativeLayout) leftMenu
				.findViewById(R.id.myDesignsLayout);
		myProfile = (RelativeLayout) rightMenu
				.findViewById(R.id.myProfileLayout);
		myDiamonds = (RelativeLayout) rightMenu
				.findViewById(R.id.DiamondsLayout);
		myFriends = (RelativeLayout) rightMenu.findViewById(R.id.FriendsLayout);
		myProfile = (RelativeLayout) rightMenu
				.findViewById(R.id.myProfileLayout);
		infoPrice = (RelativeLayout) rightMenu
				.findViewById(R.id.infoPriceLayout);
		logout = (RelativeLayout) rightMenu.findViewById(R.id.logoutLayout);

		HomeGridAdapter adapter = new HomeGridAdapter(StartActivity.this);
		StaggeredGridView gridView = (StaggeredGridView) findViewById(R.id.staggeredGridView);
		gridView.setAdapter(adapter);
		adapter.notifyDataSetChanged();
		MyDesigns.setOnClickListener(this);
		FriendsDesigns.setOnClickListener(this);
		PublicDesigns.setOnClickListener(this);
		homeButton.setOnClickListener(this);
		menuButton.setOnClickListener(this);
		myFriends.setOnClickListener(this);
		myDiamonds.setOnClickListener(this);
		infoPrice.setOnClickListener(this);
		myProfile.setOnClickListener(this);
		logout.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.HomeButton:
			if (leftMenu.isShown())
				leftMenu.setVisibility(View.GONE);
			else
				leftMenu.setVisibility(View.VISIBLE);
			break;
		case R.id.MenuButton:
			if (rightMenu.isShown())
				rightMenu.setVisibility(View.GONE);
			else
				rightMenu.setVisibility(View.VISIBLE);
			break;

		case R.id.FriendsLayout:
			startActivity(new Intent(StartActivity.this,
					MyFriendsActivity.class));
			break;
		case R.id.DiamondsLayout:
			startActivity(new Intent(StartActivity.this, MyDiamonds.class));
			break;
		case R.id.infoPriceLayout:
			startActivity(new Intent(StartActivity.this,
					InfoPriceActivity.class));
			break;

		case R.id.myProfileLayout:
			startActivity(new Intent(StartActivity.this,
					MyProfileActivity.class));
			break;
		case R.id.logoutLayout:
			startActivity(new Intent(StartActivity.this, LoginActivity.class));
			break;
		default:
			break;
		}
	}
}
