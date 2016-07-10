package com.zachy.mobilesafe;

import android.os.Bundle;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class HomePage extends Activity {

	private ImageView mHomeLogo;
	private final String[] TITLES = new String[] { "手机防盗", "骚扰拦截", "软件管家",
			"进程管理", "流量统计", "手机杀毒", "缓存清理", "常用工具" };
	private final String[] DESCS = new String[] { "远程定位手机", "全面拦截骚扰", "管理您的软件",
			"管理运行进程", "流量一目了然", "病毒无处藏身", "系统快如火箭", "工具大全" };
	private final int[] ICONS = new int[] { R.drawable.sjfd, R.drawable.srlj,
			R.drawable.rjgj, R.drawable.jcgl, R.drawable.lltj, R.drawable.sjsd,
			R.drawable.hcql, R.drawable.cygj };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		initView();
	}

	private void initView() {
		mHomeLogo = (ImageView) findViewById(R.id.home_iv_logo);
		rotateLogo();
		initGridView();
		

	}

	private void initGridView() {
		GridView home_gview = (GridView) findViewById(R.id.home_gv_gridview);
		home_gview.setAdapter(new MyAdapter());

	}

	class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return ICONS.length;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = View.inflate(getApplicationContext(),
					R.layout.home_gridview_item, null);
			ImageView iv = (ImageView) v.findViewById(R.id.item_iv_icon);
			TextView tv = (TextView) v.findViewById(R.id.item_tv_title);
			TextView desc = (TextView) v.findViewById(R.id.item_tv_desc);
			iv.setImageResource(ICONS[position]);
			tv.setText(TITLES[position]);
			desc.setText(DESCS[position]);

			return v;
		}
	}

	private void rotateLogo() {
		ObjectAnimator oj = ObjectAnimator.ofFloat(mHomeLogo, "RotationY", 0,
				360f);
		oj.setDuration(200);
		oj.setRepeatCount(ObjectAnimator.INFINITE);
		oj.setRepeatMode(ObjectAnimator.RESTART);
		oj.start();

	}
	
	public void enterSetting(View v){
		Intent intent = new Intent(this,SettingActivity.class);
		startActivity(intent);
	}
	
}
