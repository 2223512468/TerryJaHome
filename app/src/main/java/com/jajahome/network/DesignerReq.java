package com.jajahome.network;

/**
 * 申请设计师网络请求体封装
 */
public class DesignerReq {

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
        private String realname;// | 字符串 | 真实姓名
        private String address;// | 字符串 | 真实姓名
        private String company;// | 字符串 | 公司名称
        private String  card_id;// | 字符串 | 身份证号码
        private String  image_idcard_a;// 字符串 | 身份证正面图片经过 base64 编码
        private String  image_idcard_b;// 字符串 | 身份证反面图片经过 base64 编码
        private String  image_bcard;// 字符串 |名片图片经过 base64 编码
        private String invite_code;

        public String getInvite_code() {
            return invite_code;
        }

        public void setInvite_code(String invite_code) {
            this.invite_code = invite_code;
        }

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getCard_id() {
            return card_id;
        }

        public void setCard_id(String card_id) {
            this.card_id = card_id;
        }

        public String getImage_idcard_a() {
            return image_idcard_a;
        }

        public void setImage_idcard_a(String image_idcard_a) {
            this.image_idcard_a = image_idcard_a;
        }

        public String getImage_idcard_b() {
            return image_idcard_b;
        }

        public void setImage_idcard_b(String image_idcard_b) {
            this.image_idcard_b = image_idcard_b;
        }

        public String getImage_bcard() {
            return image_bcard;
        }

        public void setImage_bcard(String image_bcard) {
            this.image_bcard = image_bcard;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }
}
