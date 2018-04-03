package com.jajahome.widget.thirdlogin.qq;

import android.content.Context;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jajahome.event.EventMessage;
import com.jajahome.feature.home.MainAty;
import com.jajahome.model.QQtokenModel;
import com.jajahome.model.QQuserModel;
import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQToken;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017/3/7.
 */
public class BaseUiListener implements IUiListener {


    private Context mContext;
    private Tencent mTencent;
    private UserInfo mUserInfo;

    public BaseUiListener(Context mContext, Tencent mTencent) {

        this.mContext = mContext;
        this.mTencent = mTencent;
    }

    @Override
    public void onComplete(Object response) {
        JSONObject obj = (JSONObject) response;
        final Gson gson = new Gson();
        final QQtokenModel.NameValuePairsBean model = gson.fromJson(response.toString(), QQtokenModel.NameValuePairsBean.class);


        try {
            String openID = obj.getString("openid");
            String accessToken = obj.getString("access_token");
            String expires = obj.getString("expires_in");
            mTencent.setOpenId(openID);
            mTencent.setAccessToken(accessToken, expires);
            QQToken qqToken = mTencent.getQQToken();
            mUserInfo = new UserInfo(mContext.getApplicationContext(), qqToken);
            mUserInfo.getUserInfo(new IUiListener() {
                @Override
                public void onComplete(Object response) {
                    QQuserModel.NameValuePairsBean userModel = gson.fromJson(response.toString(), QQuserModel.NameValuePairsBean.class);
                    userModel.setOpenid(model.getOpenid());
                    EventBus.getDefault().post(new EventMessage(MainAty.QQ_LOGIN, gson.toJson(userModel)));

                }

                @Override
                public void onError(UiError uiError) {
                }

                @Override
                public void onCancel() {

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(UiError uiError) {
        Toast.makeText(mContext, "授权失败", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onCancel() {
        Toast.makeText(mContext, "授权取消", Toast.LENGTH_SHORT).show();

    }
}
