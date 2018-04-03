package com.jajahome.network;

/**
 * Created by Administrator on 2017/2/16.
 */
public class WuLiuReq {

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

        private String order_id;
        private String waybill_number;

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }

        public String getWaybill_number() {
            return waybill_number;
        }

        public void setWaybill_number(String waybill_number) {
            this.waybill_number = waybill_number;
        }
    }

}
