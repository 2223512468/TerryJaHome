package com.jajahome.model;

/**
 * Created by Administrator on 2017/3/31.
 */
public class QQtokenModel {


    /**
     * nameValuePairs : {"ret":0,"pay_token":"8BF2CBD9FCBBBCB107678E642CEA371C","pf":"desktop_m_qq-10000144-android-2002-","query_authority_cost":919,"authority_cost":0,"openid":"7E43D921E229F9F60728FE3FEAD78092","expires_in":7776000,"pfkey":"1136dac56046adb2a747a5a3fe045c91","msg":"","access_token":"707C5DE840298AC6DC44804990FDECF0","login_cost":534}
     */

    private NameValuePairsBean nameValuePairs;

    public NameValuePairsBean getNameValuePairs() {
        return nameValuePairs;
    }

    public void setNameValuePairs(NameValuePairsBean nameValuePairs) {
        this.nameValuePairs = nameValuePairs;
    }

    public static class NameValuePairsBean {
        /**
         * ret : 0
         * pay_token : 8BF2CBD9FCBBBCB107678E642CEA371C
         * pf : desktop_m_qq-10000144-android-2002-
         * query_authority_cost : 919
         * authority_cost : 0
         * openid : 7E43D921E229F9F60728FE3FEAD78092
         * expires_in : 7776000
         * pfkey : 1136dac56046adb2a747a5a3fe045c91
         * msg :
         * access_token : 707C5DE840298AC6DC44804990FDECF0
         * login_cost : 534
         */

        private int ret;
        private String pay_token;
        private String pf;
        private int query_authority_cost;
        private int authority_cost;
        private String openid;
        private int expires_in;
        private String pfkey;
        private String msg;
        private String access_token;
        private int login_cost;

        public int getRet() {
            return ret;
        }

        public void setRet(int ret) {
            this.ret = ret;
        }

        public String getPay_token() {
            return pay_token;
        }

        public void setPay_token(String pay_token) {
            this.pay_token = pay_token;
        }

        public String getPf() {
            return pf;
        }

        public void setPf(String pf) {
            this.pf = pf;
        }

        public int getQuery_authority_cost() {
            return query_authority_cost;
        }

        public void setQuery_authority_cost(int query_authority_cost) {
            this.query_authority_cost = query_authority_cost;
        }

        public int getAuthority_cost() {
            return authority_cost;
        }

        public void setAuthority_cost(int authority_cost) {
            this.authority_cost = authority_cost;
        }

        public String getOpenid() {
            return openid;
        }

        public void setOpenid(String openid) {
            this.openid = openid;
        }

        public int getExpires_in() {
            return expires_in;
        }

        public void setExpires_in(int expires_in) {
            this.expires_in = expires_in;
        }

        public String getPfkey() {
            return pfkey;
        }

        public void setPfkey(String pfkey) {
            this.pfkey = pfkey;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public String getAccess_token() {
            return access_token;
        }

        public void setAccess_token(String access_token) {
            this.access_token = access_token;
        }

        public int getLogin_cost() {
            return login_cost;
        }

        public void setLogin_cost(int login_cost) {
            this.login_cost = login_cost;
        }
    }
}
