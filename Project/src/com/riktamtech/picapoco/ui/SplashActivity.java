package com.riktamtech.picapoco.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.riktamtech.picapoco.R;
import com.riktamtech.picapoco.application.MyApplication;

public class SplashActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_screen);
		DisplayImageOptions options = new DisplayImageOptions.Builder()
				.bitmapConfig(Config.RGB_565).cacheInMemory()
				.imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2).build();
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				SplashActivity.this)
				.discCache(new UnlimitedDiscCache(MyApplication.appCacheDir))
				.memoryCache(new WeakMemoryCache())
				.defaultDisplayImageOptions(options).threadPoolSize(5).build();

		ImageLoader.getInstance().init(config);
		new CountDownTimer(5000, 1000) {

			@Override
			public void onTick(long millisUntilFinished) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				startActivity(new Intent(SplashActivity.this,
						HomeScreenActivity.class));
				finish();

			}
		}.start();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.splash, menu);
		return true;
	}

}
