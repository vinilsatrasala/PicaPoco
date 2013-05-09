package com.riktamtech.picapoco.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.riktamtech.picapoco.R;
import com.riktamtech.picapoco.ui.ReviewerActivity;

public class HomeGridAdapter extends BaseAdapter implements OnClickListener {

	private Context context;

	public HomeGridAdapter(Context context) {
		this.context = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 3;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View row = convertView;
		ImageHolder holder = null;

		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(R.layout.griditem, parent, false);

			holder = new ImageHolder();

			holder.image = (ImageView) row
					.findViewById(R.id.albumCoverImageView);
			holder.share_add = (ImageView) row
					.findViewById(R.id.add_shareButton);
			holder.albumLayout = (LinearLayout) row
					.findViewById(R.id.albumLayout);
			holder.share_add.setOnClickListener(this);
			holder.albumLayout.setOnClickListener(this);

			row.setTag(holder);
		} else {
			holder = (ImageHolder) row.getTag();
		}
		holder.albumLayout.setTag(position);
		if ((position + 1) % 3 == 0) {
			holder.image.setImageDrawable(context.getResources().getDrawable(
					R.drawable.start_album_cover));
		} else if ((position + 1) % 3 == 1) {
			holder.image.setImageDrawable(context.getResources().getDrawable(
					R.drawable.start_album_cover));
		} else {
			holder.image.setImageDrawable(context.getResources().getDrawable(
					R.drawable.start_album_cover_own));
		}

		return row;
	}

	static class ImageHolder {

		ImageView image, share_add;
		LinearLayout albumLayout;

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.albumLayout:
			if (Integer.parseInt(v.getTag().toString()) == 1)
				context.startActivity(new Intent(context,
						ReviewerActivity.class).putExtra("mode", "view"));
			else
				context.startActivity(new Intent(context,
						ReviewerActivity.class).putExtra("mode", "edit"));
			break;
		case R.id.add_shareButton:
			break;
		default:
			break;
		}
	}

}
