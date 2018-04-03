package com.jajahome.network;

/**
 * Created by ${Terry} on 2017/11/8.
 */
public class SortCityReq {

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
        private Double city;

        public Double getCity() {
            return city;
        }

        public void setCity(Double city) {
            this.city = city;
        }
    }
}
