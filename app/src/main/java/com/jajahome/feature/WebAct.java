package com.jajahome.feature;

import android.content.Context;
import android.content.Intent;

import com.jajahome.R;
import com.jajahome.base.BaseActivity;
import com.jajahome.feature.home.adapter.HomeListAdapter;
import com.jajahome.util.StringUtil;
import com.jajahome.widget.ProgressWebView;

import butterknife.BindView;

/**
 * 只显示H5页
 */
public class WebAct extends BaseActivity implements ProgressWebView.OnWebViewActionListener {
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


    @Override
    protected int getViewId() {
        return R.layout.act_web;
    }

    /**
     * 显示H5页面的静态方法  不显示title
     *
     * @param context :context
     * @param url     :连接
     */
    public static void gotoH5(Context context, String url) {
        Intent intent = new Intent(context, WebAct.class);
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
            /*textView2.setText(title);*/
        } else {
            /*textView2.setText(R.string.connect_shopping);*/
        }
        if (!StringUtil.isEmpty(h5Url)) {
            progressBar.loadUrl(h5Url);
        } else if (!StringUtil.isEmpty(data)) {
            progressBar.loadData(data);
        } else {
            showToast(R.string.url_empty);
            finish();
        }

    }

    @Override
    public void onPhoneCall(String tel) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        progressBar.stop();
    }
}
