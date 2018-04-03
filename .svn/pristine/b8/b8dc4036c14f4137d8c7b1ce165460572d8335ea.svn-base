package com.jajahome.util;

import android.text.TextUtils;

import java.util.regex.Pattern;

/**
 * Created by admin on 2016/5/24.
 */
public class CommonUtils {
    /**
     * 验证手机格式
     */
    public static boolean isMobileNO(String mobiles) {
        String telRegex = "[1]\\d{10}";//"[1]"代表第1位为数字1，"\\d{10}"代表后面是可以是0～9的数字，有10位。
        return !TextUtils.isEmpty(mobiles) && mobiles.matches(telRegex);
    }

    //验证联系电话
    public static boolean checkPhone(String phone){
        if(phone.matches("\\d{4}-\\d{8}|\\d{4}-\\d{7}|\\d(3)-\\d(8)")){
            return true;
        }else if(phone.matches("^[1][3,5]+\\d{9}")){
            return true;
        }else{
            return false;
        }
    }

    //验证邮政编码
    public static boolean checkPost(String post){
        if(post.matches("[1-9]\\d{5}(?!\\d)")){
            return true;
        }else{
            return false;
        }
    }

    //验证邮箱
    public static boolean checkEmail(String email){
        if(email.matches("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*")){
            return true;
        }else{
            return false;
        }
    }


    /**
     * 校验身份证
     *
     * @param idCard
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isIDCard(String idCard) {
        String REGEX_ID_CARD = "(^\\d{18}$)|(^\\d{15}$)";
        return Pattern.matches(REGEX_ID_CARD, idCard);
    }
}
