package com.jajahome.util;

import android.widget.EditText;
import android.widget.TextView;

/**
 * Author:    ds
 * Version    V1.0
 * Date:      16-5-23 11:47
 * Description: 字符串帮助类
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16-5-23      ds
 * Why & What is modified:
 */
public class StringHelper {
    //根据传入的字符串
    public static String getStr(String str) {
        return isEmpty(str) ? "" : str;
    }

    //根据得到的edit获取对应的值
    public static String getEditTextContent(EditText edt) {
        return edt.getText().toString().trim();
    }

    //判断字符串为空判断
    public static boolean isEmpty(String str) {
        return str == null || str.equals("null") || str.trim().equals("");
    }

    //判断et为空判断
    public static boolean isEditTextEmpty(EditText edt) {
        return isEmpty(getEditTextContent(edt));
    }

    //手机号码的判断
    public static boolean isPhoneEditTextEmpty(EditText edt) {
        return isEmpty(getEditTextContent(edt)) || getEditTextContent(edt).length() != 11;
    }

    //非空判断
    public static boolean notEmpty(String str) {
        return !isEmpty(str);
    }

    //非空判断
    public static String getTextViewContent(TextView tv) {
        return tv.getText().toString().trim();
    }

}
