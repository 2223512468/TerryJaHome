package com.jajahome.network;

/**
 * Created by Administrator on 2017/8/10.
 */
public class UpBase64Req {

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

        private String order_id;
        private Double order_price;
        private int pay_count;
        private String account;
        private String accountName;
        private String image;

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }

        public Double getOrder_price() {
            return order_price;
        }

        public void setOrder_price(Double order_price) {
            this.order_price = order_price;
        }

        public int getPay_count() {
            return pay_count;
        }

        public void setPay_count(int pay_count) {
            this.pay_count = pay_count;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getAccountName() {
            return accountName;
        }

        public void setAccountName(String accountName) {
            this.accountName = accountName;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }
}
