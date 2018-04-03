package com.jajahome.network;

/**
 * Created by Administrator on 2017/3/29.
 */
public class WxReq {

    private String cmd;

    public ContentBean getContent() {
        return content;
    }

    public void setContent(ContentBean content) {
        this.content = content;
    }

    private ContentBean content;

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }


    public static class ContentBean {
        private String access_token;
        private String openId;
        private int loginType;
        private String nickName;
        private String timestamp;
        private String headImageUrl;

        public String getHeadImageUrl() {
            return headImageUrl;
        }

        public void setHeadImageUrl(String headImageUrl) {
            this.headImageUrl = headImageUrl;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public String getOpenId() {
            return openId;
        }

        public void setOpenId(String openId) {
            this.openId = openId;
        }

        private String sex;
        private String invitecode;
        private String loginKey;

        public String getLoginKey() {
            return loginKey;
        }

        public void setLoginKey(String loginKey) {
            this.loginKey = loginKey;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getInvitecode() {
            return invitecode;
        }

        public void setInvitecode(String invitecode) {
            this.invitecode = invitecode;
        }

        public int getLoginType() {
            return loginType;
        }

        public void setLoginType(int loginType) {
            this.loginType = loginType;
        }

        public String getAccess_token() {
            return access_token;
        }

        public void setAccess_token(String access_token) {
            this.access_token = access_token;
        }


    }
}
