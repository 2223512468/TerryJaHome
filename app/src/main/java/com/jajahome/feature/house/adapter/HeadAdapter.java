package com.jajahome.feature.house.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jajahome.R;
import com.jajahome.base.BaseListHomeAdapter;
import com.jajahome.feature.house.HouseListAct;
import com.jajahome.feature.view.LoginDialog;
import com.jajahome.model.HouseModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${Terry} on 2017/10/19.
 */
public class HeadAdapter extends BaseListHomeAdapter<HouseModel.DataBean.HouseBeanX> {

    private List<HouseModel.DataBean.HouseBeanX> list = new ArrayList<>();
    private OnItemLongClickListener listener;

    public void setListener(OnItemLongClickListener listener) {
        this.listener = listener;
    }

    public HeadAdapter(Context context, List<HouseModel.DataBean.HouseBeanX> list) {
        super(context, list);
        this.list.clear();
        for (HouseModel.DataBean.HouseBeanX bean : list) {
            this.list.add(bean);
        }
        this.notifyDataSetChanged();
    }

    public void reset(List<HouseModel.DataBean.HouseBeanX> list) {
        this.list.clear();
        for (HouseModel.DataBean.HouseBeanX bean : list) {
            this.list.add(bean);
        }
        this.notifyDataSetChanged();
    }

    @Override
    public View initView(final HouseModel.DataBean.HouseBeanX houseBean, View convertView, final ViewGroup parent, int position) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.act_sort_city_item_item, null);
            holder.collectHouse = (TextView) convertView.findViewById(R.id.cityItem);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.collectHouse.setText(houseBean.getBuildings().getName() + "/" + houseBean.getHouse().getTitle());
        holder.collectHouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(parent.getContext(), HouseListAct.class);
                intent.putExtra(HouseListAct.TITLE, houseBean.getHouse().getTitle());
                intent.putExtra(HouseListAct.ID, houseBean.getBuildings().getId());
                parent.getContext().startActivity(intent);
            }
        });

        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (listener != null) {
                    LoginDialog.Builder builder = new LoginDialog.Builder(parent.getContext());
                    builder.setMessage("是否删除收藏?");
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            listener.delete(houseBean.getHouse().getId());

                        }
                    });
                    builder.create().show();

                }
                return true;
            }
        });
        return convertView;
    }


    class ViewHolder {
        TextView collectHouse;
    }

    public interface OnItemLongClickListener {
        void delete(String id);
    }

}
