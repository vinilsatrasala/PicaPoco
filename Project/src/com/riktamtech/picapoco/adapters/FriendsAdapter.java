package com.riktamtech.picapoco.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.riktamtech.picapoco.R;

public class FriendsAdapter extends BaseAdapter {

	private int normalMode = 1;
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
			row.setTag(holder);
		} else {
			holder = (ViewHolder) row.getTag();
		}

		if (mode == normalMode) {
			holder.trash.setVisibility(View.GONE);
			holder.designsFriendsLayout.setVisibility(View.VISIBLE);
		} else {
			holder.trash.setVisibility(View.VISIBLE);
			holder.designsFriendsLayout.setVisibility(View.GONE);

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
	}
}
