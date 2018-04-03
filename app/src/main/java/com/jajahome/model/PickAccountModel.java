package com.jajahome.model;

/**
 * Created by Administrator on 2017/7/5.
 */
public class PickAccountModel {


    /**
     * data : {"pickAccount":{"type":0,"aliAccount":"15267185984","wxOpenid":"","wxNickName":""}}
     * cmd : pickCash_account
     * status : 0
     */

    private DataBean data;
    private String cmd;
    private int status;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public static class DataBean {
        /**
         * pickAccount : {"type":0,"aliAccount":"15267185984","wxOpenid":"","wxNickName":""}
         */

        private PickAccountBean pickAccount;

        public PickAccountBean getPickAccount() {
            return pickAccount;
        }

        public void setPickAccount(PickAccountBean pickAccount) {
            this.pickAccount = pickAccount;
        }

        public static class PickAccountBean {
            /**
             * type : 0
             * aliAccount : 15267185984
             * wxOpenid :
             * wxNickName :
             */

            private int type;
            private String aliAccount;
            private String wxOpenid;
            private String wxNickName;

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getAliAccount() {
                return aliAccount;
            }

            public void setAliAccount(String aliAccount) {
                this.aliAccount = aliAccount;
            }

            public String getWxOpenid() {
                return wxOpenid;
            }

            public void setWxOpenid(String wxOpenid) {
                this.wxOpenid = wxOpenid;
            }

            public String getWxNickName() {
                return wxNickName;
            }

            public void setWxNickName(String wxNickName) {
                this.wxNickName = wxNickName;
            }
        }
    }
}
