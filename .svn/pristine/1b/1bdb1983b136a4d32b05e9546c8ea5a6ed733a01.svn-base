package com.jajahome.widget.thirdlogin.sina;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;

import com.jajahome.constant.Constant;
import com.jajahome.event.EventMessage;
import com.jajahome.feature.home.MainAty;
import com.jajahome.feature.user.activity.LoginAct;
import com.jajahome.network.UsersAPI;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Administrator on 2017/3/7.
 */
public class AuthListener implements WeiboAuthListener {

    private Context mContext;
    private Oauth2AccessToken mAccessToken;

    public AuthListener(Context mContext) {
        this.mContext = mContext;
    }


    @Override
    public void onComplete(Bundle values) {
        mAccessToken = Oauth2AccessToken.parseAccessToken(values);
        if (mAccessToken.isSessionValid()) {

            UsersAPI mUsersAPI = new UsersAPI(mContext, Constant.WEIBO_APP_KEY, mAccessToken);
            long uid = Long.parseLong(mAccessToken.getUid());
            mUsersAPI.show(uid, new RequestListener() {

                @Override
                public void onComplete(String response) {
                    if (!TextUtils.isEmpty(response)) {
                        EventBus.getDefault().post(new EventMessage(MainAty.SINA_LOGIN, response));
                    }
                }

                @Override
                public void onWeiboException(WeiboException e) {

                }
            });
        }
    }

    @Override
    public void onCancel() {
    }

    @Override
    public void onWeiboException(WeiboException e) {

    }
}
