package com.jajahome.util.cacheutils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

/**
 * Created by Administrator on 2017/7/6.
 */
public class NetUtils {

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null && info.isConnected()) {
                // 当前网络是连接的
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    // 当前所连接的网络可用
                    if (info.getType() == connectivity.TYPE_WIFI) {
                        return true;
                    } else {
                        Toast.makeText(context, "建议在WIFI环境下使用", Toast.LENGTH_SHORT).show();
                        return true;
                    }

                }
            }
        }
        return false;
    }
}
