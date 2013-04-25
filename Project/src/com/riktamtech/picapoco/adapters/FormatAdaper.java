package com.riktamtech.picapoco.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.riktamtech.picapoco.R;
import com.riktamtech.picapoco.application.MyApplication;
import com.riktamtech.picapoco.ui.FormatSelectActivity;

public class FormatAdaper extends BaseAdapter {

	Activity activity;
	Context context;
	LayoutInflater layoutInflater;
	RadioGroup rgp;
	private RadioButton mSelectedRB;
	private int mSelectedPosition = -1;

	private String[] albumnnames = { "Quer", "Quadrat", "Hoch" };
	private int[] albumResources = { R.drawable.quer, R.drawable.quadrat,
			R.drawable.hoch };

	public FormatAdaper(Context context) {
		this.activity = (Activity) context;
		this.context = context;
		rgp = new RadioGroup(context);
		layoutInflater = (LayoutInflater) activity.getLayoutInflater();

	}

	@Override
	public int getCount() {

		return albumResources.length;
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		View view = convertView;
		final FormatHolder FormatHolder;

		if (view == null) {
			view = layoutInflater.inflate(R.layout.style_selector_grid_item,
					null);
			FormatHolder = new FormatHolder();
			FormatHolder.image = (ImageView) view.findViewById(R.id.thumbImage);
			FormatHolder.radioButton = (RadioButton) view
					.findViewById(R.id.itemCheckBox);
			FormatHolder.infoImageView = (ImageView) view
					.findViewById(R.id.infoImage);
			view.setTag(FormatHolder);

		} else {

			FormatHolder = (FormatHolder) view.getTag();
		}
		// Setting images
		Log.d("albumResources[position]", "" + albumResources[position]);
		FormatHolder.image.setImageResource(albumResources[position]);
		FormatHolder.image.setOnClickListener(new OnClickListener() {

			@SuppressLint("NewApi")
			@Override
			public void onClick(View v) {
				FormatHolder.radioButton.performClick();
				FormatHolder.radioButton.setChecked(true);
			}
		});
		FormatHolder.radioButton.setText(albumnnames[position]);

		FormatHolder.radioButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				if ((position != mSelectedPosition && mSelectedRB != null)) {
					mSelectedRB.setChecked(false);
				}

				mSelectedPosition = position;
				mSelectedRB = (RadioButton) v;
				System.out.println("changing album type");
				switch (position) {
				case 0:
					MyApplication.currentAlbum.albumType = MyApplication.AlbumTypeAlbum;
					break;
				case 1:
					MyApplication.currentAlbum.albumType = MyApplication.AlbumTypeKlassisch;
					break;
				case 2:
					MyApplication.currentAlbum.albumType = MyApplication.AlbumTypeModern;
					break;
				case 3:
					MyApplication.currentAlbum.albumType = MyApplication.AlbumTypeFotoPur;
					break;
				case 4:
					MyApplication.currentAlbum.albumType = MyApplication.AlbumTypeFrisch;
					break;
				case 5:
					MyApplication.currentAlbum.albumType = MyApplication.AlbumTypeVintage;
					break;

				default:
					break;
				}
				if (MyApplication.currentAlbum.albumType == 0)
					((FormatSelectActivity) activity).nextTextView
							.setEnabled(false);
				else
					((FormatSelectActivity) activity).nextTextView
							.setEnabled(true);
			}
		});

		FormatHolder.infoImageView.setVisibility(View.GONE);
		if (mSelectedPosition != position) {
			FormatHolder.radioButton.setChecked(false);
		} else {
			FormatHolder.radioButton.setChecked(true);

			if (mSelectedRB != null && FormatHolder.radioButton != mSelectedRB) {
				mSelectedRB = FormatHolder.radioButton;
			}
		}

		return view;
	}
}

class FormatHolder {

	ImageView image, infoImageView;
	RadioButton radioButton;

}