package com.jajahome.model;

import java.io.Serializable;
import java.util.List;

/**
 *  套装 diy 购买加购物车model
 */
public class SetDiyPriceModel {


    /**
     * id : 23
     * set_type : 1
     * items : [{"id":950,"fab_id":0,"mat_id":2172},{"id":962,"fab_id":0,"mat_id":2173},{"id":951,"fab_id":0,"mat_id":2173},{"id":961,"fab_id":0,"mat_id":2171},{"id":969,"fab_id":0,"mat_id":2175},{"id":966,"fab_id":0,"mat_id":2178},{"id":967,"fab_id":0,"mat_id":2202},{"id":952,"fab_id":0,"mat_id":2176},{"id":963,"fab_id":0,"mat_id":2177},{"id":953,"fab_id":0,"mat_id":2177},{"id":965,"fab_id":0,"mat_id":2187},{"id":943,"fab_id":0,"mat_id":2224},{"id":947,"fab_id":1821,"mat_id":0},{"id":948,"fab_id":0,"mat_id":2182},{"id":970,"fab_id":1838,"mat_id":0},{"id":949,"fab_id":1762,"mat_id":0},{"id":941,"fab_id":0,"mat_id":2157}]
     * type : 1
     * user_id : 2009
     * user_token : {"sid":"452555","token":"2004aac343d7010f013e029e38741ba075141c"}
     */

    private String id;
    private int set_type;
    private int type;
    private String user_id;

    public LoginModle.DataBean.SessionBean getUser_token() {
        return user_token;
    }

    public void setUser_token(LoginModle.DataBean.SessionBean user_token) {
        this.user_token = user_token;
    }

    /**
     * sid : 452555
     * token : 2004aac343d7010f013e029e38741ba075141c
     */

    private LoginModle.DataBean.SessionBean user_token;
    /**
     * id : 950
     * fab_id : 0
     * mat_id : 2172
     */

    private List<ItemsBean> items;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getSet_type() {
        return set_type;
    }

    public void setSet_type(int set_type) {
        this.set_type = set_type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }



    public List<ItemsBean> getItems() {
        return items;
    }

    public void setItems(List<ItemsBean> items) {
        this.items = items;
    }


    public static class ItemsBean implements Serializable{
        private String id;
        private int fab_id;
        private int mat_id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getFab_id() {
            return fab_id;
        }

        public void setFab_id(int fab_id) {
            this.fab_id = fab_id;
        }

        public int getMat_id() {
            return mat_id;
        }

        public void setMat_id(int mat_id) {
            this.mat_id = mat_id;
        }
    }
}
