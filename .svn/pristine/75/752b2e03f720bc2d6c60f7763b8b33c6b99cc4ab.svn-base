package com.jajahome.feature.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jajahome.R;
import com.jajahome.base.BaseListHomeAdapter;
import com.jajahome.constant.Constant;
import com.jajahome.feature.house.SelectCityAct;
import com.jajahome.feature.user.activity.LoginAct;
import com.jajahome.feature.user.activity.SignAct;
import com.jajahome.model.LoginModle;
import com.jajahome.util.LoginUtil;

import java.util.List;

/**
 * 类描述：首页套装筛选
 * 创建人：lhz
 * 创建时间：2016/7/8 15:07
 * 修改时间：2016/7/8 15:07
 * 修改备注：
 */
public class HomeGridAdapter extends BaseListHomeAdapter<HomeGridModel> {
    private OnGridClickListener listener;
    private Context context;
    private String userCityname;
    private boolean isSign;
    /**
     * 选择
     */
    String[] arrayFilters;

    public HomeGridAdapter(Context context, List<HomeGridModel> list, String userCityname) {
        super(context, list);
        this.context = context;
        this.userCityname = userCityname;
        arrayFilters = context.getResources().getStringArray(R.array.home_room_filter);


    }

    public void isHasMsg(boolean sign) {
        this.isSign = sign;

        notifyDataSetChanged();
    }

    /**
     * @param homeGridModel
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View initView(final HomeGridModel homeGridModel, View convertView, ViewGroup parent, int position) {
        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater mInflater = LayoutInflater.from(parent.getContext());
            convertView = mInflater.inflate(R.layout.item_home_grid, null);
            holder = new ViewHolder();
            holder.imgView = (ImageView) convertView.findViewById(R.id.img);
            holder.mLL = (RelativeLayout) convertView.findViewById(R.id.root_ll);
            holder.signMsg = (TextView) convertView.findViewById(R.id.sign_msg);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.imgView.setImageResource(homeGridModel.getImgResId());

        if (position == 8) {
            if (isSign == true) {
                holder.signMsg.setVisibility(View.GONE);
            } else if (isSign == false) {
                if (LoginUtil.isLogin(context)) {
                    holder.signMsg.setVisibility(View.VISIBLE);
                } else {
                    holder.signMsg.setVisibility(View.GONE);
                }
            } else {
                holder.signMsg.setVisibility(View.GONE);
            }
        }

        //被点击
        holder.mLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getImgResId() == homeGridModel.getImgResId()) {
                        if (i == 7) {//找我家
                            LoginModle loginModle = LoginUtil.getInfo(context);
                            if (loginModle == null) {
                                Intent intent = new Intent(context, LoginAct.class);
                                context.startActivity(intent);
                            } else {
                                Intent intent = new Intent(context, SelectCityAct.class);
                                if (userCityname != null) {
                                    intent.putExtra(Constant.LOCATION, userCityname);
                                }
                                context.startActivity(intent);
                            }

                        } else if (i == 8) { //完整家居
                            if (LoginUtil.isLogin(context)) {
                                Intent intent = new Intent(context, SignAct.class);
                                context.startActivity(intent);
                            } else {
                                Intent intent = new Intent(context, LoginAct.class);
                                context.startActivity(intent);
                            }
                        } else {  //套装空间
                            if (listener != null) {
                                listener.onGridClicked(arrayFilters[i]);
                            }
                        }

                        return;
                    }
                }

            }
        });
        return convertView;
    }

    /**
     * 设置监听
     *
     * @param listener
     */
    public void setListener(OnGridClickListener listener) {
        this.listener = listener;
    }

    class ViewHolder {
        ImageView imgView;
        RelativeLayout mLL;
        TextView signMsg;
    }

    public interface OnGridClickListener {
        void onGridClicked(String filter);

        void onBrandClked(String barnd);
    }
}
