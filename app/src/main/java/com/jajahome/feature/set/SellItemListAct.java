package com.jajahome.feature.set;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jajahome.R;
import com.jajahome.base.BaseActivity;
import com.jajahome.constant.Constant;
import com.jajahome.feature.set.fragment.adapter.SetSellListAdapter;
import com.jajahome.feature.user.activity.LoginAct;
import com.jajahome.feature.user.activity.ShoppingCartAct;
import com.jajahome.model.LoginModle;
import com.jajahome.model.SellItemListModel;
import com.jajahome.model.SetDiyPriceModel;
import com.jajahome.network.ApiImp;
import com.jajahome.network.SetListReq;
import com.jajahome.util.Base64Helper;
import com.jajahome.util.LoginUtil;
import com.jajahome.widget.recyclerview.MultiRecycleView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 销售清单
 */
public class SellItemListAct extends BaseActivity {
    public final static String SET_MODEL = "set_model";
    public final static String ID = "ID";
    @BindView(R.id.ibtn_back)
    ImageButton ibtnBack;
    @BindView(R.id.textView2)
    TextView textView2;
    @BindView(R.id.recyclerView)
    MultiRecycleView recyclerView;
    @BindView(R.id.bottom_line)
    View bottomView;
    @BindView(R.id.btn_add_to_shopping_cart)
    Button btnAddToShoppingCart;
    @BindView(R.id.btn_buy_now)
    Button btnBuyNow;
    private String mId;

    private List<SetDiyPriceModel.ItemsBean> listItems;
    private SellItemListModel sellItemListModel;

    @Override
    protected int getViewId() {
        return R.layout.act_sell_item_list;
    }

    @Override
    protected void initEvent() {
        listItems = (List<SetDiyPriceModel.ItemsBean>) getIntent().getSerializableExtra(SET_MODEL);
        mId = getIntent().getStringExtra(ID);
        initViewController(recyclerView);
        textView2.setText(R.string.sell_list);
        ibtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        recyclerView.setLoadMoreable(false);
        recyclerView.setRefreshEnable(false);
        getList();
    }

    protected void initView(final SellItemListModel sellItemListModel) {
        SetSellListAdapter adapter = new SetSellListAdapter();
        List<SellItemListModel.DataBean.ItemListBean> listHad = sellItemListModel.getData().getItem_list();
        int totalCount = 0;
        int priceMin = 0;
        int priceMax = 0;
        List<SellItemListModel.DataBean.ItemListBean> list = new ArrayList<>();
        List<SetDiyPriceModel.ItemsBean> listIds = new ArrayList<>();
        int size = listHad.size();
        for (int i = 0; i < size; i++) {
            SellItemListModel.DataBean.ItemListBean bean = listHad.get(i);
            SellItemListModel.DataBean.ItemListBean.InfoBean infoBean = bean.getInfo();

            if (infoBean.getPay_state() == 1) {  //这个字段中0：非卖品、1：销售品、2：赠品。
                priceMin = infoBean.getPrice_basic().getMin() * infoBean.getCount() + priceMin;
                priceMax = infoBean.getPrice_basic().getMax() * infoBean.getCount() + priceMax;
            }
            //这个字段中0：非卖品、1：销售品、2：赠品。
            if (infoBean.getPay_state() != 0) {
                list.add(bean);
                listIds.add(listItems.get(i));
                totalCount = totalCount + infoBean.getCount();
            }
        }
        if (list.size() == 0) {
            bottomView.setVisibility(View.INVISIBLE);
            showEmpty(true, "未设定销售品", null);
        } else {
            recyclerView.setAdapter(adapter);
            adapter.setListItems(listIds);
            adapter.resetItems(list);

            View view = LayoutInflater.from(this).inflate(R.layout.act_sell_list, null);
            TextView tvTotalNums = (TextView) view.findViewById(R.id.tv_nums);
            TextView tvTotalPrices = (TextView) view.findViewById(R.id.tv_total_price);

            tvTotalNums.setText("共" + totalCount + "件");
            tvTotalPrices.setText("总计" + Constant.YUAN + priceMax);
            recyclerView.addFooterView(view);
        }


        //立即购买
        btnBuyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sellItemListModel == null) return;
                LoginModle loginModle = LoginUtil.getInfo(mContext);
                if (loginModle == null) {
                    gotoNewAty(LoginAct.class);
                } else {
                    ShoppingCartAct.gotoBuy(mContext, Constant.TZ_PRICE + getBuyOrShopCardUrl(loginModle, 2));
                }
            }
        });

        //添加购物车
        btnAddToShoppingCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sellItemListModel == null) return;
                LoginModle loginModle = LoginUtil.getInfo(mContext);
                if (loginModle == null) {
                    gotoNewAty(LoginAct.class);
                } else {
                    ShoppingCartAct.addToShoppingCart(mContext, Constant.TZ_PRICE + getBuyOrShopCardUrl(loginModle, 1));
                }
            }
        });

    }

    private String getBuyOrShopCardUrl(LoginModle loginModle, int i) {
        SetDiyPriceModel model = new SetDiyPriceModel();
        //1：套装，2：DIY
        model.setSet_type(1);
        //1加入购物车，2直接购买
        model.setType(i);
        //套装或DIY的id
        model.setId(mId);
        //用户信息
        model.setUser_id(loginModle.getData().getUser().getId()); //
        model.setUser_token(loginModle.getData().getSession());
        model.setItems(getItems());
        String json = new Gson().toJson(model);
        Log.e("json", json);
        return Base64Helper.encodeStringToBase64(json);
    }

    /**
     * 获取 套装信息 （购买 加入购物车
     *
     * @return
     */
    private List<SetDiyPriceModel.ItemsBean> getItems() {
        List<SetDiyPriceModel.ItemsBean> list = new ArrayList<>();
        List<SellItemListModel.DataBean.ItemListBean> listHad = sellItemListModel.getData().getItem_list();
        for (int i = 0; i < listHad.size(); i++) {
            SellItemListModel.DataBean.ItemListBean itemInfoBean = listHad.get(i);
            SellItemListModel.DataBean.ItemListBean.InfoBean infoBean = itemInfoBean.getInfo();
            if (infoBean.getPay_state() == 1) {

                SetDiyPriceModel.ItemsBean itembean = new SetDiyPriceModel.ItemsBean();
                itembean.setId(infoBean.getId());
                itembean.setFab_id(infoBean.getFabric());
                itembean.setMat_id(infoBean.getMaterial());
                list.add(itembean);
            }
        }
        //排过序的
        return list;
    }


    /**
     * 获取套装详情信息
     */
    private void getList() {
        SetListReq req = new SetListReq();
        req.setCmd(Constant.SET_ITEM_lIST);
        SetListReq.ContentBean bean = new SetListReq.ContentBean();
        bean.setItem_id_list(getIds());
        req.setContent(bean);
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(req));
        mSubscription = ApiImp.get().getSellItemList(requestBody).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<SellItemListModel>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(SellItemListModel model) {
                sellItemListModel = model;
                initView(model);
            }
        });
    }

    private List<Integer> getIds() {
        List<Integer> list = new ArrayList<>();
        if (listItems == null) return list;
        for (SetDiyPriceModel.ItemsBean itemsBean : listItems) {
            list.add(Integer.valueOf(itemsBean.getId()));
        }
        return list;
    }

}
