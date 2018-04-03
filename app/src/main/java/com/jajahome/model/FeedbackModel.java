package com.jajahome.model;

import java.util.List;

/**
 * 意见反馈
 */
public class FeedbackModel {

    /**
     * data : []
     * cmd : feedback
     * status : 0
     */

    private String cmd;
    private int status;
    private List<?> data;

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

    public List<?> getData() {
        return data;
    }

    public void setData(List<?> data) {
        this.data = data;
    }
}
