package com.zachy.ui;

import com.zachy.mobilesafe.R;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MySettingViewLayout extends RelativeLayout {

	private static final String NAMESPACE = "http://schemas.android.com/apk/res/com.zachy.mobilesafe";
	private View view;
	private ImageView toggleIv;
	private  boolean currentState;
	public MySettingViewLayout(Context context) {
		this(context,null);
	}

	public MySettingViewLayout(Context context, AttributeSet attrs) {
		this(context,attrs,0);
	}

	public MySettingViewLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView();
		String title = attrs.getAttributeValue(NAMESPACE, "title");
		boolean isToggle = attrs.getAttributeBooleanValue(NAMESPACE, "isToggle", true);
		TextView stv = (TextView) view.findViewById(R.id.setting_tv_text);
		ImageView toggle_iv = (ImageView) view.findViewById(R.id.setting_iv_toggle);
		//更具设置是否显示图片
		toggle_iv.setVisibility(isToggle?VISIBLE:GONE);
		stv.setText(title);
	}

	private void initView() {
		view = View.inflate(getContext(), R.layout.activity_home_setting, null);
		toggleIv = (ImageView) view.findViewById(R.id.setting_iv_toggle);
		addView(view);
	}
	
	public void setToggle(boolean toggleState){
		currentState = toggleState;
		if(toggleState){
			Log.e("MySettingViewLayout", "toggleState"+"为真");
			System.out.println(currentState);
			toggleIv.setImageResource(R.drawable.on);
		}else{
			Log.e("MySettingViewLayout", "toggleState"+"为假");
			System.out.println(currentState);
			toggleIv.setImageResource(R.drawable.off);
		}
	}
	
	public boolean getToggleState(){
		return currentState;
	}
	
	//
	public void changeToggle(){
//		if(currentState){
//			setToggle(!currentState);
//		}else{
//			setToggle(!currentState);
//			
//		}
		setToggle(!currentState);
	}
}
