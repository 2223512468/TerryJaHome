package com.jajahome.model;

import java.util.List;

/**
 * Created by Administrator on 2017/8/10.
 */
public class CommitModel {


    /**
     * data : []
     * status : 0
     * cmd : pay_transform_account
     */

    private int status;
    private String cmd;
    private List<?> data;
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public List<?> getData() {
        return data;
    }

    public void setData(List<?> data) {
        this.data = data;
    }
}
