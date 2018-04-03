package com.jajahome.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.jajahome.model.SearchHistoryModel;

import java.util.ArrayList;
import java.util.List;

/**
 * 存放登录过的账号
 */

public class SpSearchHistoryUtil {
    private static final String SP_NAME = "SP_SEARCH";
    //是否自动登录
    private static final String ACCOUNT = "KEYS";
    private static final int MAX_NUMS=5;
    public static void saveHistory(Context context,String type, String userName) {
        List<String> list = getAllHistory(context,type);
        if (list == null || list.size() == 0) {
            list = new ArrayList<>();
            list.add(userName);
        } else {
            int size = list.size();
            int index=-1;
            for (int i = 0; i < size; i++) {
                if (userName.equals(list.get(i))) {
                  index=i;
                }
            }
            if(index!=-1){
                list.remove(index);
            }
            list.add(0,userName);
        }
        if(list.size()>MAX_NUMS){
            list=list.subList(0,MAX_NUMS);
        }
        SearchHistoryModel bean = new SearchHistoryModel(list);
        Gson gson = new Gson();
        context.getSharedPreferences(SP_NAME+type, Context.MODE_PRIVATE).edit().putString(ACCOUNT, gson.toJson(bean)).apply();
    }

    public static List<String> getAllHistory(Context context,String type) {
        SharedPreferences sp = context.getSharedPreferences(SP_NAME+type, Context.MODE_PRIVATE);
        String strAccount = sp.getString(ACCOUNT, "");
        if (StringUtil.isEmpty(strAccount)) {
            return null;
        } else {
            Gson gson = new Gson();
            SearchHistoryModel accountBean = gson.fromJson(strAccount, SearchHistoryModel.class);
            return accountBean.getKeys();
        }
    }

    private static void setValue(Context context, String accounts) {

    }

    private static SharedPreferences.Editor getEditor(Context context) {
        return context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE).edit();
    }
}
