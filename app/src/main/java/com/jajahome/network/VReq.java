package com.jajahome.network;

/**
 * Created by ${Terry} on 2016/11/28.
 */
public class VReq {

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

        private int tzid;

        public int getTzid() {
            return tzid;
        }

        public void setTzid(int tzid) {
            this.tzid = tzid;
        }
    }
}
