package com.riktamtech.picapoco.application;

import java.io.File;
import java.util.ArrayList;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.riktamtech.picapoco.R;
import com.riktamtech.picapoco.beans.AlbumDetailsBean;
import com.riktamtech.picapoco.beans.ImageDetailsBean;
import com.riktamtech.picapoco.ui.AlbumCatalogInfoActivity;
import com.riktamtech.picapoco.ui.HomeScreenActivity;
import com.riktamtech.picapoco.ui.WebViewActivity;

public class MyApplication extends Application {

	public static File appCacheDir;
	public static ArrayList<ImageDetailsBean> imageDetailsArrayList;
	public static ImageLoader imageLoader;
	public static DisplayImageOptions options;

	public static int FormatSize20X20 = 1, FormatSize30X30 = 4,
			FormatSizea2 = 2, FormatSizea3 = 5, FormatSizea4 = 3;
	public static int AlbumTypeAlbum = 1, AlbumTypeKlassisch = 2,
			AlbumTypeModern = 3, AlbumTypeFrisch = 4, AlbumTypeFotoPur = 5,
			AlbumTypeVintage = 6;
	public static int designerEigene = 1, designerGrafiker = 3;
	public static AlbumDetailsBean currentAlbum;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		imageDetailsArrayList = new ArrayList<ImageDetailsBean>();
		imageLoader = ImageLoader.getInstance();
		imageLoader.init(ImageLoaderConfiguration.createDefault(this));
		options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.preloader).cacheInMemory()
				.cacheOnDisc().bitmapConfig(Bitmap.Config.RGB_565).build();
		currentAlbum = new AlbumDetailsBean();

		File sdcardDirectory = new File(
				Environment.getExternalStorageDirectory() + "/Picapoco/Albums");
		File thumbsDirectory = new File(
				Environment.getExternalStorageDirectory()
						+ "/Picapoco/Albums/.nomedia");
		File coverImagesDirectory = new File(
				Environment.getExternalStorageDirectory()
						+ "/Picapoco/CoverImages");
		File coverImagesDirectoryNoMedia = new File(
				Environment.getExternalStorageDirectory()
						+ "/Picapoco/CoverImages/.nomedia");
		if (!coverImagesDirectory.exists())
			coverImagesDirectory.mkdirs();
		if (!coverImagesDirectoryNoMedia.exists())
			coverImagesDirectoryNoMedia.mkdirs();
		if (!sdcardDirectory.exists())
			sdcardDirectory.mkdirs();
		if (!thumbsDirectory.exists())
			thumbsDirectory.mkdirs();
		System.out.println("Created PicturePlix Directory");
		appCacheDir = sdcardDirectory;

	}

	public Bitmap getThumbNail(long originId) {
		return MediaStore.Images.Thumbnails.getThumbnail(
				getApplicationContext().getContentResolver(), originId,
				MediaStore.Images.Thumbnails.MICRO_KIND, null);

	}

	public interface ServiceStatusListener {
		public void onSuccess(Object object);

		public void onFailure(Exception exception);
	}

	/**
	 * to get ideal pages
	 * 
	 * @param designBy
	 * @param imageCount
	 * @return
	 */
	public int getIdealPages(int designBy, int imageCount) {
		int imagePages = 0;

		if (designBy == designerEigene) {
			imagePages = (int) Math
					.round((((imageCount - 2) / (8 * 2.25)) - 0.5)) * 8;
		} else if (designBy == designerGrafiker) {
			imagePages = (int) Math
					.round((((imageCount - 2) / (8 * 4.5)) - 0.5)) * 8;
		}
		if (imagePages < 24)
			imagePages = 24;
		else if (imagePages > 72)
			imagePages = 72;
		return imagePages;

	}

	/**
	 * To get minimum number of pages
	 * 
	 * @param designBy
	 * @param imageCount
	 * @return
	 */
	public int getMinimumPages(int designBy, int imageCount) {
		int imagePages;
		if (designBy == designerEigene) {
			imagePages = (int) Math
					.round((((imageCount - 2) / (8 * 4.5)) - 0.5)) * 8;
		} else {
			imagePages = (int) Math.round((((imageCount - 2) / (8 * 8)) - 0.5)) * 8;
		}
		if (imagePages < 24)
			imagePages = 24;
		else if (imagePages > 72)
			imagePages = 72;
		return imagePages;
	}

	/**
	 * To get maximum number of pages
	 * 
	 * @param designBy
	 * @param imageCount
	 * @return
	 */
	public int getMaximumPages(int designBy, int imageCount) {
		int imagePages;
		if (designBy == designerEigene) {
			imagePages = (int) Math.round((((imageCount - 2) / (8 * 1)) - 0.5)) * 8;
		} else {
			imagePages = (int) Math
					.round((((imageCount - 2) / (8 * 2.25)) - 0.5)) * 8;
		}
		if (imagePages < 24)
			imagePages = 24;
		else if (imagePages > 72)
			imagePages = 72;
		return imagePages;
	}

	public void initializeBottomMenu(final Context context, ViewGroup viewGroup) {
		final ImageView homeImageView, infoImageView, pricingImageView, albumiImageView;
		homeImageView = (ImageView) viewGroup.findViewById(R.id.homeImage);
		infoImageView = (ImageView) viewGroup.findViewById(R.id.infoImage);
		pricingImageView = (ImageView) viewGroup
				.findViewById(R.id.pricingImage);
		albumiImageView = (ImageView) viewGroup.findViewById(R.id.albumImage);
		OnClickListener bottomMenuListener = new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (v == homeImageView) {
					Intent intent = new Intent(context,
							HomeScreenActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(intent);
				} else if (v == infoImageView) {
					Intent intent = new Intent(context, WebViewActivity.class);
					intent.putExtra("title", "info.html");
					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(intent);
				} else if (v == pricingImageView) {
					Intent intent = new Intent(context, WebViewActivity.class);
					intent.putExtra("title", "pricing.html");
					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(intent);
				} else if (v == albumiImageView) {
					Intent intent = new Intent(context,
							AlbumCatalogInfoActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(intent);
				}
			}
		};
		homeImageView.setOnClickListener(bottomMenuListener);
		infoImageView.setOnClickListener(bottomMenuListener);
		pricingImageView.setOnClickListener(bottomMenuListener);
		albumiImageView.setOnClickListener(bottomMenuListener);

	}

}
