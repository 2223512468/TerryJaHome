package com.jajahome.feature.user.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.google.gson.Gson;
import com.jajahome.R;
import com.jajahome.base.BaseActivity;
import com.jajahome.constant.Constant;
import com.jajahome.event.EventMessage;
import com.jajahome.feature.H5Act;
import com.jajahome.feature.user.adapter.DesignApplySelectPhotoAdapter;
import com.jajahome.model.AreaModel;
import com.jajahome.model.AvatarModel;
import com.jajahome.model.LoginModle;
import com.jajahome.network.ApiImp;
import com.jajahome.network.DesignerReq;
import com.jajahome.network.HttpUtil;
import com.jajahome.util.Base64Helper;
import com.jajahome.util.IdCheckUtil;
import com.jajahome.util.LoginUtil;
import com.jajahome.util.StringUtil;
import com.jajahome.util.T;
import com.jajahome.widget.AutoListView;
import com.wq.photo.widget.PickConfig;

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
 * 申请设计师
 */
public class DesignerApplyAct extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.ibtn_back)
    ImageButton ibtnBack;
    @BindView(R.id.textView2)
    TextView textView2;
    @BindView(R.id.ibtn_news)
    ImageButton ibtnNews;
    @BindView(R.id.edit_avatar)
    EditText editAvatar;
    @BindView(R.id.edit_identify)
    EditText editIdentify;
    @BindView(R.id.edit_company)
    EditText editCompany;
    @BindView(R.id.edit_address)
    TextView editAddress;
    @BindView(R.id.view_address)
    LinearLayout viewAddress;
    @BindView(R.id.tv_designer_agreement)
    TextView tvDesignerAgreement;
    @BindView(R.id.btn_send)
    Button btnSend;
    @BindView(R.id.list_view)
    AutoListView listView;
    @BindView(R.id.checkbox)
    CheckBox checkbox;
    @BindView(R.id.edit_invite)
    EditText inviteNo;
    @BindView(R.id.invite_no_ll)
    LinearLayout inviteLL;
    @BindView(R.id.invite)
    TextView inviteIntroduce;


    /**
     * 是否勾选设计师协议，默认勾选，反之
     */
    private boolean isAgress = true;
    /**
     * 设计师申请，图片选择器
     */
    private DesignApplySelectPhotoAdapter mAdapter;
    /**
     * 设置选择器
     */
    private OptionsPickerView pvOptions;
    /**
     * 存放省级集合
     */
    private ArrayList<String> options1Items = new ArrayList<>();
    /**
     * 存放市区级集合
     */
    private ArrayList<List<String>> options2Items = new ArrayList<>();

    private String address;
    private LoginModle info;
    private Boolean isHasInviteNo = false;


    @Override
    protected int getViewId() {
        return R.layout.act_designer_apply;
    }

    @Override
    protected void initEvent() {
        ibtnBack.setOnClickListener(this);
        tvDesignerAgreement.setOnClickListener(this);
        viewAddress.setOnClickListener(this);
        btnSend.setOnClickListener(this);
        mAdapter = new DesignApplySelectPhotoAdapter(mContext);
        listView.setAdapter(mAdapter);
        pvOptions = new OptionsPickerView(this);
        getAreaData();
        isHasInviteNo = isInviteNo();
        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isAgress = isChecked;
            }
        });
    }

    //判断自已是否有邀请码

    private boolean isInviteNo() {
        info = LoginUtil.getInfo(mContext);
        if (info!=null&&info.getData()!=null&&!StringUtil.isEmpty(info.getData().getUser().getInvitecode())) {
            inviteLL.setVisibility(View.GONE);
            inviteIntroduce.setVisibility(View.GONE);
            return true;
        } else {
            inviteLL.setVisibility(View.VISIBLE);
            inviteIntroduce.setVisibility(View.VISIBLE);
            return false;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == PickConfig.PICK_REQUEST_CODE) {
            //在data中返回 选择的图片列表
            ArrayList<String> paths = data.getStringArrayListExtra("data");
            String path = paths.get(0);
            mAdapter.setPath(path);
        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibtn_back:
                /**
                 * 关闭当前页面
                 */
                finish();
                break;
            case R.id.view_address:
                /**
                 * 显示选择器
                 */
                pvOptions.show();
                break;
            case R.id.tv_designer_agreement:
                /**
                 * 跳转设计师协议h5页面
                 */
                H5Act.gotoH5(mContext, Constant.DESIGNER_REGISTER, "设计师协议");
                break;
            case R.id.btn_send:
                /**
                 * 申请设计师
                 */
                apply();
                break;
        }
    }

    /**
     * 提交申请设计师
     */

    private void apply() {

        if (!isAgress) {
            showToast(R.string.pls_agress_desiner);
            return;
        }
        String name = editAvatar.getText().toString();
        if (StringUtil.isEmpty(name)) {
            showToast(R.string.input_name);
            return;
        }
        String cardId = editIdentify.getText().toString();
        if (StringUtil.isEmpty(cardId)) {
            showToast(R.string.input_identify);
            return;
        }

        if (!IdCheckUtil.IDCardValidate(cardId)) {
            showToast("请输入正确的身份证号码");
            return;
        }
        String company = editCompany.getText().toString();
        if (StringUtil.isEmpty(company)) {
            showToast(R.string.input_company);
            return;
        }
        if (StringUtil.isEmpty(address)) {
            showToast(R.string.input_address);
            return;
        }
        String[] urls = mAdapter.getmUrls();
        if (StringUtil.isEmpty(urls[0])) {
            showToast(R.string.tv_designer_commit);
            return;
        }
        if (StringUtil.isEmpty(urls[1])) {
            showToast(R.string.tv_designer_commit_no);
            return;
        }

        String inviteNum = null;
        if (isHasInviteNo == false) {
            inviteNum = inviteNo.getText().toString();
            if (StringUtil.isEmpty(inviteNum)) {
                showToast(R.string.input_invite);
                return;
            }
        }

        DesignerReq baseReq = new DesignerReq();
        baseReq.setCmd(Constant.DESIGNER_REG);
        DesignerReq.ContentBean contentBean = new DesignerReq.ContentBean();
        contentBean.setRealname(name);
        contentBean.setCompany(company);
        contentBean.setCard_id(cardId);
        contentBean.setInvite_code(inviteNum);
        contentBean.setAddress(address);
        contentBean.setImage_idcard_a(Base64Helper.encodeBase64File1(urls[0]));
        contentBean.setImage_idcard_b(Base64Helper.encodeBase64File1(urls[1]));
        if (!StringUtil.isEmpty(urls[2])) {
            contentBean.setImage_bcard(Base64Helper.encodeBase64File1(urls[2]));
        }
        baseReq.setContent(contentBean);
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(baseReq));
        showProgressDialog("申请中");
        ApiImp.get().sendAvatar(requestBody, HttpUtil.getSession(mContext))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AvatarModel>() {
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
                    public void onNext(AvatarModel avatarModel) {
                        if (avatarModel.getStatus() == 0) {
                            T.showShort(mContext, "申请成功");
                            EventBus.getDefault().post(new EventMessage(UserAct.CHANGE_ACTION, ""));
                            finish();
                        } else {
                            T.showShort(mContext, avatarModel.getMessage());
                        }
                    }
                });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == DesignApplySelectPhotoAdapter.PERMISSON_STORGE) {
            if (grantResults.length == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                mAdapter.selectPhoto();
            } else {
                showToast("权限被禁止，无法选取图片");
            }
        }
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

                    }

                    @Override
                    public void onNext(AreaModel areaModel) {

                        for (int i = 0; i < areaModel.getData().getArea().size(); i++) {
                            options1Items.add(i, areaModel.getData().getArea().get(i).getProvince());
                            ArrayList<String> list = new ArrayList<>();
                            AreaModel.DataBean.AreaBean areaBean = areaModel.getData().getArea().get(i);
                            if (areaBean.getCity().size() == 0) {
                                list.add("");
                            } else {
                            /*    List<String> city = areaModel.getData().getArea().get(i).getCity();
                                for (int j = 0; j < city.size(); j++) {
                                    list.add(areaModel.getData().getArea().get(i).getCity().get(j));
                                }*/
//                               list.add(areaModel.getData().getArea().get(i).getCity().);

                                for (int j = 0; j < areaModel.getData().getArea().get(i).getCity().size(); j++) {
                                    list.add(areaModel.getData().getArea().get(i).getCity().get(j).getCity());
                                }

                            }


                            options2Items.add(i, list);
                        }


                        pvOptions.setPicker(options1Items, options2Items, true);
                        pvOptions.setTitle("选择城市");
                        pvOptions.setCyclic(false, false, true);
                        pvOptions.setSelectOptions(0, 0);
                        pvOptions.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {

                            @Override
                            public void onOptionsSelect(int options1, int option2, int options3) {

                            }

                            @Override
                            public void onOptionsSelect(int options1, int option2) {
                                //返回的分别是三个级别的选中位置
                                String tx = options1Items.get(options1) + " "
                                        + options2Items.get(options1).get(option2);
                                address = tx;
                                editAddress.setText(tx);
                            }
                        });
                    }
                });
    }
}
