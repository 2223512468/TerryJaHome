package com.jajahome.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by liuhaizhu on 2017/4/27.
 */

public class TimeUtil {
    private final static String TIME_UNIT_DAY = "yyyy年MM月dd日";
    private final static String TIME_UNIT_DAY_SS = "yyyy年MM月dd日 HH:mm:ss";
    private final static String TIME_UNIT_COMMENT = "MM月dd日";
    private final static String NOTICE_TIME = "yyyy-MM-dd";

    public static String getTime(long time) {
        Date date = new Date(time * 1000);
        return new SimpleDateFormat(TIME_UNIT_DAY).format(date);
    }

    public static String getCommentTime(long time) {
        Date date = new Date(time * 1000);
        return new SimpleDateFormat(TIME_UNIT_COMMENT).format(date);
    }

    public static String getTimeDesc(long time) {
        Date date = new Date(time * 1000);
        return new SimpleDateFormat(TIME_UNIT_DAY_SS).format(date);
    }

    public static String getTime(String user_time) {
        String re_time = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date d;
        try {
            d = sdf.parse(user_time);
            long l = d.getTime();
            String str = String.valueOf(l);
            // re_time = str.substring(0, 10);
            re_time = str;
        } catch (ParseException e) {
            // TODO Auto-generated catch block e.printStackTrace();
        }
        return re_time;
    }
}
