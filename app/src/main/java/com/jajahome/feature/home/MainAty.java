package com.jajahome.feature.home;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jajahome.R;
import com.jajahome.application.JaJaHomeApplication;
import com.jajahome.base.BaseActivity;
import com.jajahome.constant.Constant;
import com.jajahome.event.EventMessage;
import com.jajahome.feature.diy.fragment.DiyFrag;
import com.jajahome.feature.home.adapter.HomeGridAdapter;
import com.jajahome.feature.home.fragment.HomeFrag;
import com.jajahome.feature.house.SelectCityAct;
import com.jajahome.feature.item.fragment.Itemfrag;
import com.jajahome.feature.search.SearchShowAct;
import com.jajahome.feature.set.fragment.SetFrag;
import com.jajahome.feature.show.fragment.ShowFrag;
import com.jajahome.feature.user.activity.LoginAct;
import com.jajahome.feature.user.activity.MyCollectAct;
import com.jajahome.feature.user.activity.ShoppingCartAct;
import com.jajahome.feature.user.activity.UserAct;
import com.jajahome.feature.zxing.MipcaActivityCapture;
import com.jajahome.model.ConfigModel;
import com.jajahome.model.LoginModle;
import com.jajahome.model.ShoppingCartNumsModel;
import com.jajahome.model.SimpleModel;
import com.jajahome.network.ApiImp;
import com.jajahome.network.BaseReq;
import com.jajahome.network.DetailReq;
import com.jajahome.network.HttpCode;
import com.jajahome.network.HttpUtil;
import com.jajahome.util.FilterSpUtil;
import com.jajahome.util.LocationSvc;
import com.jajahome.util.LoginUtil;
import com.jajahome.util.StringUtil;
import com.jajahome.util.cacheutils.NetUtils;
import com.jajahome.widget.ApkDownload;
import com.jajahome.widget.commontablayout.CommonTabLayout;
import com.jajahome.widget.commontablayout.CustomTabEntity;
import com.jajahome.widget.commontablayout.OnTabSelectListener;
import com.jajahome.widget.commontablayout.TabEntity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * 首页(包含4个Fragment)
 */
public class MainAty extends BaseActivity implements View.OnClickListener, HomeGridAdapter.OnGridClickListener {
    public static final int WX_LOGIN = 0X00;
    public static final int QQ_LOGIN = 0X011;
    public static final int SINA_LOGIN = 0X02;
    public static final String ACTION = "item";
    public static final String ACTION_CLASS = "ACTION_CLASS";
    @BindView(R.id.image_person)
    CircleImageView imagePerson;
    @BindView(R.id.div)
    View div;
    @BindView(R.id.btn_shopping_cart)
    View mBtnShoppingCart; //购物车按钮
    @BindView(R.id.tv_shopping_cart_num)
    TextView mTvShoppingCart;
    @BindView(R.id.img_v)
    ImageView imgV;
    @BindView(R.id.div_top)
    View divTop;
    @BindView(R.id.container)
    FrameLayout container;
    private long exitTime;     //返回退出
    @BindView(R.id.tablayout_bottom)
    CommonTabLayout mTabLayout;
    @BindView(R.id.diy_collect)
    ImageView diyImage;
    @BindView(R.id.city_tv)
    TextView cityName;
    @BindView(R.id.er_wm)
    ImageView eImv;
    @BindView(R.id.img_search)
    ImageView imgSearch;


    private LoginModle info;   //登陆信息
    private SetFrag mSetFrag;  //套装fragment
    private ProgressDialog dialog;
    private String userCity;
    private String city = "";
    public static final int PERMISSON_LOC = 0X14;
    public static final int PERMISSON_CAMERA = 0X15;
    public static String Tag = "MainAty";
    ApkDownload apk;
    private Map<Integer, List<View>> imgMap = new HashMap<>();

    @Override
    protected int getViewId() {
        return R.layout.act_main;
    }

    @Override
    protected void initEvent() {

        if (!NetUtils.isNetworkAvailable(this)) {
            Toast.makeText(this, "请检查网络，建议WIFI下使用", Toast.LENGTH_SHORT).show();
        } else {
            apk = new ApkDownload(this, Tag);
            apk.check();
        }

        initFragments();
        getConfig();
        imagePerson.setOnClickListener(MainAty.this);
        mBtnShoppingCart.setOnClickListener(MainAty.this);
        diyImage.setOnClickListener(MainAty.this);
        cityName.setOnClickListener(this);
        eImv.setOnClickListener(this);
        imgSearch.setOnClickListener(this);

        // 注册广播
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.LOCATION_ACTION);

        this.registerReceiver(mLocationBroadcastReceiver, filter);

        checkPermission();

        // 等待提示
        dialog = new ProgressDialog(this);
        dialog.setMessage("正在定位...");
        dialog.setCancelable(true);
        putView();
        setTab();
    }

    private LocationBroadcastReceiver mLocationBroadcastReceiver = new LocationBroadcastReceiver();

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mLocationBroadcastReceiver != null) {
            unregisterReceiver(mLocationBroadcastReceiver);
        }
        if (apk != null) {
            apk.setContextAlive(false);
        }

    }

    /**
     * Android 6.0 以后 相机权限
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void checkCameraPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            //询问是否允许
            ActivityCompat.requestPermissions((Activity) mContext, new String[]{Manifest.permission.CAMERA}, PERMISSON_CAMERA);
        } else {
            Intent intent = new Intent(this, MipcaActivityCapture.class);
            startActivity(intent);
        }
    }

    /**
     * Android 6.0 以后 定位
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void checkPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //无定位权限，询问是否允许
            ActivityCompat.requestPermissions((Activity) mContext, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSON_LOC);
        } else {
            // 启动服务
            Intent intent = new Intent();
            intent.setClass(this, LocationSvc.class);
            startService(intent);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSON_LOC) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 启动服务
                Intent intent = new Intent();
                intent.setClass(this, LocationSvc.class);
                startService(intent);
            } else {
                showToast("权限被禁止，无法获取位置信息");
            }
        } else if (requestCode == PERMISSON_CAMERA) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 启动服务
                Intent intent = new Intent(this, MipcaActivityCapture.class);
                startActivity(intent);
            } else {
                showToast("权限被禁止，无法使用该功能");
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        info = LoginUtil.getInfo(this);
        //是否有个人信息数据
        if (info != null && info.getData() != null) {
            String type = info.getData().getUser().getType();
            setType(type);

            //获取购物车数量
            getShoppingCartNums();
            LoginModle.DataBean.UserBean.AvatarBean avatarBean = info.getData().getUser().getAvatar();

            if (avatarBean != null) {
                //头像是否为空
                String avatarUrlBig = avatarBean.getSmall();
                if (!StringUtil.isEmpty(avatarUrlBig)) {
                    Picasso.with(this).load(info.getData().getUser().getAvatar().getUrl()).error(R.mipmap.ic_holder_header_small).placeholder(R.mipmap.ic_holder_header_big).into(imagePerson);
                } else {
                    imagePerson.setImageResource(R.mipmap.ic_holder_header_big);
                }
            } else {
                imagePerson.setImageResource(R.mipmap.ic_holder_header_small);
            }
        } else {
            imgV.setVisibility(View.GONE);
            mTvShoppingCart.setVisibility(View.GONE);
            imagePerson.setImageResource(R.mipmap.ic_holder_header_big);

        }
    }

    public static String actionId;
    public static String action;
    private JaJaHomeApplication app;

    private void setTab() {
        action = getIntent().getStringExtra(ACTION_CLASS);
        actionId = getIntent().getStringExtra(ACTION);
        app = (JaJaHomeApplication) getApplication();

        if (!StringUtil.isEmpty(action) && action.equals("set")) {
            mTabLayout.setCurrentTab(1);
        } else if (!StringUtil.isEmpty(actionId) && !actionId.equals("-1")) {
            mTabLayout.setCurrentTab(2);
            app.setmId(actionId);
            app.setAction(action);
        } else if (!StringUtil.isEmpty(actionId) && actionId.equals("-1")) {
            mTabLayout.setCurrentTab(2);
        }
    }


    private void setType(String type) {
        if (StringUtil.isEmpty(type)) return;
        //1 注册用户(一般用户), 2 设计师(申请中), 3 认证设计师(审核通过), 4装饰公司,5 城市合伙人,6协会
        switch (type) {
            case "1":
                imgV.setVisibility(View.GONE);
                break;
            case "2":
                imgV.setVisibility(View.GONE);
                break;
            case "3":
                if (info != null) {
                    int type_class = info.getData().getUser().getType_class();
                    if (type_class == 1) {
                        imgV.setImageResource(R.mipmap.ic_v_designer);
                        imgV.setVisibility(View.VISIBLE);
                    } else if (type_class == 5) {
                        imgV.setImageResource(R.mipmap.ic_v_super_designer);
                        imgV.setVisibility(View.VISIBLE);
                    }
                }
                break;
            case "4":
                imgV.setImageResource(R.mipmap.ic_v_decoration_company);
                imgV.setVisibility(View.VISIBLE);
                break;
            case "5":
                imgV.setImageResource(R.mipmap.ic_v_city_partner);
                imgV.setVisibility(View.VISIBLE);
                break;
            case "6":
                imgV.setImageResource(R.mipmap.ic_v_xiehui);
                imgV.setVisibility(View.VISIBLE);
                break;
            case "7":
                imgV.setImageResource(R.mipmap.ic_v_facilitator);
                imgV.setVisibility(View.VISIBLE);
                break;
            case "8":
                imgV.setImageResource(R.mipmap.ic_v_internastaff);
                imgV.setVisibility(View.VISIBLE);
                break;
        }
    }

    /**
     * 获取配置信息
     */
    private void getConfig() {
        DetailReq req = new DetailReq();
        req.setCmd(Constant.CONFIG_V2);
        DetailReq.ContentBean contentBean = new DetailReq.ContentBean();
        contentBean.setCity(city);
        req.setContent(contentBean);
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(req));
        mSubscription = ApiImp.get().
                config_v2(requestBody, HttpUtil.getSession(this)).
                subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Observer<ConfigModel>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(ConfigModel model) {
                        FilterSpUtil.saveModel(mContext, model);
                    }
                });
    }

    /**
     * 初始化页面控件（主要是Fragment）
     */
    private void initFragments() {
        mTabLayout.setTabData(genTabEntity(), getSupportFragmentManager(), R.id.container, genFragmnet());
        mTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                if (position == 0 || position == 4) {
                    imgSearch.setVisibility(View.GONE);
                } else {
                    imgSearch.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
    }

    private ArrayList<CustomTabEntity> genTabEntity() {
        ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
        int[] mIconUnselectIds = {
                R.mipmap.ic_home_home_unselected, R.mipmap.ic_home_set_unselected,
                R.mipmap.ic_home_item_unselected, R.mipmap.ic_home_show_unselected, R.mipmap.ic_home_diy_unselected};
        int[] mIconSelectIds = {
                R.mipmap.ic_home_home_selected, R.mipmap.ic_home_set_selected,
                R.mipmap.ic_home_item_selected, R.mipmap.ic_home_show_selected, R.mipmap.ic_home_diy_selected};
        for (int i = 0; i < mIconUnselectIds.length; i++) {
            mTabEntities.add(new TabEntity("", mIconSelectIds[i], mIconUnselectIds[i]));
        }
        return mTabEntities;
    }

    /**
     * 获取需要关联的fragment的数据
     *
     * @return ：关联的fragment的数据
     */

    private ArrayList<Fragment> genFragmnet() {
        ArrayList<Fragment> list = new ArrayList<>();
        HomeFrag homeFrag = new HomeFrag();
        Bundle b = new Bundle();
        b.putString(Constant.LOCATION, userCity);
        if (cityName != null) {
            b.putString(Constant.LOCATION_ACTION_CITY, city);
        }
        homeFrag.setArguments(b);
        homeFrag.setListener(this);
        list.add(homeFrag);
        mSetFrag = new SetFrag();
        list.add(mSetFrag);

        list.add(new Itemfrag());
        list.add(new ShowFrag());
        list.add(new DiyFrag());
        return list;
    }

    // 返回键监听
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (event.getAction() == KeyEvent.ACTION_DOWN
                    && event.getRepeatCount() == 0) {
                exitFirst();
            }
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

    /**
     * 退出程序
     */
    private void exitFirst() {
        // 判断2次点击事件时间
        if (!StringUtil.isEmpty(actionId) || !StringUtil.isEmpty(action)) {
            finish();
        } else {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                showToast(R.string.press_again_to_exit);
                exitTime = System.currentTimeMillis();
            } else {
                finish();
            }
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //点击头像进入个人中心
            case R.id.image_person:
                if (LoginUtil.isLogin(mContext)) {
                    gotoNewAty(UserAct.class);
                } else {
                    gotoNewAty(LoginAct.class);
                }
                break;
            case R.id.btn_shopping_cart:
                if (LoginUtil.isLogin(mContext)) {
                    ShoppingCartAct.gotoShoppingCart(mContext, getShoppingCartReqUrl());
                } else {
                    gotoNewAty(LoginAct.class);
                }
                break;
            case R.id.diy_collect:
                if (LoginUtil.isLogin(mContext)) {
                    getUserInfo();
                } else {
                    gotoNewAty(LoginAct.class);
                }
                break;
            case R.id.city_tv:
                LoginModle loginModle = LoginUtil.getInfo(this);
                if (loginModle == null) {
                    Intent intent = new Intent(this, LoginAct.class);
                    startActivity(intent);
                } else {
                    if (cityName.getText() != null) {
                        Intent intent = new Intent(this, SelectCityAct.class);
                        if (city != null) {
                            intent.putExtra(Constant.LOCATION, city);
                        }
                        startActivity(intent);
                    }
                }

                break;
            case R.id.er_wm:
                checkCameraPermission();
                break;
            case R.id.img_search:
                Intent intent = new Intent(mContext, SearchShowAct.class);
                if (mTabLayout.getCurrentTab() == 1) {
                    intent.putExtra(SearchShowAct.TYPE_EXTRA, SearchShowAct.TYPE_SET);
                } else if (mTabLayout.getCurrentTab() == 2) {
                    intent.putExtra(SearchShowAct.TYPE_EXTRA, SearchShowAct.TYPE_ITEM);
                } else {
                    intent.putExtra(SearchShowAct.TYPE_EXTRA, SearchShowAct.TYPE_SHOW);
                }
                startActivity(intent);
                break;

        }
    }


    private String getShoppingCartReqUrl() {
        return Constant.SHOPPING_CART + "user_id=" + info.getData().getUser().getId() + "&user_token="
                + info.getData().getSession().getToken();
    }

    @Override
    public void onGridClicked(String filter) {
        mTabLayout.setCurrentTab(1);
        mSetFrag.onFilter(filter);
    }

    @Override
    public void onBrandClked(String barnd) {
        //完整家居
        mTabLayout.setCurrentTab(1);
        mSetFrag.onFilter(barnd);
    }

    /**
     * 请求获取购物车数量
     */
    public void getShoppingCartNums() {
        mSubscription = ApiImp.get().
                shoppingCartNums(HttpUtil.getRequestBody(Constant.SHOPCART_NUMBER), HttpUtil.getSession(mContext)).
                subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Observer<ShoppingCartNumsModel>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(ShoppingCartNumsModel model) {
                        if (model.getStatus() == HttpCode.SUCCESS) {
                            int num = model.getData().getNumber();
                            if (num > 0) {
                                //购物车商品数量大于0
                                mTvShoppingCart.setVisibility(View.VISIBLE);
                                mTvShoppingCart.setText(String.valueOf(num));
                            } else {
                                mTvShoppingCart.setVisibility(View.GONE);
                            }
                        }
                    }
                });
    }

    private class LocationBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (!intent.getAction().equals(Constant.LOCATION_ACTION)) return;
            userCity = intent.getStringExtra(Constant.LOCATION);
            city = intent.getStringExtra(Constant.LOCATION_NAME);
            JaJaHomeApplication.city = city;
            dialog.dismiss();
            cityName.setText(city);
            getConfig();
            unregisterReceiver(mLocationBroadcastReceiver);// 不需要时注销
            mLocationBroadcastReceiver = null;
        }
    }

    @Override
    public void onEventMainThread(EventMessage event) {
        super.onEventMainThread(event);
        switch (event.action) {
            case Constant.homeTag:
                position = 0;
                break;
            case Constant.setTag:
            case Constant.itemTag:
            case Constant.showTag:
                position = 1;
                break;
            case Constant.diyTag:
                position = 2;
                break;
            default:
                break;
        }
        forEach(position);
    }

    private List<View> listViews;
    private int position;

    private void putView() {

        List<View> posList1 = new ArrayList<>();
        posList1.add(cityName);
        posList1.add(eImv);
        imgMap.put(0, posList1);

        List<View> elseList = new ArrayList<>();
        elseList.add(imgSearch);
        imgMap.put(1, elseList);

        List<View> posList5 = new ArrayList<>();
        posList5.add(diyImage);
        imgMap.put(2, posList5);
    }

    private void forEach(int pos) {
        for (Map.Entry e : imgMap.entrySet()) {
            int key = (int) e.getKey();
            listViews = (List<View>) e.getValue();
            if (pos == key) {
                for (View view : listViews) {
                    view.setVisibility(View.VISIBLE);
                }
            } else {
                for (View view : listViews) {
                    view.setVisibility(View.GONE);
                }
            }
        }
    }


    public void getUserInfo() {
        if (LoginUtil.getInfo(mContext) == null) return;
        BaseReq baseReq = new BaseReq();
        baseReq.setCmd(Constant.USERINFO);
        BaseReq.ContentBean contentBean = new BaseReq.ContentBean();
        baseReq.setContent(contentBean);
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(baseReq));
        RequestBody requestBody1 = RequestBody.create(MediaType.parse("application/session"), gson.toJson(LoginUtil.getInfo(mContext).getData().getSession()));
        mSubscription = ApiImp.get().userInfo(requestBody, requestBody1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SimpleModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(SimpleModel model) {
                        if (model.getStatus() == HttpCode.INVALID_LOGIN_STATE) {
                            showLoginStatusDiyErrorDialog(0);
                        } else if (model.getStatus() != HttpCode.SUCCESS) {
                            showToast(model.getMessage());
                        } else if (model.getStatus() == HttpCode.SUCCESS) {
                            Gson gson = new Gson();

                            LoginModle loginModle = gson.fromJson(gson.toJson(model), LoginModle.class);
                            info = LoginUtil.getInfo(mContext);
                            info.getData().setUser(loginModle.getData().getUser());
                            LoginUtil.saveInfo(mContext, info);
                            gotoNewAty(MyCollectAct.class);
                        }
                    }
                });
    }
}
