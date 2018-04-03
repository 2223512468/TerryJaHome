package com.jajahome.util.cacheutils;

import android.os.Environment;

import com.google.gson.Gson;
import com.jajahome.feature.GuildAct;
import com.jajahome.feature.diy.fragment.DiyFrag;
import com.jajahome.feature.home.fragment.HomeFrag;
import com.jajahome.feature.item.fragment.Itemfrag;
import com.jajahome.feature.set.fragment.SetFrag;
import com.jajahome.feature.show.fragment.ShowDetailFrag;
import com.jajahome.model.DiyListModel;
import com.jajahome.model.HomeBannerModel;
import com.jajahome.model.ItemListModel;
import com.jajahome.model.LaunchModel;
import com.jajahome.model.RecommendModel;
import com.jajahome.model.SetListModel;
import com.jajahome.model.ShowListModel;
import com.jajahome.util.AESUtils;
import com.jajahome.util.StringUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;

/**
 * Created by Administrator on 2017/7/6.
 */
public class CacheUtils {

    public static void writeData(String result, String dirName) {
        if (StringUtil.isEmpty(result)) {
            return;
        }
        HomeBannerModel homeBannerModel = null;
        RecommendModel recommendModel = null;
        RecommendModel recommendSetModel = null;
        SetListModel setListModel = null;
        ItemListModel itemListModel = null;
        ShowListModel showListModel = null;
        DiyListModel diyListModel = null;
        LaunchModel launchModel = null;
        File fileDir = null;
        Gson gson = new Gson();
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            if (dirName.equals(HomeFrag.homeCacheB)) {
                homeBannerModel = gson.fromJson(result, HomeBannerModel.class);
            } else if (dirName.equals(HomeFrag.homeCacheR)) {
                recommendModel = gson.fromJson(result, RecommendModel.class);
            } else if (dirName.equals(SetFrag.dirName)) {
                setListModel = gson.fromJson(result, SetListModel.class);
            } else if (dirName.equals(Itemfrag.dirName)) {
                itemListModel = gson.fromJson(result, ItemListModel.class);
            } else if (dirName.equals(ShowDetailFrag.dirName)) {
                showListModel = gson.fromJson(result, ShowListModel.class);
            } else if (dirName.equals(DiyFrag.dirName)) {
                diyListModel = gson.fromJson(result, DiyListModel.class);
            } else if (dirName.equals(HomeFrag.homeCacheSI)) {
                recommendSetModel = gson.fromJson(result, RecommendModel.class);
            } else if (dirName.equals(GuildAct.dirName)) {
                launchModel = gson.fromJson(result, LaunchModel.class);
            }

            String sdCardDir = Environment.getExternalStorageDirectory() + "/com.jaja.home/";//获取SDCard目录
            String gsonInfo = null;

            if (dirName.equals(HomeFrag.homeCacheB)) {
                gsonInfo = gson.toJson(homeBannerModel);
            } else if (dirName.equals(HomeFrag.homeCacheR)) {
                gsonInfo = gson.toJson(recommendModel);
            } else if (dirName.equals(SetFrag.dirName)) {
                gsonInfo = gson.toJson(setListModel);
            } else if (dirName.equals(Itemfrag.dirName)) {
                gsonInfo = gson.toJson(itemListModel);
            } else if (dirName.equals(ShowDetailFrag.dirName)) {
                gsonInfo = gson.toJson(showListModel);
            } else if (dirName.equals(DiyFrag.dirName)) {
                gsonInfo = gson.toJson(diyListModel);
            } else if (dirName.equals(HomeFrag.homeCacheSI)) {
                gsonInfo = gson.toJson(recommendSetModel);
            } else if (dirName.equals(GuildAct.dirName)) {
                gsonInfo = gson.toJson(launchModel);
            }

            result = AESUtils.encode(gsonInfo);
            try {
                fileDir = new File(sdCardDir);
                if (!fileDir.exists()) {
                    fileDir.mkdir();
                }

                File saveFile = new File(sdCardDir + dirName);
                FileOutputStream outStream = null;
                if (!saveFile.exists()) {
                    saveFile.createNewFile();
                }
                outStream = new FileOutputStream(saveFile);
                outStream.write(result.getBytes());
                outStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public static String readData(String dirName) {
        String str = null;
        File fileDir = null;
        String sdCardDir = Environment.getExternalStorageDirectory() + "/com.jaja.home/";//获取SDCard目录
        try {
            fileDir = new File(sdCardDir);
            if (!fileDir.exists()) {
                fileDir.mkdir();
            }
            File saveFile1 = new File(sdCardDir + dirName);
            BufferedReader br = null;
            if (!saveFile1.exists()) {
                saveFile1.createNewFile();
            }
            br = new BufferedReader(new FileReader(saveFile1));
            String readline = "";
            StringBuffer sb = new StringBuffer();
            while ((readline = br.readLine()) != null) {
                System.out.println("readline:" + readline);
                sb.append(readline);
            }
            if (StringUtil.isEmpty(sb.toString())) {
                return null;
            } else {
                str = AESUtils.decode(sb.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }
}
