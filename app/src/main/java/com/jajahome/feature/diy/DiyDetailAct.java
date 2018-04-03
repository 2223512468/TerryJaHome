package com.jajahome.feature.diy;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.jajahome.R;
import com.jajahome.base.BaseActivity;
import com.jajahome.constant.Constant;
import com.jajahome.diyview.ImageDiyFrameLayout;
import com.jajahome.event.EventMessage;
import com.jajahome.feature.H5Act;
import com.jajahome.feature.diy.adapter.DiyExpandableAdapter;
import com.jajahome.feature.set.SetDiyLandscapeAct;
import com.jajahome.feature.set.SetLandscapeAct;
import com.jajahome.feature.user.activity.LoginAct;
import com.jajahome.model.AddFavoriteModel;
import com.jajahome.model.DiyModel;
import com.jajahome.model.DiyPosModel;
import com.jajahome.model.DiySaveModel;
import com.jajahome.model.ImageModel;
import com.jajahome.model.LoginModle;
import com.jajahome.model.SetDiyPriceModel;
import com.jajahome.model.SetItemModel;
import com.jajahome.model.SetModel;
import com.jajahome.model.SimpleModel;
import com.jajahome.network.ApiImp;
import com.jajahome.network.BaseReq;
import com.jajahome.network.DiyReq;
import com.jajahome.network.HttpCode;
import com.jajahome.network.HttpUtil;
import com.jajahome.network.ReqPage;
import com.jajahome.network.SetItemPos;
import com.jajahome.pop.PopShare;
import com.jajahome.util.LocationSvc;
import com.jajahome.util.LoginUtil;
import com.jajahome.util.StringUtil;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 套装详情页
 */
public class DiyDetailAct extends BaseActivity implements
        DiyExpandableAdapter.OnSetItemChangeListener {
    public static final String SET_ID = "SET_ID"; //id
    public static final String SET_URL = "SET_URL"; //url
    public static final String TYPE = "TYPE";     //类型
    public static final String DIY_TIP = "SET_TIP";

    @BindView(R.id.ibtn_back)
    ImageButton ibtnBack;

    @BindView(R.id.img_frame_layout)
    ImageDiyFrameLayout imgFrameLayout;

    TextView tvName;
    TextView tvPriceDiscount;
    TextView tvPriceBisic;
    TextView tvSetTips;

    @BindView(R.id.listview)
    ExpandableListView listview;
    @BindView(R.id.bottom_img_msg)
    ImageView bottomImgMsg;
    @BindView(R.id.bottom_img_collect)
    ImageView bottomImgCollect;
    @BindView(R.id.bottom_img_share)
    ImageView bottomImgShare;
    @BindView(R.id.tv_save)
    TextView tvSave;
    @BindView(R.id.set_mod)
    ImageView modTv;
    @BindView(R.id.scale_onclick)
    ImageView scale_onclick;
    @BindView(R.id.gone_tv)
    TextView gone_tv;
    Button btnSellList;

    /**
     * 分享的弹窗
     */

    private PopShare mPopShare;
    /**
     * 微信分享api
     */
    private IWXAPI mIwxapi;
    private String mId;//单品id
    private DiyModel mDiyModel;//套装数据
    private List<SetModel.DataBean.SetBean.ListBean> listItems;
    /**
     * 套装图 连接地址
     */
    private String mSetUrl;
    private String SetTips;
    /**
     * 0 默认模板+收藏夹 **
     */
    private int type;
    DiyExpandableAdapter adapter;
    private String cityName;

    /**
     * DIY ID
     */
    private String saveDiyId;
    private Boolean flag = true;
    public static SetItemPos item_ids[];
    private SetItemPos setItemPos;
    private List<SetItemPos> setItemPosList = new ArrayList<>();


    @Override
    protected int getViewId() {
        return R.layout.act_diy_detail;
    }

    @Override
    protected void initEvent() {
        initViewController(listview);
        mId = getIntent().getStringExtra(SET_ID);
        mSetUrl = getIntent().getStringExtra(SET_URL);
        type = getIntent().getIntExtra(TYPE, 0);
        SetTips = getIntent().getStringExtra(DIY_TIP);

        getDetail();
        showLoading(true, "");
        ibtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        setListener();
        mIwxapi = WXAPIFactory.createWXAPI(mContext, Constant.WEICHAT_APP_ID, true);
    }

    private void setListener() {
        bottomImgCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginModle loginModle = LoginUtil.getInfo(mContext);
                if (loginModle == null) {
                    gotoNewAty(LoginAct.class);
                } else {
                    addFav();
                }
            }
        });
        bottomImgMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (LocationSvc.sharedPreferencesUtils != null && !StringUtil.isEmpty(LocationSvc.sharedPreferencesUtils.getUserCity())) {
                    cityName = LocationSvc.sharedPreferencesUtils.getUserCity();
                } else {
                    cityName = "";
                }
                String about_us = Constant.ABOUT_US + "&city=" + cityName;
                H5Act.gotoH5(mContext, about_us, "联系我们");
            }
        });

        //保存
        bottomImgShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (LoginUtil.isLogin(mContext)) {
                    saveDiy();
                } else {
                    gotoNewAty(LoginAct.class);
                }
            }
        });

        modTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ImageDiyFrameLayout.isReverse == true) {
                    ImageDiyFrameLayout.isReverse = false;
                    setData();
                } else {
                    ImageDiyFrameLayout.isReverse = true;
                    setData();
                }

            }
        });

        listview.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {

                if (tvPriceDiscount != null) {
                    if (firstVisibleItem == 0) {
                        gone_tv.setVisibility(View.GONE);
                    } else {
                        gone_tv.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

    }

    private String loginInfo;

    @Override
    protected void onResume() {
        super.onResume();
        if (loginInfo != null) {
            getDetail();
            loginInfo = null;
        }
    }


    @Override
    public void onEventMainThread(EventMessage event) {
        super.onEventMainThread(event);
        if (event.action == Constant.LOGIN) {
            loginInfo = event.msg;
        }

    }


    /**
     * 保存diy信息
     */
    private void saveDiy() {
        DiyReq.ContentBean contentBean = new DiyReq.ContentBean();
        if (!StringUtil.isEmpty(saveDiyId)) {
            contentBean.setId(saveDiyId);
        } else {
            if (0 == type) {
                contentBean.setDiy_id(mId);
            } else {
                contentBean.setId(mId);
            }
        }
        contentBean.setName(mDiyModel.getData().getSet().getName());
        contentBean.setDesc(mDiyModel.getData().getSet().getName());
        contentBean.setDiy_pos(getDiyPosList());
        DiyReq reqPage = new DiyReq();
        reqPage.setContent(contentBean);
        reqPage.setCmd(Constant.SAVE_DIY);
        Gson gson = new Gson();
        showProgressDialog("保存中");
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(reqPage));
        mSubscription = ApiImp.get().saveDiy(requestBody, HttpUtil.getSession(mContext)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<SimpleModel>() {
            @Override
            public void onCompleted() {
                dissmisProgressDialog();
            }

            @Override
            public void onError(Throwable e) {
                dissmisProgressDialog();
                e.printStackTrace();
                showToast("网络出小差了");
            }

            @Override
            public void onNext(SimpleModel model) {
                if (model.getStatus() == HttpCode.SUCCESS) {
                    showToast(R.string.save_success);
                    Gson gson = new Gson();
                    DiySaveModel.DataBean bean = gson.fromJson(gson.toJson(model.getData()), DiySaveModel.DataBean.class);
                    saveDiyId = bean.getSet().getId();
                } else {
                    showToast(model.getMessage());
                }
            }
        });
    }


    /**
     * 获取diy保存的pos信息
     *
     * @return
     */
    private List<DiyPosModel> getDiyPosList() {
        List<SetModel.DataBean.SetBean.ListBean> list = imgFrameLayout.getmList();
        List<DiyPosModel> diyPosList = new ArrayList<>();
        for (SetModel.DataBean.SetBean.ListBean bean : list) {
            DiyPosModel model = new DiyPosModel();
            model.setId(bean.getId());
            Object object = bean.getItem_info().getImage();
            Gson gson = new Gson();
            try {
                ImageModel img = gson.fromJson(gson.toJson(object), ImageModel.class);
                model.setImage_url(img.getUrl());
            } catch (JsonSyntaxException e) {

            }
            model.setItem_id(bean.getItem_info().getId());
            diyPosList.add(model);
        }
        return diyPosList;
    }

    private List<SetDiyPriceModel.ItemsBean> getItems() {
        List<SetDiyPriceModel.ItemsBean> list = new ArrayList<>();
        List<SetModel.DataBean.SetBean.ListBean> listNew = imgFrameLayout.getmList();
        for (int i = 0; i < listNew.size(); i++) {
            SetModel.DataBean.SetBean.ListBean.ItemInfoBean itemInfoBean = getInfoBean(i, listNew);
            SetDiyPriceModel.ItemsBean itembean = new SetDiyPriceModel.ItemsBean();
            itembean.setId(itemInfoBean.getId());
            itembean.setFab_id(itemInfoBean.getFabric());
            itembean.setMat_id(itemInfoBean.getMaterial());
            list.add(itembean);
        }
        //排过序的
        return list;
    }


    private SetModel.DataBean.SetBean.ListBean.ItemInfoBean getInfoBean(int i, List<SetModel.DataBean.SetBean.ListBean> listNew) {
        for (SetModel.DataBean.SetBean.ListBean listBean : listNew) {
            if (listBean.getIndex() == i) {
                return listBean.getItem_info();
            }
        }
        return listNew.get(0).getItem_info();
    }

    /**
     * 获取套装详情信息
     */
    private void getDetail() {
        ReqPage.ContentBean contentBean = new ReqPage.ContentBean();
        contentBean.setId(mId);
        contentBean.setType(String.valueOf(type));//** 0 默认模板+收藏夹 **
        ReqPage reqPage = new ReqPage();
        reqPage.setContent(contentBean);
        reqPage.setCmd(Constant.DIY);
        final Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(reqPage));
        mSubscription = ApiImp.get().diy(requestBody, HttpUtil.getSession(mContext)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<DiyModel>() {
            @Override
            public void onCompleted() {
                showLoading(false, "");
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                showToast("网络异常或者账号在其他设备登录");
            }

            @Override
            public void onNext(DiyModel model) {
                mDiyModel = model;

                List<SetModel.DataBean.SetBean.ListBean> list = model.getData().getSet().getList();
                if (mDiyModel != null) {
                    modTv.setVisibility(View.VISIBLE);
                    scale_onclick.setVisibility(View.VISIBLE);
                    //item_ids = new SetItemPos[list.size()];
                /*    for (int i = 0; i < list.size(); i++) {
                        setItemPos = new SetItemPos();
                        String item_id = list.get(i).getItem_info().getId();
                        setItemPos.setItem_id(Integer.parseInt(item_id));

                        String pos_id = list.get(i).getId();
                        setItemPos.setPos_id(Integer.parseInt(pos_id));
                        item_ids[i] = setItemPos;

                    }*/

                    for (int i = 0; i < list.size(); i++) {
                        setItemPos = new SetItemPos();
                        String item_id = list.get(i).getItem_info().getId();
                        setItemPos.setItem_id(Integer.parseInt(item_id));
                        String pos_id = list.get(i).getId();
                        setItemPos.setPos_id(Integer.parseInt(pos_id));
                        setItemPosList.add(setItemPos);
                    }

                }
                setData();
            }
        });
    }

    /**
     * 设置数据
     */

    private String image_blueprint;

    private void setData() {

        DiyModel.DataBean.SetBean setBean = mDiyModel.getData().getSet();
        listItems = setBean.getList();
        image_blueprint = setBean.getImage_blueprint();
        if (adapter == null) {
            adapter = new DiyExpandableAdapter(mContext, listItems, type, imgFrameLayout);

            if (!StringUtil.isEmpty(image_blueprint)) {
                imgFrameLayout.setBottomImage(image_blueprint);
            }
        }
        imgFrameLayout.setmList(listItems);
        listview.setGroupIndicator(null);
        adapter.setOnSetItemChangeListener(this);
        //添加头部
        if (flag) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.header_set_detail, null);
            tvName = (TextView) view.findViewById(R.id.tv_name);
            tvPriceBisic = (TextView) view.findViewById(R.id.tv_price_basic);
            tvPriceDiscount = (TextView) view.findViewById(R.id.tv_price_discount);
            btnSellList = (Button) view.findViewById(R.id.btn_sell_list);
            tvSetTips = (TextView) view.findViewById(R.id.setTips);
            if (!StringUtil.isEmpty(SetTips)) {
                tvSetTips.setText(SetTips);
            } else tvSetTips.setVisibility(View.GONE);

            btnSellList.setVisibility(View.VISIBLE);
            listview.addHeaderView(view);
            listview.setAdapter(adapter);
            flag = false;

        }

        adapter.notifyDataSetChanged();
        listview.setFocusable(false);
        tvName.setText(setBean.getName());
        imgFrameLayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, SetDiyLandscapeAct.class);
                intent.putExtra(SetLandscapeAct.BITMAP, (Serializable) imgFrameLayout.getmList());
                intent.putExtra(SetDiyLandscapeAct.MAP, adapter.isBigMap);
                intent.putExtra(SetDiyLandscapeAct.BOTTOMURI, image_blueprint);
                startActivity(intent);
            }
        });

        scale_onclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, SetDiyLandscapeAct.class);
                intent.putExtra(SetLandscapeAct.BITMAP, (Serializable) imgFrameLayout.getmList());
                intent.putExtra(SetDiyLandscapeAct.MAP, adapter.isBigMap);
                intent.putExtra(SetDiyLandscapeAct.BOTTOMURI, image_blueprint);
                startActivity(intent);
            }
        });

        btnSellList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DiySellItemListAct.class);
                intent.putExtra(DiySellItemListAct.IDLIST, (Serializable) setItemPosList);
                intent.putExtra(DiySellItemListAct.DIY_MODEL, (Serializable) getItems());
                intent.putExtra(DiySellItemListAct.ID, type);
                intent.putExtra(DiySellItemListAct.MID, mId);
                startActivity(intent);
            }
        });

        setPrice();
        listview.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {

                if (LoginUtil.isLogin(DiyDetailAct.this)) {

                    if (clickMap.containsKey(i)) {

                        if (clickMap.get(i) == false) {
                            clickMap.put(i, true);
                            adapter.pushMap(clickMap);

                        } else {
                            clickMap.put(i, false);
                            adapter.pushMap(clickMap);
                        }

                    } else {
                        clickMap.put(i, true);
                        adapter.pushMap(clickMap);

                    }
                }
                return false;
            }
        });

    }

    private HashMap<Integer, Boolean> clickMap = new HashMap<>();

    /**
     * 获取套装中单品价格
     */
    /**
     * 获取套装中单品价格
     */
    private void setPrice() {
        int[] prices = imgFrameLayout.getPrice();
        double rate = mDiyModel.getData().getSet().getDiscount_rate(); //套装折扣
        if (prices[1] == 0) {
            tvPriceDiscount.setText(R.string.pls_ask_price);
            gone_tv.setText(R.string.pls_ask_price);
            tvPriceBisic.setVisibility(View.GONE);
        } else if (0 < rate && rate < 1) {
            int priceDis = prices[1]; //总单品价格之和  折扣后
            String text = Constant.YUAN + (int) (priceDis * rate);
            tvPriceDiscount.setText(text);
            gone_tv.setText(text);
            tvPriceBisic.setText(Constant.YUAN + prices[0]);
            tvPriceBisic.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            if (prices[0] == prices[1]) {
                String text = Constant.YUAN + prices[1];
                //  tvPriceDiscount.setText(text);
                gone_tv.setText(text);
                tvPriceBisic.setVisibility(View.GONE);
            } else {
               /* String text = Constant.YUAN + prices[1];
                tvPriceDiscount.setText(text);
                tvPriceBisic.setText(Constant.YUAN + prices[0]);
                tvPriceBisic.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);*/
                tvPriceBisic.setText(Constant.YUAN + prices[0] + "-" + prices[1]);
                gone_tv.setText(Constant.YUAN + prices[0] + "-" + prices[1]);
            }

            if (prices[2] == prices[3]) {
                String text = Constant.YUAN + prices[2];
                tvPriceDiscount.setText(text);
                gone_tv.setText(text);
            } else {
                tvPriceDiscount.setText(Constant.YUAN + prices[2] + "-" + prices[3]);
                gone_tv.setText(Constant.YUAN + prices[2] + "-" + prices[3]);
            }
        }
        if (rate == 1) {
            tvPriceBisic.setVisibility(View.GONE);
        }

    }

    /**
     * 添加收藏
     */
    private void addFav() {
        BaseReq baseReq = new BaseReq();
        baseReq.setCmd(Constant.ADD_FAVORITE);
        BaseReq.ContentBean contentBean = new BaseReq.ContentBean();
        contentBean.setId(Integer.valueOf(mId));
        contentBean.setType(0);
        baseReq.setContent(contentBean);
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(baseReq));
        showProgressDialog("收藏中");
        mSubscription = ApiImp.get().addFavorite(requestBody, HttpUtil.getSession(mContext)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<AddFavoriteModel>() {
            @Override
            public void onCompleted() {
                dissmisProgressDialog();
            }

            @Override
            public void onError(Throwable e) {
                dissmisProgressDialog();
                e.printStackTrace();
            }

            @Override
            public void onNext(AddFavoriteModel model) {
                showToast(R.string.collect_success);
            }
        });
    }

    /**
     * 删除收藏
     */
    private void delFav() {
        BaseReq baseReq = new BaseReq();
        baseReq.setCmd(Constant.ADD_FAVORITE);
        BaseReq.ContentBean contentBean = new BaseReq.ContentBean();
        contentBean.setId(Integer.valueOf(mId));
        contentBean.setType(1);
        baseReq.setContent(contentBean);
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(baseReq));
        showProgressDialog("取消收藏中");
        mSubscription = ApiImp.get().delFavorite(requestBody, HttpUtil.getSession(mContext)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<AddFavoriteModel>() {
            @Override
            public void onCompleted() {
                dissmisProgressDialog();
            }

            @Override
            public void onError(Throwable e) {
                dissmisProgressDialog();
                e.printStackTrace();
            }

            @Override
            public void onNext(AddFavoriteModel model) {
                showToast(R.string.del_collect_success);
            }
        });
    }

    @Override
    public void onSetItemChanged(String itemId, SetItemModel.DataBean.ListBean bean, String url, int groupPosition, String id) {
        imgFrameLayout.changeImage(itemId, bean, url, groupPosition);
        SetItemPos setItemPos = new SetItemPos();
        if (!StringUtil.isEmpty(id) && !StringUtil.isEmpty(itemId)) {
            setItemPos.setItem_id(Integer.parseInt(id));
            setItemPos.setPos_id(Integer.parseInt(itemId));
        }
        //item_ids[groupPosition] = setItemPos;
        setItemPosList.set(groupPosition, setItemPos);
        setPrice();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == DiySelectItemAct.RESULT_CODE) {
            Bundle bundle = data.getExtras();
            int pos = bundle.getInt(DiySelectItemAct.POS);
            ArrayList<SetItemModel.DataBean.ListBean> list = (ArrayList<SetItemModel.DataBean.ListBean>) bundle.getSerializable(DiySelectItemAct.DATA);
            adapter.addSelectItems(pos, list);

        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (adapter != null) {
            adapter.stopSubscription();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImageDiyFrameLayout.isReverse = false;
    }

}
