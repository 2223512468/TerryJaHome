package com.jajahome.feature.user.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.google.gson.Gson;
import com.jajahome.R;
import com.jajahome.base.BaseActivity;
import com.jajahome.constant.Constant;
import com.jajahome.event.EventMessage;
import com.jajahome.model.AreaModel;
import com.jajahome.model.SaveAddressModel;
import com.jajahome.network.ApiImp;
import com.jajahome.network.BaseReq;
import com.jajahome.network.HttpUtil;
import com.jajahome.util.CommonUtils;
import com.jajahome.util.LoginUtil;
import com.jajahome.util.StringHelper;
import com.jajahome.util.StringUtil;
import com.jajahome.util.T;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 编辑地址
 * Created by laotang on 2016/7/20.
 */
public class AddressAct extends BaseActivity implements View.OnClickListener {
    /**
     * 存放省会集合
     */
    private ArrayList<String> options1Items = new ArrayList<>();
    /**
     * 存放市区集合
     */
    private ArrayList<List<String>> options2Items = new ArrayList<>();


    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();


    /**
     * 地区选择器
     */
    private OptionsPickerView pvOptions;
    @BindView(R.id.ibtn_back)
    ImageButton ibtnBack;
    @BindView(R.id.textView2)
    TextView textView2;
    @BindView(R.id.rl_address)
    RelativeLayout rlAddress;
    @BindView(R.id.ll_edit)
    LinearLayout llEdit;
    @BindView(R.id.edit_area)
    EditText editArea;
    @BindView(R.id.btn_sava)
    Button btnSava;
    @BindView(R.id.edit_detail_address)
    EditText editDetailAddress;
    @BindView(R.id.edit_postcode)
    EditText editPostcode;
    @BindView(R.id.edit_name)
    EditText editName;
    @BindView(R.id.edit_phone)
    EditText editPhone;
    @BindView(R.id.edit_telephone)
    EditText editTelephone;
    @BindView(R.id.checkbox)
    CheckBox checkbox;

    private String area;
    private String detailAddress;
    private String postCode;
    private String name;
    private String phone;
    private String telePhone;
    int type;
    private String id;

    @Override
    protected int getViewId() {
        return R.layout.act_address;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        editPhone.setText(intent.getStringExtra("mobile"));
        editTelephone.setText(intent.getStringExtra("tel"));
        editPostcode.setText(intent.getStringExtra("postcode"));
        editArea.setText(intent.getStringExtra("area"));
        editDetailAddress.setText(intent.getStringExtra("address"));
        editName.setText(intent.getStringExtra("name"));
        String type = intent.getStringExtra("type");
        id = intent.getStringExtra("id");
        if ("1".equals(type)) {
            checkbox.setChecked(true);
        }
    }

    @Override
    protected void initEvent() {
        getAreaData();
        ibtnBack.setOnClickListener(this);
        editArea.setOnClickListener(this);
        btnSava.setOnClickListener(this);
        /**
         * 设置选择器
         */
        pvOptions = new OptionsPickerView(this);
        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    type = 1;
                } else {
                    type = 0;
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
            case R.id.edit_area:
                /**
                 * 显示选择器
                 */
                pvOptions.show();
                break;
            case R.id.btn_sava:
                /**
                 * 保存地址
                 */
                if (isCheckInput()) {
                    SaveAddress();
                }
                break;
        }
    }


    /**
     * 保存地址
     */
    private void SaveAddress() {
        BaseReq baseReq = new BaseReq();

        baseReq.setCmd(Constant.SAVE_ADDRESS);
        BaseReq.ContentBean.AddressBean addressBean = new BaseReq.ContentBean.AddressBean();
        addressBean.setMobile(phone);
        addressBean.setArea(area);
        addressBean.setDetail_address(detailAddress);
        addressBean.setPostcode(postCode);
        addressBean.setTel(telePhone);
        addressBean.setType(type);
        addressBean.setName(name);
        if (!StringUtil.isEmpty(id)) {
            addressBean.setId(id);
        }
        BaseReq.ContentBean contentBean = new BaseReq.ContentBean();
        contentBean.setAddress(addressBean);
        baseReq.setContent(contentBean);

        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(baseReq));
        RequestBody requestBodyl = RequestBody.create(MediaType.parse("application/session"), gson.toJson(LoginUtil.getInfo(mContext).getData().getSession()));
        showProgressDialog("正在保存");
        mSubscription = ApiImp.get().saveAddress(requestBody, requestBodyl)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SaveAddressModel>() {
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
                    public void onNext(SaveAddressModel saveAddressModel) {
                        if (saveAddressModel.getStatus() == 0) {
                            T.showShort(AddressAct.this, getString(R.string.save_success));
                            EventBus.getDefault().post(new EventMessage(ShoppingCartAct.RELOAD_ACTION, ""));
                            finish();
                        } else {
                            T.showShort(AddressAct.this, saveAddressModel.getMessage());
                        }
                    }
                });
    }

    /**
     * 对地址信息逐一判断
     *
     * @return
     */
    public boolean isCheckInput() {
        area = editArea.getText().toString().trim();
        detailAddress = editDetailAddress.getText().toString().trim();
        postCode = editPostcode.getText().toString().trim();
        name = editName.getText().toString().trim();
        phone = editPhone.getText().toString().trim();
        telePhone = editTelephone.getText().toString().trim();
        /**
         *验证地区
         */
        if (StringHelper.isEditTextEmpty(editArea)) {
            T.showShort(AddressAct.this, getString(R.string.toast_login_area_empty));
            return false;
        }
        /**
         * 验证详细地址
         */
        if (StringHelper.isEditTextEmpty(editDetailAddress)) {
            T.showShort(AddressAct.this, getString(R.string.toast_address_empty));
            return false;
        }

        /**
         * 验证姓名
         */
        if (StringHelper.isEditTextEmpty(editName)) {
            T.showShort(AddressAct.this, getString(R.string.toast_name_empty));
            return false;
        }
        /**
         * 验证手机号
         */
        if (StringHelper.isEditTextEmpty(editPhone)) {
            T.showShort(AddressAct.this, getString(R.string.toast_phone_empty));
            return false;
        }

        /**
         * 验证邮编
         */
        if (!StringHelper.isEditTextEmpty(editPostcode)) {
            if (!CommonUtils.checkPost(postCode)) {
                T.showShort(AddressAct.this, getString(R.string.toast_postCode_error));
                return false;
            }
        }

        /**
         * 验证手机号码的格式
         */
        if (!CommonUtils.isMobileNO(phone)) {
            T.showShort(AddressAct.this, getString(R.string.toast_login_phone_error));
            return false;
        }
        /**
         * 验证电话号码
         */
        if (!StringHelper.isEditTextEmpty(editTelephone)) {
            if (!CommonUtils.checkPhone(telePhone)) {
                T.showShort(AddressAct.this, getString(R.string.toast_login_telephone_error));
                return false;
            }
        }

        return true;
    }


    /**
     * 获取城市信息
     */
    private void getAreaData() {
        mSubscription = ApiImp.get().getArea(HttpUtil.getRequestBody(Constant.AREALIST_V2))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AreaModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        T.showShort(AddressAct.this, getString(R.string.userNewsOutof));
                    }

                    @Override
                    public void onNext(AreaModel areaModel) {

                        for (int i = 0; i < areaModel.getData().getArea().size(); i++) {
                            options1Items.add(i, areaModel.getData().getArea().get(i).getProvince());
                            ArrayList<String> cityList = new ArrayList<>();//该省的城市列表（第二级）
                            ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

                            AreaModel.DataBean.AreaBean areaBean = areaModel.getData().getArea().get(i);
                            if (areaBean.getCity().size() == 0) {
                                cityList.add("");
                                Province_AreaList.add(cityList);
                            } else {

                                for (int j = 0; j < areaBean.getCity().size(); j++) {//遍历该省份的所有城市
                                    cityList.add(areaBean.getCity().get(j).getCity() + "");
                                    List<AreaModel.DataBean.AreaBean.CityBean.CountyBean> county = areaBean.getCity().get(j).getCounty();
                                    ArrayList<String> countyList = new ArrayList<String>();//该城市的所有地区列表
                                    if (county.size() == 0) {
                                        countyList.add("");
                                    } else {
                                        for (int k = 0; k < county.size(); k++) {
                                            countyList.add(county.get(k).getName());//添加该城市所有地区数据
                                        }
                                    }
                                    Province_AreaList.add(j, countyList);
                                }

                            }
                            options2Items.add(i, cityList);
                            options3Items.add(i, Province_AreaList);
                        }

                        pvOptions.setPicker(options1Items, options2Items, options3Items, true);
                        pvOptions.setTitle("选择城市");
                        pvOptions.setCyclic(false, false, false);
                        pvOptions.setSelectOptions(0, 0, 0);
                        pvOptions.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {

                            @Override
                            public void onOptionsSelect(int options1, int option2, int option3) {
                                //返回的分别是三个级别的选中位置
                                String tx = options1Items.get(options1) + " "
                                        + options2Items.get(options1).get(option2) + " " + options3Items.get(options1).get(option2).get(option3);
                                ;
                                editArea.setText(tx);
                            }

                            @Override
                            public void onOptionsSelect(int options1, int option2) {

                            }

                        });
                    }
                });
    }
}
