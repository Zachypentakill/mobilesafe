package com.zachy.mobilesafe;

import java.io.File;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.zachy.tools.PackageTools;
import com.zachy.tools.SharePreferenceKey;
import com.zachy.tools.SharedPreferenceTool;

public class SplashActivity extends Activity {

	private static final String CONNECTURL = "http://192.168.1.119/safe.html";
	private String verApkURL;
	private String verMsg;
	private int verCode;
	private ProgressDialog pd;
	protected final static String TAG = "SplashActivity";
	private String SAVEURL;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
//		Thread t = new Thread(){@Override
//		public void run() {
//			super.run();
//			try {
//				String hostAddress = InetAddress.getLocalHost().getHostAddress();
//				System.out.println("--------------"hostAddress);
//			} catch (UnknownHostException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			
//			
//		}};
//		t.start();
		initView();

	}

	// 显示更新警告框
	private void showAlertDialog() {
		AlertDialog.Builder builder = new Builder(this);
		builder.setTitle("有新版本")
				.setIcon(R.drawable.ic_launcher)
				.setOnCancelListener(new OnCancelListener() {

					@Override
					public void onCancel(DialogInterface dialog) {
						dialog.dismiss();
						enterHome();
					}
				})
				.setMessage(verMsg)
				.setNegativeButton("下次再说",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
								enterHome();
							}
						})
				.setPositiveButton("确定更新",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								updateAPK();
							}
						}).show();
		;
		;
	}

	protected void updateAPK() {
		// 开始更新
		// 1.确认sd卡是否加载成功
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			showProgressDialog();
			downloadAPK();

		} else {
			Toast.makeText(getApplicationContext(), "SD卡未挂载", 0).show();
			pd.dismiss();
		}
		;
	}

	private void showProgressDialog() {
		pd = new ProgressDialog(SplashActivity.this);
		pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		pd.setCancelable(false);
		pd.show();

	}

	// 开始下载apk
	private void downloadAPK() {
		HttpUtils conn = new HttpUtils(8000);
		SAVEURL = Environment.getExternalStorageDirectory().toString()
				+ "/safe.apk";
		Log.d(TAG, SAVEURL);
		conn.download(verApkURL, SAVEURL, new RequestCallBack() {
			// 下载成功时调用
			@Override
			public void onSuccess(ResponseInfo responseInfo) {
				Log.d(TAG, "onSuccess");
				pd.dismiss();
				installApk();
			}

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				Log.d(TAG, "下载失败");// TODO Auto-generated method stub
				pd.dismiss();
				enterHome();
			}

			@Override
			public void onLoading(long total, long current, boolean isUploading) {
				super.onLoading(total, current, isUploading);
				Log.d(TAG, "onLoading");
				pd.setMax((int) total);
				pd.setProgress((int) current);
			}

		});

	}

	protected void installApk() {
		Intent intent = new Intent();
		intent.setAction("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.setDataAndType(Uri.fromFile(new File(SAVEURL)),
				"application/vnd.android.package-archive");
		startActivityForResult(intent, 0);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		enterHome();
	}

	// 更新
	public void update() {
		HttpUtils utils = new HttpUtils(8000);
		utils.send(HttpMethod.GET, CONNECTURL, null,
				new RequestCallBack<String>() {

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						// 连接成功时
						String result = responseInfo.result;
						Log.e(TAG, "连接成功");
						processJason(result);

					}

					@Override
					public void onFailure(HttpException arg0, String arg1) {
						// 连接失败时
						Log.e(TAG, "连接失败");
						enterHome();
					}

				});
	}

	protected void processJason(String result) {
		try {
			// 注意传入result
			JSONObject jasonObject = new JSONObject(result);
			verApkURL = jasonObject.getString("apkurl");
			verMsg = jasonObject.getString("msg");
			verCode = jasonObject.getInt("code");
			Log.i(TAG, verMsg);
			if (PackageTools.getVersionCode(this) == verCode) {
				enterHome();
			} else {
				showAlertDialog();
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}

	}

	// 初始化试图
	public void initView() {
		TextView splash_tv_version = (TextView) findViewById(R.id.splash_tv_version);
		splash_tv_version.setText("版本：" + PackageTools.getVersionName(this));
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {

				if (SharedPreferenceTool.getSharedPreference(
						SplashActivity.this, SharePreferenceKey.UPDATETOGGLE,
						true)) {

					update();
				} else {

					enterHome();
				}
			}
		}, 2000);
	}

	protected void enterHome() {
		Intent intent = new Intent(this, HomePage.class);
		startActivity(intent);
		finish();

	}
}
