package com.zachy.tools;

import android.content.Context;
import android.content.SharedPreferences;
import android.test.MoreAsserts;

public class SharedPreferenceTool {
	// 测试下 不加context
	private static SharedPreferences sp;

	public static void saveSharedPreference(Context context, String key, boolean value) {
		if(sp==null){sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);}
		sp.edit().putBoolean(key, value).commit();
	}
	
	public static boolean getSharedPreference(Context context, String key, boolean defValue) {
		if(sp==null){sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);}
		return sp.getBoolean(key, defValue);
		
	}
}
