package com.jajahome.network;

/**
 * Created by Administrator on 2016/10/28.
 */
public class CityReq {

    private String cmd;
    private ContentBean content;

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

    public static class ContentBean {
        private String user_location;

        public String getUser_location() {
            return user_location;
        }

        public void setUser_location(String user_location) {
            this.user_location = user_location;
        }
    }
}
