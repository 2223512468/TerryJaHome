package com.jajahome.feature.user.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jajahome.R;
import com.jajahome.base.BaseActivity;
import com.jajahome.base.BaseRecyclerAdapter;
import com.jajahome.constant.Constant;
import com.jajahome.feature.user.adapter.AddressAdapter;
import com.jajahome.feature.view.LoginDialog;
import com.jajahome.model.AddressModel;
import com.jajahome.network.ApiImp;
import com.jajahome.network.BaseReq;
import com.jajahome.util.LoginUtil;
import com.jajahome.util.T;
import com.jajahome.widget.recyclerview.MultiRecycleView;

import java.util.List;

import butterknife.BindView;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/8/21.
 */
public class DefaultAct extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.lv_address)
    MultiRecycleView recycleView;
    @BindView(R.id.manager_tv)
    TextView textView;
    @BindView(R.id.ibtn_back)
    ImageButton imgBack;


    private Button btnAdd;
    private AddressAdapter mAdapter;
    private List<AddressModel.AddressBean> address;


    @Override
    protected int getViewId() {
        return R.layout.act_default_address;
    }

    @Override
    protected void initEvent() {

        initViewController(recycleView);
        mAdapter = new AddressAdapter();
        recycleView.setAdapter(mAdapter);

        mAdapter.setmLongListener(new BaseRecyclerAdapter.OnItemLongClickListener() {
            @Override
            public void OnItemLongClick(final int id, final int index) {
                LoginDialog.Builder builder = new LoginDialog.Builder(mContext);
                builder.setMessage("是否删除地址?");
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.i("TAG", which + "");
                        dialog.dismiss();
                        deleteItem(index);

                    }
                });
                builder.create().show();
            }
        });

        /**
         * 单个条目点击
         */
        mAdapter.setOnItemOnListener(new BaseRecyclerAdapter.OnItemClickedListener() {
            @Override
            public void onItemClicked(int id, int index) {
                Intent intent = new Intent();

                intent.putExtra("id", address.get(index).getId());
                intent.putExtra("area", address.get(index).getArea());
                intent.putExtra("address", address.get(index).getDetail_address());
                intent.putExtra("mobile", address.get(index).getMobile());
                intent.putExtra("name", address.get(index).getName());
                intent.putExtra("tel", address.get(index).getTel());
                intent.putExtra("type", address.get(index).getType());
                intent.putExtra("postcode", address.get(index).getPostcode());

                setResult(2, intent);
                finish();

            }
        });


        setFooter();
        setListener();

    }

    @Override
    protected void onResume() {
        super.onResume();
        getAddress();
    }

    /**
     * 获取地址列表
     */
    private void getAddress() {
        BaseReq baseReq = new BaseReq();
        baseReq.setCmd(Constant.ADDRESSLIST);
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(baseReq));
        RequestBody requestBodyl = RequestBody.create(MediaType.parse("application/session"), gson.toJson(LoginUtil.getInfo(mContext).getData().getSession()));
        mSubscription = ApiImp.get().getAddress(requestBody, requestBodyl)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AddressModel>() {

                    @Override
                    public void onCompleted() {
                        recycleView.setRefreshEnable(false);
                        recycleView.setLoadMoreable(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(AddressModel addressModel) {
                        if (addressModel.getStatus() == 0) {
                            address = addressModel.getAddress();
                            mAdapter.resetItems(address);
                        }
                    }
                });
    }

    /**
     * 删除地址
     *
     * @param i
     */
    private void deleteItem(final int i) {
        BaseReq baseReq = new BaseReq();
        baseReq.setCmd(Constant.DEL_ADDRESS);
        BaseReq.ContentBean contentBean = new BaseReq.ContentBean();
        contentBean.setId(Integer.parseInt(address.get(i).getId()));
        baseReq.setContent(contentBean);
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(baseReq));
        RequestBody requestBodyl = RequestBody.create(MediaType.parse("application/session"), gson.toJson(LoginUtil.getInfo(mContext).getData().getSession()));
        showProgressDialog("正在删除");
        mSubscription = ApiImp.get().deleteAddress(requestBody, requestBodyl)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AddressModel>() {
                    @Override
                    public void onCompleted() {
                        dissmisProgressDialog();
                        T.showShort(DefaultAct.this, "删除成功");
                    }

                    @Override
                    public void onError(Throwable e) {
                        dissmisProgressDialog();
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(AddressModel addressModel) {
                        if (addressModel.getStatus() == 0) {
                            address = addressModel.getAddress();
                            mAdapter.remove(i);
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    private void setListener() {
        textView.setOnClickListener(this);
        imgBack.setOnClickListener(this);
    }


    private void setFooter() {
        View footer = LayoutInflater.from(mContext).inflate(R.layout.act_my_address_header, null);
        btnAdd = (Button) footer.findViewById(R.id.btn_add);
        btnAdd.setOnClickListener(this);
        recycleView.addFooterView(footer);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_add:
                if (address != null && address.size() < 5) {
                    gotoNewAty(AddressAct.class);
                } else {
                    T.showShort(DefaultAct.this, "收货地址最多只能添加5条");
                }
                break;

            case R.id.manager_tv:
                gotoNewAty(MyAddressAct.class);
                break;
            case R.id.ibtn_back:
                finish();
                break;
        }
    }
}
