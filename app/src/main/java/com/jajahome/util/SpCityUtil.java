package com.jajahome.util;

import android.content.Context;

/**
 * 类描述：保存选择的城市信息
 * 创建人：admin
 * 创建时间：2016/5/10 16:14
 * 修改人：admin
 * 修改时间：2016/5/10 16:14
 * 修改备注：
 */
public class SpCityUtil {

    private static String SP_NAME="jajahome_city";
    private static String HOUSE_CITY_NAME="house_city_name"; //城市名
    private static String HOUSE_CITY_CODE="house_city_code"; //编号

    private String cityName;
    private int cityCode;

    /**
     * 获取保存的城市编码
     */
    public static int getCityCode(Context context) {
        return context.getApplicationContext().
                getSharedPreferences(SP_NAME,Context.MODE_PRIVATE).getInt(HOUSE_CITY_CODE,-1);
    }
    /**
     * 保存城市编码
     */
    public static  void setCityCode(Context context,int cityCode) {
       context.getApplicationContext().
                getSharedPreferences(SP_NAME,Context.MODE_PRIVATE).edit().putInt(HOUSE_CITY_CODE,cityCode).apply();
    }
    /**
     * 获取保存的城市名称
     */
    public static  String getCityName(Context context) {
        return context.getApplicationContext().
                getSharedPreferences(SP_NAME,Context.MODE_PRIVATE).getString(HOUSE_CITY_NAME,"全部");
    }
    /**
     * 保存城市名称
     */
    public static  void setCityName(Context context,String cityName) {
        context.getApplicationContext().
                getSharedPreferences(SP_NAME,Context.MODE_PRIVATE).edit().putString(HOUSE_CITY_NAME,cityName).apply();
    }
}
