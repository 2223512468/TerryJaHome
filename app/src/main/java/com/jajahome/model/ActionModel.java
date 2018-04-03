package com.jajahome.model;

/**
 * Created by Administrator on 2017/8/17.
 */
public class ActionModel {


    /**
     * data : {"integrals":19862}
     * cmd : integral_exchange
     * status : 0
     */

    private DataBean data;
    private String cmd;
    private int status;
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

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
         * integrals : 19862
         */

        private int integrals;

        public int getIntegrals() {
            return integrals;
        }

        public void setIntegrals(int integrals) {
            this.integrals = integrals;
        }
    }
}
