package com.jajahome.util;

import com.google.gson.Gson;
import com.jajahome.model.WxTokenModel;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Administrator on 2017/3/6.
 */
public class HttpUtils {

    public static WxTokenModel wxTokenModel;

    public static String netConnect(String WxURL) {

        try {
            // 设置请求的地址 通过URLEncoder.encode(String s, String enc)
            // 使用指定的编码机制将字符串转换为 application/x-www-form-urlencoded 格式
            // 根据地址创建URL对象(网络访问的url)
            URL url = new URL(WxURL);
            // url.openConnection()打开网络链接
            HttpURLConnection urlConnection = (HttpURLConnection) url
                    .openConnection();
            urlConnection.setRequestMethod("GET");// 设置请求的方式
            urlConnection.setReadTimeout(5000);// 设置超时的时间
            urlConnection.setConnectTimeout(5000);// 设置链接超时的时间

            // 获取响应的状态码 404 200 505 302
            if (urlConnection.getResponseCode() == 200) {
                // 获取响应的输入流对象
                InputStream is = urlConnection.getInputStream();

                // 创建字节输出流对象
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                // 定义读取的长度
                int len = 0;
                // 定义缓冲区
                byte buffer[] = new byte[1024];
                // 按照缓冲区的大小，循环读取
                while ((len = is.read(buffer)) != -1) {
                    // 根据读取的长度写入到os对象中
                    os.write(buffer, 0, len);
                }
                // 释放资源
                is.close();
                os.close();
                // 返回字符串
                String result = new String(os.toByteArray());
                Gson gson = new Gson();
                wxTokenModel = gson.fromJson(result, WxTokenModel.class);
                return result;
            } else {

                return "";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }


    public static String urlEnodeUTF8(String str) {
        String result = str;
        try {
            result = URLEncoder.encode(str, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
