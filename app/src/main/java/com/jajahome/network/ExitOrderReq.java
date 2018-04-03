package com.jajahome.network;

/**
 *  详情网络请求体封装
 */
public class ExitOrderReq {

    /**
     * cmd : home
     * content : {"id":1}
     */

    private String cmd;
    /**
     * id : 1
     */

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
        private String id;
        private int status_refund;

        public int getStatus_refund() {
            return status_refund;
        }

        public void setStatus_refund(int status_refund) {
            this.status_refund = status_refund;
        }

        public String getId() {
            return id;
        }
        public void setId(String id) {
            this.id = id;
        }
    }
}
