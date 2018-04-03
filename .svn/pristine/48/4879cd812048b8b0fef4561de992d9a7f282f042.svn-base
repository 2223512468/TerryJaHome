package com.jajahome.widget;

import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.ClientCertRequest;
import android.webkit.HttpAuthHandler;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.jajahome.R;
import com.jajahome.base.BaseActivity;
import com.jajahome.feature.item.ItemDetailAct;
import com.jajahome.feature.user.activity.AddressAct;
import com.jajahome.feature.user.activity.OrderDetailAct;


/**
 * 类描述：带有进度条的处理购物车页面的webview
 */
public class JShoppingCartWebView extends LinearLayout {
    /**
     * 关闭当前页面动作
     */
    private final String CLOSE_ACTION = "webview://close";
    /**
     * 添加地址的动作 (必须和页面中的webview://address对应)
     */
    private final String ADD_ADDRESS_ACTION = "webview://address";
    /**
     * 提交订单的动作 (必须和页面中的webview://address对应)
     */
    private final String ITEM_INFO = "webview://item_info?id=";
    /**
     * 提交订单的动作 (必须和页面中的webview://address对应)
     */
    private final String SUBMIT_ORDER_ACTION = "webview://order?order_id=";
    private String mUrl;//当前连接
    private WebView mWebView;
    private ProgressBar mProgressBar;
    private Context mContext;

    private boolean isSubmit=false;
    public JShoppingCartWebView(Context context) {
        super(context);
        init(context);
    }

    public JShoppingCartWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public JShoppingCartWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    protected void init(Context context) {
        mContext = context;
        setOrientation(VERTICAL);
        initProressView();
        mWebView = new WebView(context);
        addView(mWebView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        initWebView();
    }

    private void initProressView() {
        mProgressBar = (ProgressBar) LayoutInflater.from(mContext).inflate(R.layout.progress_bar_linear, null);
        mProgressBar.setMax(100);
        mProgressBar.setProgress(0);
        addView(mProgressBar, LayoutParams.MATCH_PARENT, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, 4, getResources().getDisplayMetrics()));
    }

    private void initWebView() {
        WebSettings s = mWebView.getSettings();
        s.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        s.setUseWideViewPort(true);
        s.setLoadWithOverviewMode(true);
        s.setSaveFormData(true);
        s.setJavaScriptCanOpenWindowsAutomatically(true);
        s.setJavaScriptEnabled(true);
        s.setGeolocationEnabled(true);
        s.setGeolocationDatabasePath("/data/data/org.itri.html5webview/databases/");
        s.setDomStorageEnabled(true);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                return super.shouldInterceptRequest(view, request);
            }

            @Override
            public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host, String realm) {
                super.onReceivedHttpAuthRequest(view, handler, host, realm);
            }

            @Override
            public void onFormResubmission(WebView view, Message dontResend, Message resend) {
                super.onFormResubmission(view, dontResend, resend);
            }

            @Override
            public void onReceivedClientCertRequest(WebView view, ClientCertRequest request) {
                super.onReceivedClientCertRequest(view, request);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //添加地址
                if (url.equals(CLOSE_ACTION)) {
                    ((BaseActivity) getContext()).finish();
                    return true;
                } else if (url.equals(ADD_ADDRESS_ACTION)) {
                    ((BaseActivity) getContext()).gotoNewAty(AddressAct.class);
                    return true;
                } else if (url.contains(SUBMIT_ORDER_ACTION)) {
                    //进入订单详情页面
                    String order = url.replace(SUBMIT_ORDER_ACTION, "");
                    Intent intent = new Intent(getContext(), OrderDetailAct.class);
                    intent.putExtra(OrderDetailAct.ORDER_ID, order);
                    getContext().startActivity(intent);
                    return true;
                }else if (url.contains(ITEM_INFO)) {
                    //进入单品
                    String id = url.replace(ITEM_INFO, "");
                    Intent intent = new Intent(getContext(), ItemDetailAct.class);
                    intent.putExtra(ItemDetailAct.ITEM_ID, id);
                    getContext().startActivity(intent);
                return true;
            }
                mUrl = url;
                view.loadUrl(url);
                return true;
            }
        });
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsBeforeUnload(WebView view, String url, String message, JsResult result) {
                return super.onJsBeforeUnload(view, url, message, result);
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                // TODO Auto-generated method stub
                super.onProgressChanged(view, newProgress);
                mProgressBar.setProgress(newProgress);
                if (newProgress == 100) {
                    if (mProgressBar != null) {
                        mProgressBar.setVisibility(View.GONE);
                    }
                    if(isSubmit){
                        isSubmit=false;
                        getWebView().loadUrl("javascript:submit_price()");
                    }
                }
            }
        });
    }

    public WebView getWebView() {
        return mWebView;
    }

    public void loadUrl(String url) {
        mUrl=url;
        getWebView().loadUrl(url);
    }

    public void loadData(String data) {
        getWebView().loadData(data, "text/html; charset=UTF-8", null);
    }

    public void stop() {
        mWebView.destroy();
    }

    public void reload() {
        refreshAfterAddAddress();
    }
    public void refreshAfterAddAddress() {
        isSubmit=true;
        mWebView.loadUrl(mUrl );
    }
}
