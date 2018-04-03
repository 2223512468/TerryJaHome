package com.jajahome.feature.user.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.jajahome.R;
import com.jajahome.base.BaseActivity;
import com.jajahome.event.EventMessage;
import com.jajahome.util.StringUtil;
import com.jajahome.widget.JShoppingCartWebView;

import butterknife.BindView;

/**
 * 0 - 待处理 ,  1 - 已处理(用户付款成功后,通过此表生成对应物流表中数据) , 2 - 订单取消(交易关闭)  3 交易完成 , 4 退款中 5 退款成功(交易关闭) 6 请询价(必须客服改价后才能处理) 7 已发货(待收货)
 *  加载购物车的H5
 */
public class ShoppingCartAct extends BaseActivity{
	/**
	 * 重新加载页面的action
	 */
	public static final int RELOAD_ACTION=0X998;
	/**
	 * 传递H5链接
	 */
	public static final String H5_URL = "H5_URL";
	/**
	 * 传递类型
	 */
	public static final String TYPE = "TYPE";

	private int type=0;//0 立即购买 1加入购物车
	/**
	 * 连接
	 */
	private String mUrl;
	@BindView(R.id.web_view)
	JShoppingCartWebView mWebView;
	@BindView(R.id.ibtn_back)
	ImageButton ibtnBack;
	@BindView(R.id.textView2)
	TextView textView2;

	@Override
	protected int getViewId() {
		return R.layout.act_h5_shopping_cart;
	}

	/**
	 * 调起立即购买
	 * @param context :context
	 * @param url  :连接
	 */
	public static void gotoBuy(Context context, String url) {
		Intent intent = new Intent(context, ShoppingCartAct.class);
		intent.putExtra(H5_URL, url);
		context.startActivity(intent);

	}

	/**
	 * 首页进入购物车
	 * @param context :context
	 * @param url  :连接
	 */
	public static void gotoShoppingCart(Context context, String url) {
		Intent intent = new Intent(context, ShoppingCartAct.class);
		intent.putExtra(H5_URL, url);
		intent.putExtra(TYPE, -1);
		context.startActivity(intent);

	}
	/**
	 * 调起加入购物车
	 * @param context :context
	 * @param url  :连接
	 */
	public static void addToShoppingCart(Context context, String url) {
		Intent intent = new Intent(context, ShoppingCartAct.class);
		intent.putExtra(H5_URL, url);
		intent.putExtra(TYPE, 1);
		context.startActivity(intent);

	}
	@Override
	protected void initEvent() {
		mUrl = getIntent().getStringExtra(H5_URL);
		type=getIntent().getIntExtra(TYPE,0);
		if (type==0) {
			textView2.setText(R.string.buy_now);
		}else if(type==-1){
			textView2.setText(R.string.shopping_cart);
		}else {
			textView2.setText(R.string.add_to_shopping_cart);
		}
		if (!StringUtil.isEmpty(mUrl)) {
			mWebView.loadUrl(mUrl);
		}else {
			showToast(R.string.data_error);
			finish();
		}
		ibtnBack.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mWebView.stop();
	}

	@Override
	public void onEventMainThread(EventMessage event) {
		super.onEventMainThread(event);
		if(event.action==RELOAD_ACTION){
			//重新加载页面
			mWebView.reload();
		}
	}
}
