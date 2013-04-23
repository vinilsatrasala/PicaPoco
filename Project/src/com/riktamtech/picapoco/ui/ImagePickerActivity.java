package com.riktamtech.picapoco.ui;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.riktamtech.picapoco.R;
import com.riktamtech.picapoco.application.MyApplication;
import com.riktamtech.picapoco.beans.ImageDetailsBean;

/**
 * This class will help in picking multiple images from gallery
 * 
 * @author Suneel
 * 
 */
public class ImagePickerActivity extends Activity implements OnClickListener {
	Button backButton;
	private ImageAdapter imageAdapter;
	private int selectedCount = 0, selectable = 6;
	private TextView titleTextView;
	private ArrayList<String> selectedImages;
	private ArrayList<ImageDetailsBean> imageDetailsBeansArrayList;
	private Dialog progressDialog;
	private TextView selectAllOrNoneTextView, nextTextView;
	private boolean selectFlagEnabled = false;
	private GridView imagegrid;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_images_select);
		// initializing text views
		imagegrid = (GridView) findViewById(R.id.imagesGridView);
		selectAllOrNoneTextView = (TextView) findViewById(R.id.selectTextView);
		nextTextView = (TextView) findViewById(R.id.nextTextView);
		titleTextView = (TextView) findViewById(R.id.titleTextView);

		selectAllOrNoneTextView.setOnClickListener(this);
		nextTextView.setOnClickListener(this);

		selectedImages = new ArrayList<String>();

		// Setting on click listeners
		// Getting images details
		final String[] columns = { MediaStore.Images.Media.DATA,
				MediaStore.Images.Media._ID,
				MediaStore.Images.Media.DISPLAY_NAME };
		final String orderBy = MediaStore.Images.Media.DATE_TAKEN;
		Cursor imagecursor = managedQuery(
				MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null,
				null, orderBy + " DESC ");
		imageDetailsBeansArrayList = new ArrayList<ImageDetailsBean>();
		// Assigning image details to bean
		imagecursor.moveToFirst();

		// getting indexes

		int image_column_index = imagecursor
				.getColumnIndex(MediaStore.Images.Media._ID);
		int dataColumnIndex = imagecursor
				.getColumnIndex(MediaStore.Images.Media.DATA);
		int nameColumnIndex = imagecursor
				.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME);

		while (!imagecursor.isAfterLast()) {
			ImageDetailsBean bean = new ImageDetailsBean();

			// ThumbNail
			bean.THUMBNAIL_ORIGIN_ID = imagecursor.getInt(image_column_index);
			bean.IMAGE_PATH = imagecursor.getString(dataColumnIndex);
			bean.IMAGE_NAME = imagecursor.getString(nameColumnIndex);

			if (MyApplication.currentAlbum.selectedImagesPaths
					.contains(bean.IMAGE_PATH))
				bean.THUMBNAILSELECTION = true;
			else
				bean.THUMBNAILSELECTION = false;
			imageDetailsBeansArrayList.add(bean);
			imagecursor.moveToNext();
		}

		// Setting Adapter to grid view
		imageAdapter = new ImageAdapter();
		imagegrid.setAdapter(imageAdapter);
		selectedCount = MyApplication.currentAlbum.selectedImagesPaths.size();
		if (selectedCount == imageDetailsBeansArrayList.size()) {
			selectAllOrNoneTextView.setText(R.string.selectnone);
			selectFlagEnabled = !selectFlagEnabled;
		}
		if (selectedCount < 24)
			titleTextView.setText(selectedCount
					+ " Fotos gewählt | Mehr Fotos Wählen");
		else {
			titleTextView.setText(selectedCount
					+ " Fotos gewählt | Ideal für "
					+ ((MyApplication) getApplication()).getIdealPages(
							MyApplication.currentAlbum.designerType,
							selectedCount) + " Seiten");
		}
		// Bottom menu

		ViewGroup bottomMenuGroup = (ViewGroup) findViewById(R.id.bottomMenuGroup);
		((MyApplication) getApplication()).initializeBottomMenu(this,
				bottomMenuGroup);
	}

	public class ImageAdapter extends BaseAdapter {
		private LayoutInflater mInflater;

		public ImageAdapter() {
			mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		public int getCount() {
			return imageDetailsBeansArrayList.size();
		}

		public Object getItem(int position) {
			return position;
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(final int position, View convertView,
				ViewGroup parent) {
			final ViewHolder holder;
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = mInflater.inflate(R.layout.image_picker_item,
						null);
				holder.imageview = (ImageView) convertView
						.findViewById(R.id.thumbImage);
				holder.checkbox = (CheckBox) convertView
						.findViewById(R.id.itemCheckBox);

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			// Retrive Bean
			final ImageDetailsBean bean = imageDetailsBeansArrayList
					.get(position);
			holder.checkbox.setId(position);
			holder.checkbox.setEnabled(false);
			holder.imageview.setId(position);
			holder.checkbox.setVisibility(View.INVISIBLE);
			// Lazy Loader
			MyApplication.imageLoader.displayImage("file://" + bean.IMAGE_PATH,
					holder.imageview, MyApplication.options);

			holder.imageview.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {

					Log.d("ImagePicker", "OnClick called");
					final int id = ((ImageView) v).getId();
					if (bean.THUMBNAILSELECTION) {
						Log.d("ImagePicker", "unselecting");
						MyApplication.currentAlbum.selectedImagesPaths
								.remove(bean.IMAGE_PATH);
						holder.checkbox.setChecked(false);
						bean.THUMBNAILSELECTION = false;
						selectedCount--;
						holder.checkbox.setVisibility(View.INVISIBLE);
						holder.imageview.setAlpha(225);
					} else {
						if (validate()) {
							Log.d("ImagePicker", "selecting");
							MyApplication.currentAlbum.selectedImagesPaths
									.add(bean.IMAGE_PATH);
							holder.checkbox.setChecked(true);
							bean.THUMBNAILSELECTION = true;
							selectedCount++;
							holder.checkbox.setVisibility(View.VISIBLE);
							holder.imageview.setAlpha(155);
						}
					}
					if (selectedCount < 24)
						titleTextView.setText(selectedCount
								+ " Fotos gewählt | Mehr Fotos Wählen");
					else {
						titleTextView.setText(selectedCount
								+ " Fotos gewählt | Ideal für "
								+ ((MyApplication) getApplication())
										.getIdealPages(
												MyApplication.currentAlbum.designerType,
												selectedCount) + " Seiten");
					}
				}
			});
			if (bean.THUMBNAILSELECTION) {
				holder.checkbox.setVisibility(View.VISIBLE);
				holder.imageview.setAlpha(155);
			} else {
				holder.imageview.setAlpha(225);
			}
			holder.checkbox.setChecked(bean.THUMBNAILSELECTION);
			return convertView;
		}

		protected boolean validate() {
			if (MyApplication.currentAlbum.designerType == MyApplication.designerGrafiker) {
				if (selectedCount == 1000) {
					AlertDialog.Builder builder = new AlertDialog.Builder(
							ImagePickerActivity.this);

					builder.setMessage("Max. (1000) Zahl an Fotos erreicht !")
							.setCancelable(false)
							.setPositiveButton("OK",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog, int id) {
											// do things
										}
									});
					AlertDialog alert = builder.create();
					alert.show();
				} else
					return true;

			} else if (MyApplication.currentAlbum.designerType == MyApplication.designerEigene) {
				if (selectedCount == 400) {
					AlertDialog.Builder builder = new AlertDialog.Builder(
							ImagePickerActivity.this);
					builder.setMessage("Max. (400) Zahl an Fotos erreicht !")
							.setCancelable(false)
							.setPositiveButton("OK",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog, int id) {
											// do things
										}
									});
					AlertDialog alert = builder.create();
					alert.show();
				} else
					return true;
			}
			// TODO after setting grafiker etc need to set it false
			return true;
		}
	}

	class ViewHolder {
		ImageView imageview;
		CheckBox checkbox;
	}

	@Override
	public void onClick(View v) {
		if (v == selectAllOrNoneTextView) {
			if (!selectFlagEnabled) {
				MyApplication.currentAlbum.selectedImagesPaths = new ArrayList<String>();
				for (int i = 0; i < imageDetailsBeansArrayList.size(); i++) {
					imageDetailsBeansArrayList.get(i).THUMBNAILSELECTION = true;
					MyApplication.currentAlbum.selectedImagesPaths
							.add(imageDetailsBeansArrayList.get(i).IMAGE_PATH);
				}
				selectAllOrNoneTextView.setText(R.string.selectnone);
				selectedCount = imageDetailsBeansArrayList.size();
				if (selectedCount < 24)
					titleTextView.setText(selectedCount
							+ " Fotos gewählt | Mehr Fotos Wählen");
				else
					titleTextView.setText(selectedCount
							+ " Fotos gewählt | Ideal für "
							+ ((MyApplication) getApplication()).getIdealPages(
									MyApplication.currentAlbum.designerType,
									selectedCount) + " Seiten");
			} else {
				MyApplication.currentAlbum.selectedImagesPaths = new ArrayList<String>();
				for (int i = 0; i < imageDetailsBeansArrayList.size(); i++) {
					imageDetailsBeansArrayList.get(i).THUMBNAILSELECTION = false;
				}
				selectedCount = 0;
				selectAllOrNoneTextView.setText(R.string.selectall);
				titleTextView.setText(selectedCount
						+ " Fotos gewählt | Mehr Fotos Wählen");

			}
			selectFlagEnabled = !selectFlagEnabled;
			((BaseAdapter) imagegrid.getAdapter()).notifyDataSetChanged();
		} else if (v == nextTextView) {
			startActivity(new Intent(ImagePickerActivity.this,
					LoginActivity.class));
			// if (selectedCount < 26) {
			// AlertDialog.Builder builder = new AlertDialog.Builder(this);
			// builder.setTitle("Achtung");
			// builder.setMessage("Bitte wählen Sie mindestens 26 Fotos")
			// .setCancelable(false)
			// .setPositiveButton("OK",
			// new DialogInterface.OnClickListener() {
			// public void onClick(DialogInterface dialog,
			// int id) {
			// // do things
			// }
			// });
			// AlertDialog alert = builder.create();
			// alert.show();
			// } else if (MyApplication.currentAlbum.designerType ==
			// MyApplication.designerGrafiker) {
			// if (selectedCount > 1000) {
			// AlertDialog.Builder builder = new AlertDialog.Builder(this);
			// builder.setMessage("Max. (1000) Zahl an Fotos erreicht !")
			// .setCancelable(false)
			// .setPositiveButton("OK",
			// new DialogInterface.OnClickListener() {
			// public void onClick(
			// DialogInterface dialog, int id) {
			// // do things
			// }
			// });
			// AlertDialog alert = builder.create();
			// alert.show();
			// }
			// else
			// startActivity(new Intent(ImagePickerActivity.this,
			// AlbumOverViewActivity.class));
			//
			// } else if (MyApplication.currentAlbum.designerType ==
			// MyApplication.designerEigene) {
			// if (selectedCount > 400) {
			// AlertDialog.Builder builder = new AlertDialog.Builder(this);
			// builder.setMessage("Max. (400) Zahl an Fotos erreicht !")
			// .setCancelable(false)
			// .setPositiveButton("OK",
			// new DialogInterface.OnClickListener() {
			// public void onClick(
			// DialogInterface dialog, int id) {
			// // do things
			// }
			// });
			// AlertDialog alert = builder.create();
			// alert.show();
			// }
			// // TODO uncomment
			// // else
			// // startActivity(new Intent(ImagePickerActivity.this,
			// // AlbumOverViewActivity.class));
			// }
		}
	}
}