package com.jajahome.network;

/**
 * Created by Administrator on 2017/4/7.
 */
public class MpReq {

    /**
     * cmd : home
     * content : {"id":1}
     */

    private String cmd;

    public ContentBean getContent() {
        return content;
    }

    public void setContent(ContentBean content) {
        this.content = content;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    /**
     * id : 1
     */

    private ContentBean content;

    public static class ContentBean {

        private String phoneNum;
        private String password;
        private String sms_token;

        public String getPhoneNum() {
            return phoneNum;
        }

        public void setPhoneNum(String phoneNum) {
            this.phoneNum = phoneNum;
        }

        public String getSms_token() {
            return sms_token;
        }

        public void setSms_token(String sms_token) {
            this.sms_token = sms_token;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
