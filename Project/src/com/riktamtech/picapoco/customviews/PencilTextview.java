package com.riktamtech.picapoco.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.TextView;

import com.riktamtech.picapoco.R;

public class PencilTextview extends TextView {
	private int mPencilColor;
	private String mPosition;

	public PencilTextview(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
				R.styleable.PencilTextview, 0, 0);

		try {
			mPencilColor = a.getInteger(R.styleable.PencilTextview_Color, 0);
			mPosition = a.getString(R.styleable.PencilTextview_Position);
		} finally {
			a.recycle();
		}
	}

	public int getPencilColor() {

		return mPencilColor;
	}

	public String getPencilPosition() {

		return mPosition;
	}

}
