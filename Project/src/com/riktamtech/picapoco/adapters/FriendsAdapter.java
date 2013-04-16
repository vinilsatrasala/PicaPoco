package com.riktamtech.picapoco.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.riktamtech.picapoco.R;
import com.riktamtech.picapoco.ui.AddFriendActivity;
import com.riktamtech.picapoco.ui.ForeignFriendsActivity;
import com.riktamtech.picapoco.ui.ForeignProfileActivity;
import com.riktamtech.picapoco.ui.MyFriendsActivity;
import com.riktamtech.picapoco.ui.StartActivity;

public class FriendsAdapter extends BaseAdapter implements OnClickListener {

	private int normalMode = 1, editmode = 2;

	private int mode;
	private Context context;
	private int layoutResourceId;

	public FriendsAdapter(Context context, int mode, int layoutResourceId) {
		this.context = context;
		this.mode = mode;
		this.layoutResourceId = layoutResourceId;
	}

	@Override
	public int getCount() {
		return 5;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		ViewHolder holder = null;

		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, parent, false);

			holder = new ViewHolder();

			holder.trash = (TextView) row.findViewById(R.id.TrashTextView);
			holder.addFriend = (TextView) row
					.findViewById(R.id.addFriendTextView);
			holder.designsFriendsLayout = (LinearLayout) row
					.findViewById(R.id.Designs_Friends_LinearLayout);
			holder.friendsFrameButton = (FrameLayout) row
					.findViewById(R.id.FriendsFrameButton);
			holder.pictureFrame = (FrameLayout) row
					.findViewById(R.id.ProfilePicFrame);
			holder.designsFrameButton = (FrameLayout) row
					.findViewById(R.id.DesignFrameButton);
			holder.addFriend.setOnClickListener(this);
			holder.friendsFrameButton.setOnClickListener(this);
			holder.pictureFrame.setOnClickListener(this);
			holder.designsFrameButton.setOnClickListener(this);
			row.setTag(holder);
		} else {
			holder = (ViewHolder) row.getTag();
		}

		if (mode == normalMode) {
			holder.trash.setVisibility(View.GONE);
			holder.designsFriendsLayout.setVisibility(View.VISIBLE);
			holder.addFriend.setVisibility(View.GONE);

		} else if (mode == editmode) {
			holder.trash.setVisibility(View.VISIBLE);
			holder.designsFriendsLayout.setVisibility(View.GONE);
			holder.addFriend.setVisibility(View.GONE);
		} else {
			holder.trash.setVisibility(View.GONE);
			holder.designsFriendsLayout.setVisibility(View.VISIBLE);
			holder.addFriend.setVisibility(View.VISIBLE);
		}

		return row;
	}

	public void changeMode(int friendMode) {
		mode = friendMode;
		notifyDataSetChanged();
	}

	static class ViewHolder {
		TextView trash;
		TextView addFriend;
		LinearLayout designsFriendsLayout;
		FrameLayout friendsFrameButton, pictureFrame, designsFrameButton;
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.addFriendTextView:
			context.startActivity(new Intent(context, AddFriendActivity.class));
			break;
		case R.id.FriendsFrameButton:
			if (context instanceof MyFriendsActivity)
				context.startActivity(new Intent(context,
						ForeignFriendsActivity.class));
			else {
				context.startActivity(new Intent(context,
						ForeignFriendsActivity.class));
				((Activity) context).finish();
			}

			break;
		case R.id.ProfilePicFrame:
			context.startActivity(new Intent(context,
					ForeignProfileActivity.class));

			break;

		case R.id.DesignFrameButton:
			context.startActivity(new Intent(context, StartActivity.class)
					.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
			break;
		default:
			break;
		}
	}
}
