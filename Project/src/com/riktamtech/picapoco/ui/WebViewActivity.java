package com.riktamtech.picapoco.ui;

import java.io.InputStream;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.riktamtech.picapoco.R;

@SuppressLint("SetJavaScriptEnabled")
public class WebViewActivity extends Activity {
	String infoText;
	WebView webview;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		Bundle bun = getIntent().getExtras();
		setContentView(R.layout.activity_web_view);

		((TextView) findViewById(R.id.backTextView))
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						finish();
					}
				});
		webview = (WebView) findViewById(R.id.infoWebView);
		webview.setWebViewClient(new myWebClient());
		webview.getSettings().setJavaScriptEnabled(true);
		webview.setBackgroundColor(0x00000000);

		try {
			AssetManager assetManager = getAssets();

			InputStream in_s = assetManager.open(bun.getString("title"));
			byte[] b = new byte[in_s.available()];
			in_s.read(b);
			infoText = (new String(b));
		} catch (Exception e) {
			infoText = ("Error: can't load this page now.");
		}
		webview.loadDataWithBaseURL("file:///android_asset/", infoText,
				"text/html", "utf-8", "");
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		webview = (WebView) findViewById(R.id.infoWebView);
		webview.loadDataWithBaseURL("file:///android_asset/", infoText,
				"text/html", "utf-8", "");
		super.onResume();
	}

	public class myWebClient extends WebViewClient {
		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {

			super.onPageStarted(view, url, favicon);
		}

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			// TODO check url pattern
			if (url.startsWith("mailto:")) {
				finish();
				return true;
			}

			else if (url.startsWith("tel:")) {
				Intent intent = new Intent(Intent.ACTION_CALL,
						Uri.parse("tel:07022483"));
				startActivity(intent);
				view.reload();
				return true;
			} else if (url.startsWith("finish")) {
				finish();
				return true;
			} else {
				view.loadUrl(url);
				return true;
			}
		}
	}
}
