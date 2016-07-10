package com.zachy.mobilesafe;

import com.zachy.tools.SharePreferenceKey;
import com.zachy.tools.SharedPreferenceTool;
import com.zachy.ui.MySettingViewLayout;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class SettingActivity extends Activity implements OnClickListener {

	private MySettingViewLayout mUpdate;
	private MySettingViewLayout mAntiSpamMsg;
	private MySettingViewLayout belonging;
	private MySettingViewLayout belongingStyle;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);

		initView();

	}

	// 初始化视图
	private void initView() {
		mUpdate = (MySettingViewLayout) findViewById(R.id.setting_msv_update);
		mAntiSpamMsg = (MySettingViewLayout) findViewById(R.id.setting_msv_antiSpamMsg);
		belonging = (MySettingViewLayout) findViewById(R.id.setting_msv_belonging);
		belongingStyle = (MySettingViewLayout) findViewById(R.id.setting_msv_belongingStyle);
		boolean toggle =SharedPreferenceTool.getSharedPreference(this, SharePreferenceKey.UPDATETOGGLE, true);
		boolean toggle2 =SharedPreferenceTool.getSharedPreference(this, SharePreferenceKey.ANTISPAMTOGGLE, true);
		boolean toggle3 =SharedPreferenceTool.getSharedPreference(this, SharePreferenceKey.ANTISPAMTOGGLE, true);
		mUpdate.setToggle(toggle);
		mAntiSpamMsg.setToggle(toggle2);
		belonging.setToggle(toggle3);
		mUpdate.setOnClickListener(this);
		mAntiSpamMsg.setOnClickListener(this);
		belonging.setOnClickListener(this);
		// 更新设置toggle的状态
		//updateToggle();
	}

//	public void updateSetting(View v){
//		mUpdate.changeToggle();
//		SharedPreferenceTool.saveSharedPreference(this, SharePreferenceKey.UPDATETOGGLE, mUpdate.getToggleState());
//		System.out.println("保存的数据为："+mUpdate.getToggleState());
//	}
//	
//	public void antiSpamSetting(View v){
//		mAntiSpamMsg.changeToggle();
//		SharedPreferenceTool.saveSharedPreference(this, SharePreferenceKey.ANTISPAMTOGGLE, mAntiSpamMsg.getToggleState());
//	}
//	private void updateToggle() {
//		//初始化切换按钮的状态
//		mUpdate.setToggle(true);
//		mUpdate.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				mUpdate.changeToggle();
//			}
//		});
//	}

	@Override
	public void onClick(View v) {
		if(v == mUpdate){
			mUpdate.changeToggle();
			SharedPreferenceTool.saveSharedPreference(this, SharePreferenceKey.UPDATETOGGLE, mUpdate.getToggleState());
		}else if(v == mAntiSpamMsg){
			mAntiSpamMsg.changeToggle();
			SharedPreferenceTool.saveSharedPreference(this, SharePreferenceKey.ANTISPAMTOGGLE, mAntiSpamMsg.getToggleState());
		}else if(v == belonging){
			belonging.changeToggle();
			SharedPreferenceTool.saveSharedPreference(this, SharePreferenceKey.SHOWBELONGINGTOGGLE, belonging.getToggleState());
		}
		
	}

	
}
