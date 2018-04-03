package com.jajahome.pop;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.jajahome.R;
import com.jajahome.constant.Constant;
import com.jajahome.feature.TencentWebV;
import com.jajahome.feature.item.ItemDetailAct;
import com.jajahome.feature.set.SetDetailAct;
import com.jajahome.feature.user.activity.UserAct;
import com.jajahome.model.LoginModle;
import com.jajahome.util.DensityUtil;
import com.jajahome.util.LoginUtil;
import com.jajahome.util.StringUtil;
import com.jajahome.util.T;
import com.jajahome.util.Util;
import com.jajahome.widget.ImageZoomUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.SendMultiMessageToWeiboRequest;
import com.tencent.connect.share.QQShare;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import java.io.File;
import java.io.FileOutputStream;


/**
 * 类描述：弹出分享 （只能是url 获取text中一种）
 * 创建人：admin
 * 创建时间：2016/4/25 16:19
 * 修改人：admin
 * 修改时间：2016/4/25 16:19
 * 修改备注：
 */
public class PopShare {
    private Context mContext;
    private PopupWindow mPopupWindow;
    private View view;
    //警告语
    private String mText;
    private Tencent mTencent;                 //QQ  QQ空间分析api
    private IWXAPI mIwxapi;                   //微信分享api
    private IWeiboShareAPI mIWeiboShareAPI;   //微博分享api
    private String text;     //分享的文本
    private String mUrl;     //分享的连接
    private String mSubhead;  //副标题
    private LinearLayout mLL;
    private TextView mTvCode;
    private String ShowURI, ShowText;
    private Bitmap shareBit;
    private LoginModle info;
    private String Tag;

    public void setText(String text) {
        this.text = text;
    }

    public void setmUrl(String mUrl) {
        this.mUrl = mUrl;
    }


    public String getShowURI() {
        return ShowURI;
    }

    public void setShowURI(String showURI) {
        ShowURI = showURI;
        imageLoader.loadImage(ShowURI, new SimpleImageLoadingListener() {
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                super.onLoadingComplete(imageUri, view, loadedImage);
                shareBit = loadedImage;
            }
        });
    }

    public String getShowText() {
        return ShowText;
    }

    public void setShowText(String showText) {
        ShowText = showText;
    }

    public String getmSubhead() {
        return mSubhead;
    }

    public void setmSubhead(String mSubhead) {
        this.mSubhead = mSubhead;
    }

    public PopShare(Context context, IWXAPI mIwxapi, IWeiboShareAPI mIWeiboShareAPI, View view) {
        mTencent = Tencent.createInstance(Constant.APP_ID, context.getApplicationContext());
        this.mIwxapi = mIwxapi;
        this.mIWeiboShareAPI = mIWeiboShareAPI;
        this.mIwxapi.registerApp(Constant.WEICHAT_APP_ID);
        this.mContext = context;
        info = LoginUtil.getInfo(mContext);
        this.view = view;
        init();
    }


    public PopShare(Context context, IWXAPI mIwxapi, IWeiboShareAPI mIWeiboShareAPI, View view, String Tag) {
        mTencent = Tencent.createInstance(Constant.APP_ID, context.getApplicationContext());
        this.mIwxapi = mIwxapi;
        this.mIWeiboShareAPI = mIWeiboShareAPI;
        this.mIwxapi.registerApp(Constant.WEICHAT_APP_ID);
        this.mContext = context;
        info = LoginUtil.getInfo(mContext);
        this.view = view;
        this.Tag = Tag;
        init();
    }

    protected void init() {
        View rootView = null;
        if (!StringUtil.isEmpty(Tag) && Tag.equals(TencentWebV.Tag)) {
            rootView = LayoutInflater.from(mContext).inflate(R.layout.act_vr_pop, null);
            mPopupWindow = new PopupWindow(rootView, ViewGroup.LayoutParams.WRAP_CONTENT, (int) DensityUtil.getDisplayHeightDp(mContext) / 2);
        } else {
            rootView = LayoutInflater.from(mContext).inflate(R.layout.pop_share, null);
            mPopupWindow = new PopupWindow(rootView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }
        LinearLayout viewSinaWeibo = (LinearLayout) rootView.findViewById(R.id.view_weibo);
        LinearLayout viewQQ = (LinearLayout) rootView.findViewById(R.id.view_qq);
        LinearLayout viewWeichat = (LinearLayout) rootView.findViewById(R.id.view_weixin);
        LinearLayout viewWeichatZone = (LinearLayout) rootView.findViewById(R.id.view_weixin_zone);
        mTvCode = (TextView) rootView.findViewById(R.id.pop_share_tv_code);
        mLL = (LinearLayout) rootView.findViewById(R.id.pop_share_img);

        mPopupWindow.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.bg_transparent));
        mPopupWindow.setFocusable(true);

        rootView.findViewById(R.id.btn_dismiss).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        //点击微博分享
        viewSinaWeibo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                shareToWeiBo();
            }
        });
        viewQQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (StringUtil.isEmpty(text)) {
                    shareToQQ();
                } else {
                    shareToQQText();
                }

            }
        });
        viewWeichat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (StringUtil.isEmpty(text)) {
                    shareToWeichat(true);
                } else {
                    shareToWeichatText(true);
                }

            }
        });
        viewWeichatZone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (StringUtil.isEmpty(text)) {
                    shareToWeichat(false);
                } else {
                    shareToWeichatText(false);
                }
            }
        });
    }

    /**
     * 分享到微博
     */
    private void shareToWeiBo() {
        sendWeiboMessage();
    }


    /**
     * 显示popupwindow
     */
    public void show() {
        if (mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        } else {
            mPopupWindow.showAtLocation(view, 0, 0, 0);
        }
    }

    public void vrShow() {
        if (mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        } else {
            mPopupWindow.showAtLocation(view, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        }
    }

    /**
     * 显示popupwindow
     *
     * @param view
     */
    public void show(View view, String text) {
        if (mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        } else {
            mPopupWindow.showAtLocation(view, 0, 0, 0);
        }
    }

    /**
     * 关闭popupwindow
     */
    public void dismiss() {
        if (mPopupWindow.isShowing()) mPopupWindow.dismiss();
    }

    /**
     * show er
     */
    public void showER() {
        if (mLL != null) mLL.setVisibility(View.VISIBLE);
    }

    /**
     * show er
     */
    public void setCodeText(String text) {
        if (info != null && !StringUtil.isEmpty(info.getData().getUser().getInvitecode())) {
            mTvCode.setVisibility(View.VISIBLE);
            mTvCode.setText(text);
        } else {
            mTvCode.setVisibility(View.GONE);
        }
    }

    protected void showToast(String str) {
        //去换行
        Toast.makeText(mContext, str, Toast.LENGTH_SHORT).show();
    }

    IUiListener qqShareListener = new IUiListener() {
        @Override
        public void onCancel() {
            showToast("取消成功");
        }

        @Override
        public void onComplete(Object response) {
            // TODO Auto-generated method stub
            showToast("分享成功");
        }

        @Override
        public void onError(UiError e) {
            // TODO Auto-generated method stub
            showToast("分享到QQ失败：" + e.errorMessage);
        }
    };

    /**
     * 分享到QQ URL
     */
    ImageLoader imageLoader = ImageLoader.getInstance();

    public void shareToQQ() {
        if (mUrl == null) return;
        final Bundle params = new Bundle();
        if (!StringUtil.isEmpty(Tag) && Tag.equals(TencentWebV.Tag) && !StringUtil.isEmpty(TencentWebV.DialogUrl)) {
            params.putString(QQShare.SHARE_TO_QQ_TITLE, SetDetailAct.vrName + "VR套装");
            params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, mUrl);
            params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, ShowURI);
        } else if (!StringUtil.isEmpty(Tag) && Tag.equals(TencentWebV.Tag) && StringUtil.isEmpty(TencentWebV.DialogUrl)) {
            params.putString(QQShare.SHARE_TO_QQ_TITLE, "VR套装");
            Bitmap bmp = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.ic_launcher);
            params.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, getLocalPath(bmp));
            params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, mUrl);
        } else {
            params.putString(QQShare.SHARE_TO_QQ_TITLE, "JAJAHOME");
            params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, mUrl);
            params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, ShowURI);
        }
        if (StringUtil.isEmpty(mSubhead)) {
            params.putString(QQShare.SHARE_TO_QQ_SUMMARY, ShowText);
        } else {
            params.putString(QQShare.SHARE_TO_QQ_SUMMARY, mSubhead);
        }
        mTencent.shareToQQ((Activity) mContext, params, qqShareListener);
    }

    public void shareToQQText() {
        final Bundle params = new Bundle();
        if (!StringUtil.isEmpty(Tag) && Tag.equals(UserAct.Tag)) {
            params.putString(QQShare.SHARE_TO_QQ_TITLE, "JaJahome注册邀请码" + info.getData().getUser().getInvitecode());
            params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, Constant.APP_DOWNLOAD_URL);
        } else if (!StringUtil.isEmpty(Tag) && Tag.equals(TencentWebV.Tag)) {
            params.putString(QQShare.SHARE_TO_QQ_TITLE, SetDetailAct.vrName + "VR套装");
            //params.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, TencentWebV.filepath);
            params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, mUrl);
            params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, ShowURI);
        } else if (!StringUtil.isEmpty(Tag) && Tag.equals(SetDetailAct.SetDetailTag)) {
            params.putString(QQShare.SHARE_TO_QQ_TITLE, "JAJAHOME套装");
            params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, mUrl);
            params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, ShowURI);
        } else if (!StringUtil.isEmpty(Tag) && Tag.equals(ItemDetailAct.ItemDetailTag)) {
            params.putString(QQShare.SHARE_TO_QQ_TITLE, "JAJAHOME单品");
            params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, mUrl);
            params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, ShowURI);
        }

        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, text);
        mTencent.shareToQQ((Activity) mContext, params, qqShareListener);
    }

    /**
     * @param isChat
     */
    public void shareToWeichat(boolean isChat) {
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = mUrl;
        WXMediaMessage message = new WXMediaMessage(webpage);
        //message.title = "JAJAHOME";
        if (!StringUtil.isEmpty(Tag) && Tag.equals(SetDetailAct.SetDetailTag)) {
            message.title = "JAJAHOME套装";
        } else if (!StringUtil.isEmpty(Tag) && Tag.equals(ItemDetailAct.ItemDetailTag)) {
            message.title = "JAJAHOME单品";
        } else if (!StringUtil.isEmpty(Tag) && Tag.equals(TencentWebV.Tag) && !StringUtil.isEmpty(TencentWebV.DialogUrl)) {
            message.title = SetDetailAct.vrName + "VR套装";
        } else if (!StringUtil.isEmpty(Tag) && Tag.equals(TencentWebV.Tag) && StringUtil.isEmpty(TencentWebV.DialogUrl)) {
            message.title = "VR套装";
        } else {
            message.title = "JAJAHOME";
        }
        if (StringUtil.isEmpty(mSubhead)) {
            message.description = ShowText;
        } else {
            message.description = mSubhead;
        }
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        if (shareBit != null) {
            shareBit = ImageZoomUtils.zoomImg(shareBit, 100, 100);
        } else {
            shareBit = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.ic_launcher);
        }
        byte[] bytes = null;
        if (shareBit != null) {
            bytes = Util.bmpToByteArray(shareBit, true);
        }
        message.thumbData = bytes;
        req.message = message;
        req.scene = isChat ? SendMessageToWX.Req.WXSceneSession : SendMessageToWX.Req.WXSceneTimeline;
        mIwxapi.sendReq(req);
    }

    /**
     * @param isChat
     */
    public void shareToWeichatText(boolean isChat) {

        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = mUrl;
        WXMediaMessage message = new WXMediaMessage(webpage);
        if (!StringUtil.isEmpty(Tag) && Tag.equals(SetDetailAct.SetDetailTag)) {
            message.title = "JAJAHOME套装";
        } else if (!StringUtil.isEmpty(Tag) && Tag.equals(ItemDetailAct.ItemDetailTag)) {
            message.title = "JAJAHOME单品";
        } else if ((!StringUtil.isEmpty(Tag) && Tag.equals(UserAct.Tag))) {
            message.title = "JaJahome注册邀请码" + info.getData().getUser().getInvitecode();
        } else if (!StringUtil.isEmpty(Tag) && Tag.equals(TencentWebV.Tag) && !StringUtil.isEmpty(TencentWebV.DialogUrl)) {
            message.title = SetDetailAct.vrName + "VR套装";
        } else if (!StringUtil.isEmpty(Tag) && Tag.equals(TencentWebV.Tag) && StringUtil.isEmpty(TencentWebV.DialogUrl)) {
            message.title = "VR套装";
        } else {
            message.title = "JAJAHOME";
        }

        message.description = text;
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        if (shareBit != null) {
            try {
                shareBit = ImageZoomUtils.zoomImg(shareBit, 100, 100);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            shareBit = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.ic_launcher);
        }
        byte[] bytes = null;
        if (shareBit != null && !shareBit.isRecycled()) {
            bytes = Util.bmpToByteArray(shareBit, true);
        }
        message.thumbData = bytes;
        req.message = message;
        req.scene = isChat ? SendMessageToWX.Req.WXSceneSession : SendMessageToWX.Req.WXSceneTimeline;
        mIwxapi.sendReq(req);
    }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    /**
     * 获取微博分享的分享内容
     *
     * @return
     */
    private TextObject getWeiboTextObj() {
        TextObject textObject = new TextObject();
        if (!StringUtil.isEmpty(Tag) && Tag.equals(SetDetailAct.SetDetailTag)) {
            textObject.text = "JAJAHOME套装" + "\n" + text;
        } else if (!StringUtil.isEmpty(Tag) && Tag.equals(ItemDetailAct.ItemDetailTag)) {
            textObject.text = "JAJAHOME单品" + "\n" + text;
        } else if (!StringUtil.isEmpty(Tag) && Tag.equals(UserAct.Tag)) {
            textObject.text = "JaJahome注册邀请码" + info.getData().getUser().getInvitecode() + "\n" + text;
        } else if (!StringUtil.isEmpty(Tag) && Tag.equals(TencentWebV.Tag) && !StringUtil.isEmpty(TencentWebV.DialogUrl)) {
            textObject.text = SetDetailAct.vrName + "VR套装" + "\n" + mUrl;
        } else if (!StringUtil.isEmpty(Tag) && Tag.equals(TencentWebV.Tag) && StringUtil.isEmpty(TencentWebV.DialogUrl)) {
            textObject.text = "VR套装" + "\n" + mUrl;
        } else {
            if (!StringUtil.isEmpty(mUrl)) {
                textObject.text = mUrl;
            } else {
                textObject.text = text;
            }
        }

        return textObject;
    }

    /**
     * 获取分享图片
     */

    private ImageObject getWeiboImageObj() {
        /*图片对象*/
        ImageObject imageobj = new ImageObject();
        if (shareBit != null) {
            imageobj.setImageObject(shareBit);
        } else {
            shareBit = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.ic_launcher);
            imageobj.setImageObject(shareBit);
        }
        return imageobj;
    }

    private void sendWeiboMessage() {
        /**
         * 检查用户是否安装新浪微博
         */
        if (!mIWeiboShareAPI.isWeiboAppInstalled()) {
            T.show(mContext, "您未安装新浪微博客户端", Toast.LENGTH_SHORT);
            return;
        }
        WeiboMultiMessage multmess = new WeiboMultiMessage();
        multmess.textObject = getWeiboTextObj();
        if (shareBit != null) {
            multmess.imageObject = getWeiboImageObj();
        }
        SendMultiMessageToWeiboRequest multRequest = new SendMultiMessageToWeiboRequest();
        multRequest.multiMessage = multmess;
        //以当前时间戳为唯一识别符
        multRequest.transaction = String.valueOf(System.currentTimeMillis());
        mIWeiboShareAPI.sendRequest((Activity) mContext, multRequest);
    }

    private String getSDCardPath() {
        File sdcardDir = null;
        // 判断SDCard是否存在
        boolean sdcardExist = Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED);
        if (sdcardExist) {
            sdcardDir = Environment.getExternalStorageDirectory();
        }
        return sdcardDir.toString();
    }

    private String getLocalPath(Bitmap bmp) {
        String SavePath = getSDCardPath() + "/jajahome/screenimage";
        File path = new File(SavePath);
        // 文件
        String filepath = SavePath + "/screen_1.png";
        File file = new File(filepath);
        try {
            if (!path.exists()) {
                path.mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(file);
            if (null != fos) {
                bmp.compress(Bitmap.CompressFormat.PNG, 100, fos);
                fos.flush();
                fos.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return filepath;
    }

}
