package com.jajahome.feature;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.jajahome.R;
import com.jajahome.base.BaseActivity;
import com.jajahome.constant.Constant;
import com.jajahome.feature.home.adapter.HomeListAdapter;
import com.jajahome.feature.view.LoginDialog;
import com.jajahome.pop.PopShare;
import com.jajahome.util.StringUtil;
import com.jajahome.widget.ProgressWebView;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.WeiboShareSDK;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import butterknife.BindView;

/**
 * 只显示H5页
 */
public class H5Act extends BaseActivity implements ProgressWebView.OnWebViewActionListener {
    private final int CALL_PHONE_CODE = 0X144;
    /**
     * 传递H5链接
     */
    public static final String H5_URL = "H5_URL";
    /**
     * 传递title
     */
    public static final String TITLE = "TITLE";

    /**
     * 传递HTML数据
     */
    public static final String DATA_HTML = "DATA_HTML";

    @BindView(R.id.progress_bar)
    ProgressWebView progressBar;
    @BindView(R.id.ibtn_back)
    ImageButton ibtnBack;
    @BindView(R.id.textView2)
    TextView textView2;
    @BindView(R.id.bottom_img_share)
    ImageView imgShare;

    private String phone;//h5中电话
    private PopShare mPopShare;   //分享弹窗
    private IWXAPI mIwxapi;       //微信分享api
    private IWeiboShareAPI mIWeiboShareAPI;      //微博api

    @Override
    protected int getViewId() {
        return R.layout.act_h5;
    }

    /**
     * 显示H5页面的静态方法  不显示title
     *
     * @param context :context
     * @param url     :连接
     */
    public static void gotoH5(Context context, String url) {
        Intent intent = new Intent(context, H5Act.class);
        intent.putExtra(H5_URL, url);
        context.startActivity(intent);

    }

    /**
     * 显示H5页面的静态方法  显示title
     *
     * @param context :context
     * @param url     :连接
     */
    public static void gotoH5(Context context, String url, String title) {
        Intent intent = new Intent(context, H5Act.class);
        intent.putExtra(H5_URL, url);
        intent.putExtra(TITLE, title);
        context.startActivity(intent);

    }

    public static void gotoH5(Context context, String url, String title, String Tag) {
        Intent intent = new Intent(context, H5Act.class);
        intent.putExtra(H5_URL, url);
        intent.putExtra(TITLE, title);
        intent.putExtra(HomeListAdapter.Tag, Tag);
        context.startActivity(intent);
    }

    /**
     * 显示H5页面的静态方法  显示title (html数据)
     *
     * @param context :context
     * @param data    :html数据
     */
    public static void gotoH5(String title, Context context, String data) {
        Intent intent = new Intent(context, H5Act.class);
        intent.putExtra(DATA_HTML, data);
        intent.putExtra(TITLE, title);
        context.startActivity(intent);

    }

    @Override
    protected void initEvent() {
        progressBar.setOnWebViewActionListener(this);
        final String h5Url = getIntent().getStringExtra(H5_URL);
        String title = getIntent().getStringExtra(TITLE);
        String data = getIntent().getStringExtra(DATA_HTML);
        String tag = getIntent().getStringExtra(HomeListAdapter.Tag);
        if (!StringUtil.isEmpty(title)) {
            textView2.setText(title);
        } else {
            textView2.setText(R.string.connect_shopping);
        }
        if (!StringUtil.isEmpty(h5Url)) {
            progressBar.loadUrl(h5Url);
        } else if (!StringUtil.isEmpty(data)) {
            progressBar.loadData(data);
        } else {
            showToast(R.string.url_empty);
            finish();
        }
        if (!StringUtil.isEmpty(tag)) {
            imgShare.setVisibility(View.VISIBLE);
            //初始化微信api
            mIwxapi = WXAPIFactory.createWXAPI(mContext, Constant.WEICHAT_APP_ID, true);
            //新浪微博分享
            mIWeiboShareAPI = WeiboShareSDK.createWeiboAPI(this, Constant.WEIBO_APP_KEY);
            mIWeiboShareAPI.registerApp();    // 将应用注册到微博客户端
            imgShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (StringUtil.isEmpty(h5Url)) { //链接为空时 使分享失效
                        return;
                    }
                    if (mPopShare == null) {
                        mPopShare = new PopShare(H5Act.this, mIwxapi, mIWeiboShareAPI, imgShare);
                        mPopShare.setmUrl(h5Url);
                    } else {
                        mPopShare.show();
                    }
                }
            });
        }
        ibtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onPhoneCall(String tel) {
        phone = tel;
        checkPermission();
    }


    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(mContext,
                Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE},
                    CALL_PHONE_CODE);
        } else {
            callPhone();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CALL_PHONE_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                callPhone();
            } else {
                showToast("未获取到权限，无法拨打电话");
            }
        }
    }

    private void callPhone() {
        LoginDialog.Builder builder = new LoginDialog.Builder(mContext);
        builder.setMessage("是否向" + phone + "拨打电话?");
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
                if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                startActivity(intent);
            }
        });
        builder.create().show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        progressBar.stop();
    }
}
