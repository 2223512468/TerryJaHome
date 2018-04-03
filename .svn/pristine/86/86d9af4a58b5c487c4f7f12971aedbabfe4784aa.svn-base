package com.jajahome.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 *引导页工具类
 */
public class GuildUtil {
	private static String SP_NAME="jajaHome_guild";
	private static String KEY="IS_FIRST";
	public static void setIsNotFirst(Context mContext){
		SharedPreferences sp=mContext.getApplicationContext().getSharedPreferences(SP_NAME,Context.MODE_PRIVATE);
		sp.edit().putBoolean(KEY,true).apply();
	}
	public static boolean getIsNotFirst(Context mContext) {
		SharedPreferences sp = mContext.getApplicationContext().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
		return sp.getBoolean(KEY, false);
	}
}
