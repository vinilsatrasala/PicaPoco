package com.riktamtech.picapoco.ui;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.riktamtech.picapoco.R;
import com.riktamtech.picapoco.adapters.AlbumCatalogInfoAdapter;

public class AlbumCatalogInfoActivity extends Activity {

	private ViewPager imageViewPager;
	private Spinner catalogSpinner;
	private ArrayList<ArrayList<Integer>> imageArrayList = new ArrayList<ArrayList<Integer>>();
	private ArrayList<Integer> albumArray = new ArrayList<Integer>();
	private ArrayList<Integer> classicArray = new ArrayList<Integer>();
	private ArrayList<Integer> modernArray = new ArrayList<Integer>();
	private ArrayList<Integer> freshArray = new ArrayList<Integer>();
	private ArrayList<Integer> purArray = new ArrayList<Integer>();
	private ArrayList<Integer> vintageArray = new ArrayList<Integer>();
	private String[] albumSpinnerItems = { "Album", "Klassisch", "Modern",
			"Frisch", "Foto Pur", "Vintage" };
	private AlbumCatalogInfoAdapter albumCatalogInfoAdapter;
	private TextView backTextView;
	private TextView infoTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_albumcatalog);
		infoTextView = (TextView) findViewById(R.id.albumCatalogActivity_detail);
		imageViewPager = (ViewPager) findViewById(R.id.albumCatalogActivity_ViewPager);
		catalogSpinner = (Spinner) findViewById(R.id.albumCatalogActivity_spinner);
		backTextView = (TextView) findViewById(R.id.backTextView);
		backTextView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		prepareArrays();
		albumCatalogInfoAdapter = new AlbumCatalogInfoAdapter(
				AlbumCatalogInfoActivity.this, imageArrayList.get(0));
		imageViewPager.setAdapter(albumCatalogInfoAdapter);
		catalogSpinner.setAdapter(new ArrayAdapter<String>(
				AlbumCatalogInfoActivity.this,
				R.layout.album_type_spinner_item, albumSpinnerItems));
		if (getIntent().hasExtra("item")) {
			switch (getIntent().getExtras().getInt("item")) {
			case 1:
				catalogSpinner.setSelection(0);
				break;
			case 2:
				catalogSpinner.setSelection(1);
				break;
			case 3:
				catalogSpinner.setSelection(2);
				break;
			case 4:
				catalogSpinner.setSelection(3);
				break;
			case 5:
				catalogSpinner.setSelection(4);
				break;
			case 6:
				catalogSpinner.setSelection(5);
				break;

			default:
				break;
			}

		}
		catalogSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int pos,
					long arg3) {
				// TODO Auto-generated method stub
				TextView textView = (TextView) arg1;
				textView.setPadding(0, 0, 0, 0);
				textView.setTextSize(12);
				textView.setTypeface(null, Typeface.BOLD);

				albumCatalogInfoAdapter = new AlbumCatalogInfoAdapter(
						AlbumCatalogInfoActivity.this, imageArrayList.get(pos));
				imageViewPager.setAdapter(albumCatalogInfoAdapter);
				switch (pos) {
				case 0:
					infoTextView.setText(R.string.albumCatalogActivity_detail);

					break;
				case 1:
					infoTextView
							.setText(R.string.klassichCatalogActivity_detail);

					break;
				case 2:
					infoTextView.setText(R.string.modernCatalogActivity_detail);

					break;
				case 3:
					infoTextView.setText(R.string.frischCatalogActivity_detail);

					break;
				case 4:
					infoTextView
							.setText(R.string.fotopurCatalogActivity_detail);

					break;
				case 5:
					infoTextView
							.setText(R.string.vintageCatalogActivity_detail);

					break;

				default:
					break;
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

	}

	/**
	 * Adding resource Id's of Images to respective album Arrays
	 */
	private void prepareArrays() {
		// TODO Auto-generated method stub
		albumArray.add(R.drawable.album_1);
		albumArray.add(R.drawable.album_2);
		albumArray.add(R.drawable.album_3);
		albumArray.add(R.drawable.album_4);
		albumArray.add(R.drawable.album_5);
		imageArrayList.add(albumArray);

		classicArray.add(R.drawable.classic_1);
		classicArray.add(R.drawable.classic_2);
		classicArray.add(R.drawable.classic_3);
		classicArray.add(R.drawable.classic_4);
		classicArray.add(R.drawable.classic_5);
		imageArrayList.add(classicArray);

		modernArray.add(R.drawable.modern_1);
		modernArray.add(R.drawable.modern_2);
		modernArray.add(R.drawable.modern_3);
		modernArray.add(R.drawable.modern_4);
		modernArray.add(R.drawable.modern_5);
		imageArrayList.add(modernArray);

		freshArray.add(R.drawable.fresh_1);
		freshArray.add(R.drawable.fresh_2);
		freshArray.add(R.drawable.fresh_3);
		freshArray.add(R.drawable.fresh_4);
		freshArray.add(R.drawable.fresh_5);
		imageArrayList.add(freshArray);

		purArray.add(R.drawable.pur_1);
		purArray.add(R.drawable.pur_2);
		purArray.add(R.drawable.pur_3);
		purArray.add(R.drawable.pur_4);
		purArray.add(R.drawable.pur_5);
		imageArrayList.add(purArray);

		vintageArray.add(R.drawable.vintage_1);
		vintageArray.add(R.drawable.vintage_2);
		vintageArray.add(R.drawable.vintage_3);
		vintageArray.add(R.drawable.vintage_4);
		vintageArray.add(R.drawable.vintage_5);
		imageArrayList.add(vintageArray);

	}
}
