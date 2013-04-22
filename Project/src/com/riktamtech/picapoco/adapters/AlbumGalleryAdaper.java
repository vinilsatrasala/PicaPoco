package com.riktamtech.picapoco.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import com.riktamtech.picapoco.ui.AlbumCatalogInfoActivity;
import com.riktamtech.picapoco.ui.StyleSelectActivity;

public class AlbumGalleryAdaper extends BaseAdapter {

	Activity activity;
	Context context;
	LayoutInflater layoutInflater;
	RadioGroup rgp;
	private RadioButton mSelectedRB;
	private int mSelectedPosition = -1;

	private String[] albumnnames = { "Album", "Klassisch", "Foto Pur",
			"Modern", "Frisch", "Vintage" };
	private int[] albumResources = { R.drawable.album, R.drawable.klassisch,
			R.drawable.foto_pur, R.drawable.modern, R.drawable.frisch,
			R.drawable.vintage };

	public AlbumGalleryAdaper(Context context) {
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
		final Holder holder;

		if (view == null) {
			view = layoutInflater.inflate(R.layout.style_selector_grid_item,
					null);
			holder = new Holder();
			holder.image = (ImageView) view.findViewById(R.id.thumbImage);
			holder.radioButton = (RadioButton) view
					.findViewById(R.id.itemCheckBox);
			holder.infoImageView = (ImageView) view
					.findViewById(R.id.infoImage);
			view.setTag(holder);

		} else {

			holder = (Holder) view.getTag();
		}
		// Setting images
		Log.d("albumResources[position]", "" + albumResources[position]);
		holder.image.setImageResource(albumResources[position]);
		holder.image.setOnClickListener(new OnClickListener() {

			@SuppressLint("NewApi")
			@Override
			public void onClick(View v) {
				holder.radioButton.performClick();
				holder.radioButton.setChecked(true);
			}
		});
		holder.radioButton.setText(albumnnames[position]);

		holder.radioButton.setOnClickListener(new View.OnClickListener() {

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
					((StyleSelectActivity) activity).nextTextView
							.setEnabled(false);
				else
					((StyleSelectActivity) activity).nextTextView
							.setEnabled(true);
			}
		});

		holder.infoImageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(activity,
						AlbumCatalogInfoActivity.class);
				switch (position) {

				case 0:
					intent.putExtra("item", MyApplication.AlbumTypeAlbum);
					break;
				case 1:
					intent.putExtra("item", MyApplication.AlbumTypeKlassisch);
					break;
				case 2:
					intent.putExtra("item", MyApplication.AlbumTypeFotoPur);
					break;
				case 3:
					intent.putExtra("item", MyApplication.AlbumTypeModern);
					break;
				case 4:
					intent.putExtra("item", MyApplication.AlbumTypeFrisch);
					break;
				case 5:
					intent.putExtra("item", MyApplication.AlbumTypeVintage);
					break;

				default:
					break;
				}
				activity.startActivity(intent);
			}
		});

		if (mSelectedPosition != position) {
			holder.radioButton.setChecked(false);
		} else {
			holder.radioButton.setChecked(true);

			if (mSelectedRB != null && holder.radioButton != mSelectedRB) {
				mSelectedRB = holder.radioButton;
			}
		}

		return view;
	}
}

class Holder {

	ImageView image, infoImageView;
	RadioButton radioButton;

}