package com.jajahome.network;

/**
 * Created by Administrator on 2017/7/5.
 */
public class CashReq {

    private String cmd;
    private ContentBean content;

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public ContentBean getContent() {
        return content;
    }

    public void setContent(ContentBean content) {
        this.content = content;
    }

    public static class ContentBean {

        private Double cash;
        private int type;
        private String aliAccount;
        private String wxOpenid;
        private String wxNickName;

        public String getWxNickName() {
            return wxNickName;
        }

        public void setWxNickName(String wxNickName) {
            this.wxNickName = wxNickName;
        }

        public String getWxOpenid() {
            return wxOpenid;
        }

        public void setWxOpenid(String wxOpenid) {
            this.wxOpenid = wxOpenid;
        }

        public String getAliAccount() {
            return aliAccount;
        }

        public void setAliAccount(String aliAccount) {
            this.aliAccount = aliAccount;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public Double getCash() {
            return cash;
        }

        public void setCash(double cash) {
            this.cash = cash;
        }
    }
}
