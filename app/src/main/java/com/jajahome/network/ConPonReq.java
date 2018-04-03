package com.jajahome.network;

/**
 * Created by Administrator on 2018/3/12.
 */
public class ConPonReq {

    private String cmd;
    private Content content;

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public static class Content {

        private String tel;
        private int con_id;

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public int getCon_id() {
            return con_id;
        }

        public void setCon_id(int con_id) {
            this.con_id = con_id;
        }
    }
}
