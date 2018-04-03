package com.jajahome.model;

import com.google.gson.annotations.SerializedName;

/**
 * 微信支付支付回调接口
 */
public class JWeichatPayModel {

    /**
     * pay_info : {"partnerid":"1304926501","prepayid":"wx201608251431148ba68798900163588631","noncestr":"917e5223b8c35d4f7b02f498736f6df9","timestamp":1472106675,"package":"Sign=WXPay","sign":"8BED597ABBA72CDA9010D11B2C322886","appid":"wx865a0f13acf4bc81","trade_no":"733020531472106663"}
     */

    private DataBean data;
    /**
     * data : {"pay_info":{"partnerid":"1304926501","prepayid":"wx201608251431148ba68798900163588631","noncestr":"917e5223b8c35d4f7b02f498736f6df9","timestamp":1472106675,"package":"Sign=WXPay","sign":"8BED597ABBA72CDA9010D11B2C322886","appid":"wx865a0f13acf4bc81","trade_no":"733020531472106663"}}
     * cmd : pay_weixin
     * status : 0
     */

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
         * partnerid : 1304926501
         * prepayid : wx201608251431148ba68798900163588631
         * noncestr : 917e5223b8c35d4f7b02f498736f6df9
         * timestamp : 1472106675
         * package : Sign=WXPay
         * sign : 8BED597ABBA72CDA9010D11B2C322886
         * appid : wx865a0f13acf4bc81
         * trade_no : 733020531472106663
         */

        private PayInfoBean pay_info;

        public PayInfoBean getPay_info() {
            return pay_info;
        }

        public void setPay_info(PayInfoBean pay_info) {
            this.pay_info = pay_info;
        }

        public static class PayInfoBean {
            private String partnerid;
            private String prepayid;
            private String noncestr;
            private int timestamp;
            @SerializedName("package")
            private String packageX;
            private String sign;
            private String appid;
            private String trade_no;

            public String getPartnerid() {
                return partnerid;
            }

            public void setPartnerid(String partnerid) {
                this.partnerid = partnerid;
            }

            public String getPrepayid() {
                return prepayid;
            }

            public void setPrepayid(String prepayid) {
                this.prepayid = prepayid;
            }

            public String getNoncestr() {
                return noncestr;
            }

            public void setNoncestr(String noncestr) {
                this.noncestr = noncestr;
            }

            public int getTimestamp() {
                return timestamp;
            }

            public void setTimestamp(int timestamp) {
                this.timestamp = timestamp;
            }

            public String getPackageX() {
                return packageX;
            }

            public void setPackageX(String packageX) {
                this.packageX = packageX;
            }

            public String getSign() {
                return sign;
            }

            public void setSign(String sign) {
                this.sign = sign;
            }

            public String getAppid() {
                return appid;
            }

            public void setAppid(String appid) {
                this.appid = appid;
            }

            public String getTrade_no() {
                return trade_no;
            }

            public void setTrade_no(String trade_no) {
                this.trade_no = trade_no;
            }
        }
    }
}
