package com.jajahome.feature.user.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jajahome.R;
import com.jajahome.base.BaseActivity;
import com.jajahome.base.BaseRecyclerAdapter;
import com.jajahome.constant.Constant;
import com.jajahome.feature.user.adapter.AddressAdapter;
import com.jajahome.feature.view.LoginDialog;
import com.jajahome.model.AddressModel;
import com.jajahome.model.LoginModle;
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
 * 我的收货地址
 * Created by laotang on 2016/7/20.
 */
public class MyAddressAct extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.ibtn_back)
    ImageButton ibtnBack;
    @BindView(R.id.textView2)
    TextView textView2;
    @BindView(R.id.rl_address)
    RelativeLayout rlAddress;
    @BindView(R.id.view)
    View view;
    @BindView(R.id.lv_address)
    MultiRecycleView recycleView;
    /**
     * 地址列表选择器
     */
    private AddressAdapter mAdapter;
    private Button btnAdd;
    /**
     * 存放地址集合
     */
    private List<AddressModel.AddressBean> address;

    @Override
    protected int getViewId() {
        return R.layout.act_my_address;
    }

    @Override
    protected void onResume() {
        super.onResume();
        getAddress();
    }


    @Override
    protected void initEvent() {

        ibtnBack.setOnClickListener(this);

        mAdapter = new AddressAdapter();
        recycleView.setAdapter(mAdapter);
        setFooter();
        /**
         * 长按删除地址弹出窗
         */
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
                        List<AddressModel.AddressBean> dataList = mAdapter.getDataList();
                        if (dataList.get(index).getType().equals("1")) {
                            Toast.makeText(mContext, "默认地址不能删除", Toast.LENGTH_SHORT).show();
                        } else {
                            deleteItem(index);
                        }
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
                Intent intent = new Intent(mContext, AddressAct.class);

                intent.putExtra("id", address.get(index).getId());
                intent.putExtra("area", address.get(index).getArea());
                intent.putExtra("address", address.get(index).getDetail_address());
                intent.putExtra("mobile", address.get(index).getMobile());
                intent.putExtra("name", address.get(index).getName());
                intent.putExtra("tel", address.get(index).getTel());
                intent.putExtra("type", address.get(index).getType());
                intent.putExtra("postcode", address.get(index).getPostcode());

                startActivity(intent);

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
                        T.showShort(MyAddressAct.this, "删除成功");
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

    /**
     * 获取地址列表
     */
    private void getAddress() {
        BaseReq baseReq = new BaseReq();
        baseReq.setCmd(Constant.ADDRESSLIST);
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(baseReq));
        LoginModle loginModle = LoginUtil.getInfo(mContext);
        RequestBody requestBodyl;
        if (loginModle != null) {
            requestBodyl = RequestBody.create(MediaType.parse("application/session"), gson.toJson(loginModle.getData().getSession()));
        } else {
            requestBodyl = RequestBody.create(MediaType.parse("application/session"), "");
        }
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
                        if (addressModel != null && addressModel.getStatus() == 0) {
                            address = addressModel.getAddress();
                            mAdapter.resetItems(address);
                        }
                    }
                });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibtn_back:
                /**
                 * 关闭当前界面
                 */
                finish();
                break;
            case R.id.btn_add:
                /**
                 * 跳转到地址页面
                 */
                if (address != null && address.size() < 5) {
                    gotoNewAty(AddressAct.class);
                } else {
                    T.showShort(MyAddressAct.this, "收货地址最多只能添加5条");
                }
                break;
        }
    }

    private void setFooter() {
        View footer = LayoutInflater.from(mContext).inflate(R.layout.act_my_address_header, null);
        btnAdd = (Button) footer.findViewById(R.id.btn_add);
        btnAdd.setOnClickListener(this);
        recycleView.addFooterView(footer);
    }
}
