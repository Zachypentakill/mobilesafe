package com.zachy.ui;

import android.content.Context;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.widget.TextView;

public class MyTextView extends TextView {
	public MyTextView(Context context) {
		this(context, null);
	}

	public MyTextView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public MyTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		setSingleLine();
		setMarqueeRepeatLimit(-1);
		setFocusableInTouchMode(true);
		setEllipsize(TruncateAt.MARQUEE);
	}

	@Override
	public boolean isFocused() {
		return true;
	}

}
