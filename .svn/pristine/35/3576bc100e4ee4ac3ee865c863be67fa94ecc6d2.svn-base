package com.jajahome.feature.item.fragment;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.jajahome.R;
import com.jajahome.application.JaJaHomeApplication;
import com.jajahome.base.BaseFragment;
import com.jajahome.constant.Constant;
import com.jajahome.widget.ItemWebView;

import butterknife.BindView;

/**
 * Created by Administrator on 2016/12/19.
 */
public class ItemDetailFrag extends BaseFragment {

    private WebSettings webSettings;
    @BindView(R.id.item_detail_web)
    ItemWebView webView;
    public static final String STATUS = "STATUS";

    public static ItemDetailFrag newInstance(int satus) {
        Bundle bundle = new Bundle();
        bundle.putInt(STATUS, satus);
        ItemDetailFrag imagePageFragment = new ItemDetailFrag();
        imagePageFragment.setArguments(bundle);
        return imagePageFragment;
    }

    /**
     * 单品Id
     */
    private String mId;
    private int mStatus;

    public static int action = 0x11;

    @Override
    protected int getViewId() {
        return R.layout.act_item_detail_h5;
    }

    @Override
    protected void init() {
        mStatus = getArguments().getInt(STATUS, 0);
        JaJaHomeApplication app = (JaJaHomeApplication) getActivity().getApplication();
        mId = app.getmId();


        webView.setFocusable(false);
        webSettings = webView.getSettings();
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSettings.setBlockNetworkImage(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webView.setWebViewClient(new GoodsDetailWebViewClient());


        String picTextUrl = Constant.ITEM_DETAIL_SEC + mId;
        String goldUrl = Constant.ITEM_DETAIL + mId;
        String buyUrl = Constant.ITEM_NOTICE;

        if (mStatus == 1) {
            webView.loadUrl(picTextUrl);
        } else if (mStatus == 2) {
            webView.loadUrl(goldUrl);
        } else if (mStatus == 3) {
            webView.loadUrl(buyUrl);
        }

    }

    private class GoodsDetailWebViewClient extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            webSettings.setBlockNetworkImage(false);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return true;
        }
    }

};
