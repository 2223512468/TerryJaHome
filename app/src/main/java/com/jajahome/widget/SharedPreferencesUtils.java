package com.jajahome.widget;

import android.content.Context;
import android.content.SharedPreferences;

import com.jajahome.constant.Constant;

/**
 * Created by ${Terry} on 2016/11/16.
 */
public class SharedPreferencesUtils {

    private Context mContext;
    private static String ROOMKEY = "ROOMKEY";
    private static String ROOMVALUE = "ROOMVALUE";
    private static String ROOMTRUE = "ROOMTRUE";
    private static String ROOMNAME = "ROOMNAME";
    private static String WHOLEROOMKEY = "WHOLEROOMKEY";
    private static String WHOLEROOMVALUE = "WHOLEROOMVALUE";
    private static String WHOLEROOMTRUE = "WHOLEROOMTRUE";
    private static String WHOLEROOMNAME = "WHOLEROOMNAME";

    public SharedPreferencesUtils(Context mContext) {
        this.mContext = mContext;

    }

    public void saveUserCity(String cityName) {
        SharedPreferences preferences = mContext.getSharedPreferences(Constant.LOCATION, mContext.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear().commit();
        editor.putString(Constant.CITYNAME, cityName);
        editor.commit();

    }

    public String getUserCity() {
        SharedPreferences preferences = mContext.getSharedPreferences(Constant.LOCATION, mContext.MODE_PRIVATE);
        return preferences.getString(Constant.CITYNAME, "-1");
    }

    /*保存首页传来的空间值*/

    public static void saveRoomKey(Context mContext, String value) {
        SharedPreferences preferences = mContext.getApplicationContext().getSharedPreferences(ROOMKEY, mContext.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear().commit();
        editor.putString(ROOMVALUE, value);
        editor.commit();
    }

    public static String getRoomValue(Context mContext) {
        SharedPreferences preferences = mContext.getApplicationContext().getSharedPreferences(ROOMKEY, mContext.MODE_PRIVATE);
        return preferences.getString(ROOMVALUE, "全部");
    }

    public static boolean getSetFrag(Context mContext) {
        SharedPreferences preferences = mContext.getApplicationContext().getSharedPreferences(ROOMNAME, mContext.MODE_PRIVATE);
        return preferences.getBoolean(ROOMTRUE, false);
    }

    public static void saveSetFrag(Context mContext, Boolean flag) {
        SharedPreferences preferences = mContext.getApplicationContext().getSharedPreferences(ROOMNAME, mContext.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear().commit();
        editor.putBoolean(ROOMTRUE, flag);
        editor.commit();
    }

    /*保存首页传来的完整家居值*/

    public static void saveWholeRoomKey(Context mContext, String value) {
        SharedPreferences preferences = mContext.getApplicationContext().getSharedPreferences(WHOLEROOMKEY, mContext.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear().commit();
        editor.putString(ROOMVALUE, value);
        editor.commit();
    }

    public static String getWholeRoomValue(Context mContext) {
        SharedPreferences preferences = mContext.getApplicationContext().getSharedPreferences(WHOLEROOMKEY, mContext.MODE_PRIVATE);
        return preferences.getString(ROOMVALUE, "全部");
    }

    public static boolean getWholeSetFrag(Context mContext) {
        SharedPreferences preferences = mContext.getApplicationContext().getSharedPreferences(WHOLEROOMNAME, mContext.MODE_PRIVATE);
        return preferences.getBoolean(ROOMTRUE, false);
    }

    public static void saveWholeSetFrag(Context mContext, Boolean flag) {
        SharedPreferences preferences = mContext.getApplicationContext().getSharedPreferences(WHOLEROOMNAME, mContext.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear().commit();
        editor.putBoolean(ROOMTRUE, flag);
        editor.commit();
    }

}
