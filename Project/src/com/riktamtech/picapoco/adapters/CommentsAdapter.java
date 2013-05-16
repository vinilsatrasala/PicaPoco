package com.riktamtech.picapoco.adapters;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.riktamtech.picapoco.R;
import com.riktamtech.picapoco.adapters.ReviewerAlbumAdapter.PageHolder;
import com.riktamtech.picapoco.beans.CommentsBean;

public class CommentsAdapter extends BaseAdapter {

	private Context context;
	private int layoutResourceId;
	private ArrayList<CommentsBean> commentsBeansArrayList;

	public CommentsAdapter(Context context, int layoutResourceId,
			ArrayList<CommentsBean> commentsBeansArrayList) {
		this.context = context;
		this.layoutResourceId = layoutResourceId;
		this.commentsBeansArrayList = commentsBeansArrayList;
	}

	@Override
	public int getCount() {
		return commentsBeansArrayList == null ? 0 : commentsBeansArrayList
				.size();
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
		CommentHolder holder = null;
		if (row == null) {

			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, parent, false);
			holder = new CommentHolder();
			holder.User = (TextView) row.findViewById(R.id.nameTextView);
			holder.Date = (TextView) row.findViewById(R.id.dateTextView);
			holder.Comment = (TextView) row.findViewById(R.id.commentTextView);
			row.setTag(holder);
		} else {

			holder = (CommentHolder) row.getTag();

		}
		holder.User.setText(commentsBeansArrayList.get(position).user + " | ");
		holder.Date.setText(commentsBeansArrayList.get(position).Date);
		holder.Comment.setText(commentsBeansArrayList.get(position).Comment);
		return row;
	}

	static class CommentHolder {

		TextView User, Comment, Date;

	}
}
