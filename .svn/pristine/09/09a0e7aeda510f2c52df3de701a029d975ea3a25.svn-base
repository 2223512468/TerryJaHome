package com.jajahome.feature;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Environment;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.jajahome.R;
import com.jajahome.base.BaseActivity;
import com.jajahome.constant.Constant;
import com.jajahome.feature.set.SetDetailAct;
import com.jajahome.pop.PopShare;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.WeiboShareSDK;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import java.io.File;

import butterknife.BindView;

/**
 * Created by ${Terry} on 2016/11/29.
 */
public class TencentWebV extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.tbsContent)
    WebView tbsContent;
    @BindView(R.id.set_vr_exit)
    ImageView mBack;
    @BindView(R.id.set_vr_share)
    ImageView vrShareImg;


    private PopShare mPopShare; //分享弹窗
    private IWXAPI mIwxapi; //微信分享api
    private IWeiboShareAPI mIWeiboShareAPI;      //微博api


    public static final String H5_URL = "H5_URL";
    public static String vrUrl;
    public static String Tag = "TencentWebV";
    public static String DialogUrl;


    @Override
    protected int getViewId() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        return R.layout.layout_tencent_web;
    }

    @Override
    protected void initEvent() {

        mIwxapi = WXAPIFactory.createWXAPI(mContext, Constant.WEICHAT_APP_ID, true);
        //新浪微博分享
        mIWeiboShareAPI = WeiboShareSDK.createWeiboAPI(this, Constant.WEIBO_APP_KEY);
        mIWeiboShareAPI.registerApp();    // 将应用注册到微博客户端

        DialogUrl = SetDetailAct.mSetUrl;

        if (vrUrl != null) {
            tbsContent.loadUrl(vrUrl);
            WebSettings webSettings = tbsContent.getSettings();
            webSettings.setJavaScriptEnabled(true);
            tbsContent.setWebViewClient(new WebViewClient() {

                @Override
                public void onPageStarted(WebView webView, String s, Bitmap bitmap) {
                    super.onPageStarted(webView, s, bitmap);
                }

                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url);
                    return true;
                }

                @Override
                public void onPageFinished(WebView webView, String s) {
                    webView.invalidate();
                }
            });
        }
        setListener();
    }

    public static void gotoH5(Context context, String url) {
        Intent intent = new Intent(context, TencentWebV.class);
        vrUrl = url;
        intent.putExtra(H5_URL, url);
        context.startActivity(intent);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && tbsContent.canGoBack()) {
            tbsContent.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void setListener() {
        mBack.setOnClickListener(this);
        vrShareImg.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.set_vr_exit:
                finish();
                break;
            case R.id.set_vr_share:
                if (mPopShare == null) {
                    mPopShare = new PopShare(TencentWebV.this, mIwxapi, mIWeiboShareAPI, vrShareImg, Tag);
                    mPopShare.setmUrl(vrUrl);
                }
                //mPopShare.setText(vrUrl);
                mPopShare.setShowURI(DialogUrl);
                mPopShare.vrShow();
                break;
        }
    }

    public static String filepath;

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

    private Bitmap bmp;

/*    private void setBitmap() {

        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        int w = outMetrics.widthPixels;
        int h = outMetrics.heightPixels;
        //2.获取屏幕
        View decorview = this.getWindow().getDecorView();
        decorview.setDrawingCacheEnabled(true);
        View view = getWindow().getDecorView();
        view.buildDrawingCache();
        // 获取状态栏高度
        Rect rect = new Rect();
        view.getWindowVisibleDisplayFrame(rect);
        int statusBarHeights = rect.top;
        // 允许当前窗口保存缓存信息
        view.setDrawingCacheEnabled(true);
        // 去掉状态栏
        Bitmap bitCache = view.getDrawingCache();
        if (bitCache.getHeight() >= h) {
            bmp = Bitmap.createBitmap(bitCache, 0, 0, w, h);
        }
        String SavePath = getSDCardPath() + "/jajahome/screenimage";
        File path = new File(SavePath);
        // 文件
        filepath = SavePath + "/screen_1.png";
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
        bitCache.recycle();
        // 销毁缓存信息
        view.destroyDrawingCache();

    }*/

}
