package com.jajahome.feature.diy;

import android.graphics.drawable.GradientDrawable;
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
import com.jajahome.event.EventMessage;
import com.jajahome.feature.diy.adapter.DiySellListAdapter;
import com.jajahome.feature.user.activity.LoginAct;
import com.jajahome.feature.user.activity.ShoppingCartAct;
import com.jajahome.model.LoginModle;
import com.jajahome.model.SellItemListModel;
import com.jajahome.model.SetDiyPriceModel;
import com.jajahome.network.ApiImp;
import com.jajahome.network.DiyListReq;
import com.jajahome.network.HttpUtil;
import com.jajahome.network.SetItemPos;
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
 * Created by Administrator on 2017/8/29.
 */
public class DiySellItemListAct extends BaseActivity {

    public final static int DIY_SELL0 = 0X100;
    public final static int DIY_SELL = 0X123;
    public final static int DIY_SELL1 = 0X125;
    public final static String DIY_MODEL = "diy_model";
    public final static String ID = "ID";
    public final static String MID = "MID";
    public final static String IDLIST = "IDLIST";
    private SetItemPos id_list[];
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
    private GradientDrawable gd = new GradientDrawable();
    private SellItemListModel sellItemListModel;
    private int diy_type;
    private List<SetDiyPriceModel.ItemsBean> listItems;
    private String mId;//单品id
    private TextView tvTotalNums, tvTotalPrices;
    private List<SetItemPos> setItemPosList;

    @Override
    protected int getViewId() {
        return R.layout.act_sell_item_list;
    }

    @Override
    protected void initEvent() {
        //id_list = DiyDetailAct.item_ids;

        setItemPosList = (List<SetItemPos>) getIntent().getSerializableExtra(IDLIST);
        diy_type = getIntent().getIntExtra(ID, 0);
        mId = getIntent().getStringExtra(MID);
        listItems = (List<SetDiyPriceModel.ItemsBean>) getIntent().getSerializableExtra(DIY_MODEL);

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


    public void getList() {
        DiyListReq req = new DiyListReq();
        req.setCmd(Constant.DIY_ITEM_LIST);
        DiyListReq.ContentBean bean = new DiyListReq.ContentBean();
        bean.setId_list(setItemPosList);
        bean.setDiy_type(diy_type);
        req.setContent(bean);
        final Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(req));
        mSubscription = ApiImp.get().getDiySellItemList(requestBody, HttpUtil.getSession(this)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<SellItemListModel>() {
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

    private DiySellListAdapter adapter;

    protected void initView(final SellItemListModel sellItemListModel) {
        adapter = new DiySellListAdapter();
        List<SellItemListModel.DataBean.ItemListBean> listHad = sellItemListModel.getData().getItem_list();
        int totalCount = 0;
        //   int priceMin = 0;
        //   int priceMax = 0;
        //   int totalPirce = 0;
        List<SellItemListModel.DataBean.ItemListBean> list = new ArrayList<>();
        List<SetDiyPriceModel.ItemsBean> listIds = new ArrayList<>();
        int size = listHad.size();
        for (int i = 0; i < size; i++) {
            SellItemListModel.DataBean.ItemListBean bean = listHad.get(i);
            SellItemListModel.DataBean.ItemListBean.InfoBean infoBean = bean.getInfo();

            if (infoBean.getPay_state() == 1) {  //这个字段中0：非卖品、1：销售品、2：赠品。
                priceMin = infoBean.getPrice_basic().getMin() * infoBean.getCount() + priceMin;
                priceMax = infoBean.getPrice_basic().getMax() * infoBean.getCount() + priceMax;
                totalPirce += infoBean.getPrice_basic().getMax();
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
            adapter.addListItems(list);

            View view = LayoutInflater.from(this).inflate(R.layout.act_sell_list, null);
            tvTotalNums = (TextView) view.findViewById(R.id.tv_nums);
            tvTotalPrices = (TextView) view.findViewById(R.id.tv_total_price);

            tvTotalNums.setText("共" + list.size() + "件");
            tvTotalPrices.setText("总计 " + Constant.YUAN + totalPirce);
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
                    ShoppingCartAct.gotoBuy(mContext, Constant.DIY_PRICE + getBuyOrShopCardUrl(loginModle, 2));
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
                    ShoppingCartAct.addToShoppingCart(mContext, Constant.DIY_PRICE + getBuyOrShopCardUrl(loginModle, 1));
                }
            }
        });

    }

    private String getBuyOrShopCardUrl(LoginModle loginModle, int i) {
        SetDiyPriceModel model = new SetDiyPriceModel();
        //1：套装，2：DIY
        model.setSet_type(2);
        //1加入购物车，2直接购买
        model.setType(i);
        //套装或DIY的id
        model.setId(mId);
        //用户信息
        model.setUser_id(loginModle.getData().getUser().getId()); //
        model.setUser_token(loginModle.getData().getSession());
        model.setItems(adapter.getItems());
        String json = new Gson().toJson(model);
        Log.e("json", json);
        return Base64Helper.encodeStringToBase64(json);
    }

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

    @Override
    public void onEventMainThread(EventMessage event) {
        super.onEventMainThread(event);
        if (event.action == DIY_SELL0) {
            List<SellItemListModel.DataBean.ItemListBean> listItem = adapter.getListItem();
            refreshPrice(listItem);
        }
    }

    int priceMin;
    int priceMax;
    int totalPirce;

    private void refreshPrice(List<SellItemListModel.DataBean.ItemListBean> listItem) {

        totalPirce = 0;
        for (int i = 0; i < listItem.size(); i++) {
            SellItemListModel.DataBean.ItemListBean.InfoBean infoBean = listItem.get(i).getInfo();
            if (infoBean.getPay_state() == 1) {
                priceMin = infoBean.getPrice_basic().getMin() * infoBean.getCount() + priceMin;
                priceMax = infoBean.getPrice_basic().getMax() * infoBean.getCount() + priceMax;
                totalPirce += infoBean.getPrice_basic().getMax();
            }
        }
        tvTotalNums.setText("共" + listItem.size() + "件");
        tvTotalPrices.setText("总计 " + Constant.YUAN + totalPirce);
    }

}
