package com.jajahome.network;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/3/24.
 */
public class SetItemPos implements Serializable {

    private int item_id;
    private int pos_id;

    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public int getPos_id() {
        return pos_id;
    }

    public void setPos_id(int pos_id) {
        this.pos_id = pos_id;
    }
}
