package com.jajahome.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 类描述：
 * 创建人：lhz
 * 创建时间：2016/6/7 15:03
 * 修改时间：2016/6/7 15:03
 * 修改备注：
 */
public class DateUtil {

    /**
     * 使用用户格式提取字符串日期
     *
     * @param strDate 日期字符串
     * @param pattern 日期格式
     * @return
     */

    public static Date parse(String strDate, String pattern) {

            if (StringUtil.isEmpty(strDate)) {
            return null;
        }
        try {
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            return df.parse(strDate);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * 使用用户格式格式化日期
     *
     * @param date    日期
     * @param pattern 日期格式
     * @return
     */

    public static String format(Date date, String pattern) {
        String returnValue = "";
        if (date != null) {
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            returnValue = df.format(date);
        }
        return (returnValue);
    }
}


