package com.jajahome.model;

/**
 * Created by Administrator on 2017/6/17.
 */
public class IsSignModel {

    /**
     * data : {"sign":0}
     * cmd : get_sign_info
     * status : 0
     */

    private DataBean data;
    private String cmd;
    private int status;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public static class DataBean {
        /**
         * sign : 0
         */

        private int sign;

        public int getSign() {
            return sign;
        }

        public void setSign(int sign) {
            this.sign = sign;
        }
    }
}
